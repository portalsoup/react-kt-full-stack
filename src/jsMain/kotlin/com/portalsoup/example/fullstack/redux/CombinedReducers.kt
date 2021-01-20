package com.portalsoup.example.fullstack.redux

import com.portalsoup.example.fullstack.Apper.actions.AppState
import redux.Reducer
import redux.combineReducers
import com.portalsoup.example.fullstack.counter.actions.CountState
import com.portalsoup.example.fullstack.counter.reducers.appReducer
import com.portalsoup.example.fullstack.counter.reducers.counterReducer
import kotlin.reflect.KProperty1

data class State(
    val count: CountState = CountState(0, 0),
    val appState: AppState = AppState("anon")
)

fun combinedReducers() = combineReducers(
    mapOf(
        State::count to ::counterReducer,
        State::appState to ::appReducer
    )
)

// Helper methods

fun <S, A, R> combineReducers(reducers: Map<KProperty1<S, R>, Reducer<*, A>>): Reducer<S, A> {
    return combineReducers(reducers.mapKeys { it.key.name })
}
