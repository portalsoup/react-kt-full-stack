package com.portalsoup.example.fullstack.counter.components

import com.portalsoup.example.fullstack.Apper.actions.AppDispatch
import com.portalsoup.example.fullstack.common.routes.ViewRoutes
import com.portalsoup.example.fullstack.redux.State
import com.portalsoup.example.fullstack.toUrl
import kotlinx.browser.document
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import react.*
import react.dom.*
import react.redux.rConnect
import react.router.dom.browserRouter
import react.router.dom.route
import react.router.dom.routeLink
import react.router.dom.switch
import redux.WrapperAction

interface AppProps: RProps {
    var name: String
    var setName: (String) -> Unit
}


class App(props: AppProps): RComponent<AppProps, RState>(props) {
    override fun RBuilder.render() {
        div {
            h1 { +"Full stack kotlin app example" }

            label { +"What is your name?" }
            textArea {
                attrs.id = "name-input"
                attrs {
                    onChangeFunction = {
                        val target = it.target as HTMLTextAreaElement
                        val newValue = target.value
                        println("new value: $newValue")
                        props.setName(newValue)
                    }
                }
            }
            label { +"Your name is ${props.name}" }

            routeLink(
                ViewRoutes.COUNTER.toUrl(
                    hashMapOf(
                        "name" to props.name
                    )
                )
            ) {
                button {
                    +"Go to counter"
                }
            }
        }
    }
}

interface AppStateProps: RProps {
    var name: String
}

interface AppDispatchProps: RProps {
    var setName: (String) -> Unit
}

val appComponent: RClass<AppProps> =
    rConnect<State, AppDispatch, WrapperAction, RProps, AppStateProps, AppDispatchProps, AppProps>(
        mapStateToProps = { state, _ ->
            name = state.appState.name
        },
        mapDispatchToProps = { _, _ ->
            setName = { newName ->
                AppDispatch.SetName.action(newName)
            }
        }
    )(App::class.js.unsafeCast<RClass<AppProps>>())