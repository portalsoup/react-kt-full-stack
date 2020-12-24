package com.portalsoup.example.fullstack

import com.portalsoup.example.fullstack.business.CounterService
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
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }

            static("/assets") {
                resources()
            }

            route("/counter") {

                get("/{name}") {
                    println("Getting count")
                    val name = call.parameters["name"] ?: throw RuntimeException("No valid name found.")
                    val count = CounterService.getCount(name)
                    println("found count: $count")
                    count?.let { call.respond("$count") }
                }

                get("/{name}/increment") {
                    println("Incrementing")
                    val name = call.parameters["name"] ?: throw RuntimeException("No valid name found.")
                    val count = CounterService.incrementCounter(name)
                    println("found count: $count")
                }

                get("/{name}/decrement") {
                    println("Decrementing")
                    val name = call.parameters["name"] ?: throw RuntimeException("No valid name found.")
                    val count = CounterService.decrementCounter(name)
                    println("found count: $count")

                }
            }
        }
    }.start(wait = true)}
