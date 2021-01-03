package com.portalsoup.example.fullstack.counter

import com.portalsoup.example.fullstack.common.routes.ViewRoutes
import react.*
import react.dom.div
import com.portalsoup.example.fullstack.counter.components.counterComponent
import com.portalsoup.example.fullstack.toUrl
import kotlinx.browser.document
import kotlinx.html.id
import react.dom.h1
import react.dom.label
import react.dom.textArea
import react.router.dom.*

data class GameState(var win: Boolean): RProps

fun RBuilder.app() = browserRouter {
    switch {
        route("/", exact = true) {
            div {
                h1 { +"Full stack kotlin app example" }

                label { +"What is your name?" }
                textArea {
                    attrs.id = "name-input"
                }

                routeLink(ViewRoutes.COUNTER.toUrl(hashMapOf(
                    "name" to (document.getElementById("name-input")?.nodeValue ?: "anon")
                ))) {
                    +"Go to counter"
                }
            }
        }
        route(ViewRoutes.COUNTER.toUrl()) {
            div {
                counterComponent {}
                routeLink("/") { +"Go back" }
            }
        }
    }
}
