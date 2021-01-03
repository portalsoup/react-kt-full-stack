package com.portalsoup.example.fullstack

import com.portalsoup.example.fullstack.business.CounterService
import com.portalsoup.example.fullstack.common.routes.ApiRoutes
import com.portalsoup.example.fullstack.common.routes.PathSegment
import com.portalsoup.example.fullstack.common.routes.PathVariables
import com.portalsoup.example.fullstack.common.routes.ViewRoutes
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

    // TODO This go on coroutine?
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
                println("Getting count")
                val name = call.parameters["name"] ?: throw RuntimeException("No valid name found.")
                val count = CounterService.getCount(name)
                println("found count: $count")
                count?.let { call.respond("$count") }
            }
//
            get(ApiRoutes.IncrementCount.toUrl()) {
                println("Incrementing")
                val name = call.parameters["name"] ?: throw RuntimeException("No valid name found.")
                val count = CounterService.incrementCounter(name)
                println("found count: $count")
            }

            get(ApiRoutes.DecrementCount.toUrl()) {
                println("Decrementing")
                val name = call.parameters["name"] ?: throw RuntimeException("No valid name found.")
                val count = CounterService.decrementCounter(name)
                println("found count: $count")

            }
        }
    }.start(wait = true)}
