package com.portalsoup.example.fullstack.api

import com.portalsoup.example.fullstack.business.CounterService
import com.portalsoup.example.fullstack.common.resources.CounterResource
import com.portalsoup.example.fullstack.utils.Try.Failure
import com.portalsoup.example.fullstack.utils.Try.Success
import com.portalsoup.example.fullstack.utils.getLogger
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

object CounterApi {

    val log = getLogger(javaClass)

    fun Route.counter() {
        route("counter") {
            get("{name}") {
                val name = call.parameters["name"] ?: throw RuntimeException("No valid name found.")
                when (val countForName = CounterService.recordForName(name)) {
                    is Success -> call.respond(CounterResource(name, countForName.data))
                    is Failure -> call.respond(500)
                }
            }
        }
    }
}