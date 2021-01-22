package com.portalsoup.example.fullstack.Apper.actions

import axios
import com.portalsoup.example.fullstack.jsdefs.Axios
import com.portalsoup.example.fullstack.store
import redux.RAction
import kotlin.js.Promise

// https://medium.com/@ralf.stuckert/getting-started-with-kotlin-react-part-iii-c316573e0abb

sealed class AppActions: RAction {
    data class SetName(val newState: AppState): AppActions()
    data class SaveName(val newState: AppState): AppActions()
}

sealed class AppDispatch {

    abstract fun action(name: String): Promise<AppState>

    object SetName : AppDispatch() {
        override fun action(name: String): Promise<AppState> {
            println("Got action $name")
            return Promise.resolve(AppState(name, store.getState().appState.count))
                .then { store.dispatch(AppActions.SetName(it)) }
        }
    }

    object SaveName: AppDispatch() {
        override fun action(name: String): Promise<AppState> {
            return Axios.get<Int>("/api/counter/$name").then {
                println("About to dispatch ${it.data}")
                store.dispatch(AppActions.SaveName(AppState(name, it.data)))
            }
        }
    }
}

data class AppState(
    var name: String,
    var count: Int
)