package com.portalsoup.example.fullstack.Apper.actions

import com.portalsoup.example.fullstack.store
import react.RProps
import react.router.dom.useParams
import redux.RAction
import kotlin.js.Promise

// https://medium.com/@ralf.stuckert/getting-started-with-kotlin-react-part-iii-c316573e0abb

sealed class AppActions: RAction {
    data class SetName(val newState: AppState): AppActions()
}

sealed class AppDispatch {

    abstract fun action(name: String): Promise<AppState>

    object SetName : AppDispatch() {
        override fun action(name: String): Promise<AppState> {
            println("Action called")
            return Promise.resolve(store.dispatch(AppActions.SetName(AppState(name))) as AppState)
        }
    }
}

data class AppState(
    var name: String
)