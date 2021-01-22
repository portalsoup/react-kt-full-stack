package com.portalsoup.example.fullstack.reducers

import com.portalsoup.example.fullstack.actions.AppActions
import com.portalsoup.example.fullstack.actions.AppState
import redux.RAction

fun appReducer(
    state: AppState = AppState("", 0),
    action: RAction
): AppState =
    when (action) {
        is AppActions.SetName -> {
            println("reducing ${action.newState.name}")
            state.copy(name = action.newState.name)
        }
        is AppActions.SaveName -> state.copy(count = action.newState.count)
        else -> state
    }
