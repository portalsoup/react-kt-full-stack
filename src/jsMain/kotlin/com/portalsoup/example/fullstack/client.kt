package com.portalsoup.example.fullstack

import com.portalsoup.example.fullstack.common.routes.ApiRoutes
import com.portalsoup.example.fullstack.common.routes.PathSegment
import com.portalsoup.example.fullstack.common.routes.ViewRoutes
import com.portalsoup.example.fullstack.counter.app
import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import react.redux.provider
import redux.RAction
import redux.compose
import redux.createStore
import redux.rEnhancer
import com.portalsoup.example.fullstack.counter.reducers.State
import com.portalsoup.example.fullstack.counter.reducers.combinedReducers

fun ViewRoutes.toUrl(pathParams: Map<String, String>? = null): String = segments
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
            provider(store) {
                app()
            }
        }
    }
}
