package com.portalsoup.example.fullstack

import com.portalsoup.example.fullstack.api.CounterApi.counter
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
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
                counter()
            }
        }
    }
}