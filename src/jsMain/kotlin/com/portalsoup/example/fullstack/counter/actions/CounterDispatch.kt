package com.portalsoup.example.fullstack.counter.actions

import axios
import com.portalsoup.example.fullstack.common.resources.CounterResource
import com.portalsoup.example.fullstack.store
import kotlinx.browser.document
import org.w3c.dom.url.URL
import react.RProps
import react.router.dom.useParams
import redux.RAction
import redux.WrapperAction
import kotlin.js.Promise

// https://medium.com/@ralf.stuckert/getting-started-with-kotlin-react-part-iii-c316573e0abb

data class CounterPathParams(val name: String): RProps

sealed class CountActions: RAction {
    data class IncrementCount(val newState: CountState): CountActions()
    data class DecrementCount(val newState: CountState): CountActions()
}

sealed class CounterDispatch {

    abstract fun action(): Promise<CountState>

    protected val name: () -> String =
        { useParams<CounterPathParams>()?.name ?: throw RuntimeException() }


    object IncrementCount : CounterDispatch() {
        override fun action(): Promise<CountState> {

            return axios.get<CounterResource>("/counter/${name()}/increment")
                .then { it.toResource() }
                .then { store.dispatch(CountActions.IncrementCount(it)) }
        }
    }

    object DecrementCount : CounterDispatch() {
        override fun action(): Promise<CountState> {
            println("Decrementing")
            return axios.get<CounterResource>("/counter/${name()}/decrement")
                .then { it.toResource() }
        }
    }

    object LoadCount : CounterDispatch() {
        override fun action(): Promise<CountState> {
            URL(document.URL).searchParams
            println("Loading")
            return axios.get<CounterResource>("/counter/${name()}")
                .then { it.toResource() }
        }
    }
}

data class CountState(val name: String, val current: Int, val previous: Int)

fun CounterResource.toResource(): CountState = CountState(name, count, 0)