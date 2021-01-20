package com.portalsoup.example.fullstack.counter.components

import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*
import react.redux.rConnect
import redux.WrapperAction
import com.portalsoup.example.fullstack.counter.actions.CounterDispatch
import com.portalsoup.example.fullstack.counter.actions.CounterDispatch.*
import com.portalsoup.example.fullstack.redux.State
import kotlinx.browser.document
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import react.router.dom.routeLink
import react.router.dom.useParams

data class CounterPathParams(val name: String): RProps

interface CounterProps: RProps {
    var current: Int
    var previous: Int
    var incrementCount: () -> Unit
    var decrementCount: () -> Unit
    var loadCount: () -> Unit
}

private class Counter(props: CounterProps): RComponent<CounterProps, RState>(props) {
    override fun RBuilder.render() {
        div {
            h1 { +"Counter:" }

            p { +"Current: ${props.current}\tPrevious: ${props.previous}" }

            button {
                attrs.onClickFunction = { props.incrementCount() }
                label { +"Increment" }
            }

            button {
                attrs.onClickFunction = { props.decrementCount() }
                label { +"Decrement" }
            }
            routeLink("/") { button { +"Go home" } }
        }
    }
}

interface CounterStateProps: RProps {
    var current: Int
    var previous: Int
}

interface CounterDispatchProps: RProps {
    var incrementCount: () -> Unit
    var decrementCount: () -> Unit
    var loadCount: () -> Unit
}

/**
 * rConnect function takes 7 type parameters:

State this is your root state class
SetVisibilityFilter this is the action that this component is able to dispatch, use inheritance to allow for more actions (or just put RAction here).
WrapperAction Interface used to wrap your action class into an action object that Redux understands (just use the provided WrapperAction interface).
FilterLinkProps Props of the container component, use RProps if the container has no props.
LinkStateProps These are the props from the connected component (in this case Link) that should be mapped to state. *
LinkDispatchProps These are the props from the connected component (in this case Link) that should be mapped to dispatch. *
LinkProps The props from the connected component (in this case Link).
 */
val counterComponent: RClass<CounterProps> =
    rConnect<State, CounterDispatch, WrapperAction, RProps, CounterStateProps, CounterDispatchProps, CounterProps>(
        mapStateToProps = { state, _ ->
            current = state.count.current
            previous = state.count.previous
        },
        mapDispatchToProps = { _, _ ->
            incrementCount = { IncrementCount.action(useParams<CounterPathParams>()?.name ?: "anon")}
            decrementCount = { DecrementCount.action(useParams<CounterPathParams>()?.name ?: "anon") }
            loadCount = { LoadCount.action(useParams<CounterPathParams>()?.name ?: "anon")}
        }
    )(Counter::class.js.unsafeCast<RClass<CounterProps>>())
