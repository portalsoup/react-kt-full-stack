package com.portalsoup.example.fullstack

import com.portalsoup.example.fullstack.common.routes.ApiRoutes
import com.portalsoup.example.fullstack.common.routes.PathSegment
import com.portalsoup.example.fullstack.common.routes.ViewRoutes
import com.portalsoup.example.fullstack.counter.components.appComponent
import com.portalsoup.example.fullstack.counter.components.counterComponent
import kotlinx.browser.document
import kotlinx.browser.window
import react.redux.provider
import redux.RAction
import redux.compose
import redux.createStore
import redux.rEnhancer
import com.portalsoup.example.fullstack.redux.State
import com.portalsoup.example.fullstack.redux.combinedReducers
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLTextAreaElement
import react.dom.*
import react.router.dom.browserRouter
import react.router.dom.route
import react.router.dom.routeLink
import react.router.dom.switch

fun ViewRoutes.toUrl(pathParams: Map<String, String>? = null): String {
    println("Route invoked")
    return segments
        .map { segment -> pathParams?.get(segment.name)?.let { PathSegment.StaticSegment(it) } ?: segment }
        .apply { println("original segments: $this") }
        .joinToString("/") {
            when (it) {
                is PathSegment.VariableSegment -> ":${it.name.toLowerCase()}"
                else -> it.name.toLowerCase()
            }
        }
        .takeIf { it.isNotEmpty() }
        ?.apply { println("this is this: $this") }
        ?: "/"
}

fun ApiRoutes.toUrl(): String = segments
    .joinToString("/") {
        when (it) {
            is PathSegment.VariableSegment -> ":${it.name.toLowerCase()}"
            else -> it.name.toLowerCase()
        }
    }
    .takeIf { it.isNotEmpty() }
    ?: "/"

val store = createStore<State, RAction, dynamic>(
    combinedReducers(), State(), compose(
        rEnhancer(),
        js("if(window.__REDUX_DEVTOOLS_EXTENSION__ )window.__REDUX_DEVTOOLS_EXTENSION__ ();else(function(f){return f;});")
    )
)

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            div {
                provider(store) {
                    browserRouter {
                        switch {
                            route("/", exact = true) {
                                appComponent {}
                            }
                            route("/counter/:name") {
                                counterComponent {}
                            }
                        }
                    }
                }
            }
        }
    }
}
