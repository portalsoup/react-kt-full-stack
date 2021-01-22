package com.portalsoup.example.fullstack

import com.portalsoup.example.fullstack.business.CounterService
import com.portalsoup.example.fullstack.common.resources.CounterResource
import com.portalsoup.example.fullstack.utils.Try
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import kotlinx.html.HTML


fun Application.main() {

    install(ContentNegotiation) { gson { } }

    install(Routing) {
        routing {

            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }

            static("/assets") {
                resources()
            }

            route("/api") {

                get("counter/{name}") {
                    val name = call.parameters["name"] ?: throw RuntimeException("No valid name found.")
                    when (val countForName = CounterService.recordForName(name)) {
                        is Try.Success -> call.respond(CounterResource(name, countForName.data))
                        is Try.Failure -> call.respond(500)
                    }
                }
            }
        }
    }
}