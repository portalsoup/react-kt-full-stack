package com.portalsoup.example.fullstack

import com.portalsoup.example.fullstack.business.CounterService
import com.portalsoup.example.fullstack.common.routes.ApiRoutes
import com.portalsoup.example.fullstack.common.routes.PathSegment
import com.portalsoup.example.fullstack.common.routes.ViewRoutes
import com.portalsoup.example.fullstack.utils.Try
import com.portalsoup.example.fullstack.utils.Try.Failure
import com.portalsoup.example.fullstack.utils.Try.Success
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.server.netty.Netty
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import kotlinx.html.*
import kotlin.random.Random

fun ViewRoutes.toUrl(): String = segments
    .joinToString("/") {
        when (it) {
            is PathSegment.VariableSegment -> "{${it.name.toLowerCase()}}"
            else -> it.name.toLowerCase()
        }
    }
    .takeIf { it.isNotEmpty() }
    ?: "/"

fun ApiRoutes.toUrl(): String = segments
    .joinToString("/") {
        when (it) {
            is PathSegment.VariableSegment -> "{${it.name.toLowerCase()}}"
            else -> it.name.toLowerCase()
        }
    }
    .takeIf { it.isNotEmpty() }
    ?: "/"

fun HTML.index() {
    head {
        title("Hello from Ktor!")
    }
    body {
        div {
            id = "root"
        }
        script(src = "/assets/output.js") {}
    }
}

fun main(args: Array<String>) {

    Retrier("database initialization") {
        DatabaseFactory.init()
    }
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        routing {
            get(ViewRoutes.HOME.toUrl()) {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }

            static("/assets") {
                resources()
            }

            get(ApiRoutes.GetCount.toUrl()) {
                val name = call.parameters["name"] ?: throw RuntimeException("No valid name found.")
                when (val countForName = CounterService.recordForName(name)) {
                    is Success -> call.respond("${countForName.data}")
                    is Failure -> call.respond(500)
                }
            }
        }
    }.start(wait = true)}
