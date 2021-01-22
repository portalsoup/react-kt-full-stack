package com.portalsoup.example.fullstack.actions

import com.portalsoup.example.fullstack.common.resources.CounterResource
import com.portalsoup.example.fullstack.jsdefs.Axios
import com.portalsoup.example.fullstack.jsdefs.AxiosRequestConfig
import com.portalsoup.example.fullstack.store
import kotlinext.js.jsObject
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
            val config: dynamic = jsObject()
            val headers: dynamic = jsObject()
            headers["Accept"] = "application/json"
            config.headers = headers

            return Axios.get<CounterResource>("/api/counter/$name", config = config).then {
                println("About to dispatch ${it.data}")
                store.dispatch(AppActions.SaveName(AppState(it.data.name, it.data.count)))
            }
        }
    }
}

data class AppState(
    var name: String,
    var count: Int
)