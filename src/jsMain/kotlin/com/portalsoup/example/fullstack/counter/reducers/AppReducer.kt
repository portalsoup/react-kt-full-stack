package com.portalsoup.example.fullstack.counter.reducers

import com.portalsoup.example.fullstack.Apper.actions.AppActions
import com.portalsoup.example.fullstack.Apper.actions.AppState
import redux.RAction

fun appReducer(state: AppState = AppState(""), action: RAction): AppState = when (action) {
    is AppActions.SetName -> {
        println("state $state \naction $action")
        state.copy(name = action.newState.name)
    }
    else -> state
}