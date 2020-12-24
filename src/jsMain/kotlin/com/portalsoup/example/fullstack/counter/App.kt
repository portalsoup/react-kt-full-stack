package com.portalsoup.example.fullstack.counter

import react.*
import react.dom.div
import com.portalsoup.example.fullstack.counter.components.counterComponent
import react.dom.h1
import react.router.dom.*

data class GameState(var win: Boolean): RProps

private const val COUNTER_PATH = "/counter"

fun RBuilder.app() = browserRouter {
    switch {
        route("/", exact = true) {
            div {
                h1 { +"Full stack kotlin app example" }
                routeLink(COUNTER_PATH) {
                    +"Go to counter"
                }
            }
        }
        route(COUNTER_PATH) {
            div {
                counterComponent {}
                routeLink("/") { +"Go back" }
            }
        }
    }
}
