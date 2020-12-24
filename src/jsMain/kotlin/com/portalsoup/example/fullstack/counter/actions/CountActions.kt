package com.portalsoup.example.fullstack.counter.actions

import redux.RAction

data class CountState(val current: Int, val previous: Int) {
    fun increment(): CountState {
        println("Incrementing")
        return CountState(current = current + 1, previous = current)
    }

    fun decrement(): CountState {
        println("Decrementing")
        return CountState(current = current - 1, previous = current)
    }
}

sealed class CountActions: RAction {
    object IncrementCount : CountActions()
    object DecrementCount : CountActions()
}
