package com.portalsoup.example.fullstack

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
