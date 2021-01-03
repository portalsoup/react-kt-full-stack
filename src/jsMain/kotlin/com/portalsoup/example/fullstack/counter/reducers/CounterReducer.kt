package com.portalsoup.example.fullstack.counter.reducers

import com.portalsoup.example.fullstack.counter.actions.CountActions
import redux.RAction
import com.portalsoup.example.fullstack.counter.actions.CountState

fun counterReducer(state: CountState = CountState("", 0, 0), action: RAction): CountState = when (action) {
    is CountActions.IncrementCount -> {
        println("state $state")
        state
    }
    is CountActions.DecrementCount -> state
    else -> state
}
