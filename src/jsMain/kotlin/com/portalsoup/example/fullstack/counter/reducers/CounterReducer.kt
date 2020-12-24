package com.portalsoup.example.fullstack.counter.reducers

import redux.RAction
import com.portalsoup.example.fullstack.counter.actions.CountActions
import com.portalsoup.example.fullstack.counter.actions.CountState


fun counterReducer(state: CountState = CountState(0, 0), action: RAction): CountState = when (action) {
    is CountActions.IncrementCount -> state.increment()
    is CountActions.DecrementCount -> state.decrement()
    else -> state
}
