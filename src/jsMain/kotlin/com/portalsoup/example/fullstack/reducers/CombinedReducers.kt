package com.portalsoup.example.fullstack.reducers

import com.portalsoup.example.fullstack.actions.AppState
import redux.Reducer
import redux.combineReducers
import kotlin.reflect.KProperty1

data class State(
    val appState: AppState = AppState("anon", 0)
)

fun combinedReducers() = combineReducers(
    mapOf(
        State::appState to ::appReducer
    )
)

// Helper methods

fun <S, A, R> combineReducers(reducers: Map<KProperty1<S, R>, Reducer<*, A>>): Reducer<S, A> {
    return combineReducers(reducers.mapKeys { it.key.name })
}
