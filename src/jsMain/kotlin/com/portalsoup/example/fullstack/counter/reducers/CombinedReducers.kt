package com.portalsoup.example.fullstack.counter.reducers

import redux.Reducer
import redux.combineReducers
import com.portalsoup.example.fullstack.counter.actions.CountState
import kotlin.reflect.KProperty1

data class State(
    val count: CountState = CountState(0, 0)
)

fun combinedReducers() = combineReducers(
    mapOf(
        State::count to ::counterReducer
    )
)


// Helper methods

fun <S, A, R> combineReducers(reducers: Map<KProperty1<S, R>, Reducer<*, A>>): Reducer<S, A> {
    return combineReducers(reducers.mapKeys { it.key.name })
}
