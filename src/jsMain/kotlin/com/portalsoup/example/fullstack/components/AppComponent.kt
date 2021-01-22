package com.portalsoup.example.fullstack.components

import com.portalsoup.example.fullstack.Apper.actions.AppDispatch
import com.portalsoup.example.fullstack.common.routes.ViewRoutes
import com.portalsoup.example.fullstack.reducers.State
import com.portalsoup.example.fullstack.toUrl
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLTextAreaElement
import react.*
import react.dom.*
import react.redux.rConnect
import react.router.dom.routeLink
import redux.WrapperAction

interface AppProps: RProps {
    var name: String
    var count: Int
    var setName: (String) -> Unit
    var saveName: (String) -> Unit
}


class App(props: AppProps): RComponent<AppProps, RState>(props) {
    override fun RBuilder.render() {
        println("test")
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

            button {
                label { +"Save" }
                attrs {
                    onClickFunction = {
                        props.saveName(props.name)
                    }
                }
            }
            label { +"Your name is ${props.name} and you've pinged the API ${props.count} times" }

        }
    }
}

interface AppStateProps: RProps {
    var name: String
    var count: Int
}

interface AppDispatchProps: RProps {
    var setName: (String) -> Unit
    var saveName: (String) -> Unit
}

val appComponent: RClass<AppProps> =
    rConnect<State, AppDispatch, WrapperAction, RProps, AppStateProps, AppDispatchProps, AppProps>(
        mapStateToProps = { state, _ ->
            name = state.appState.name
            count = state.appState.count
        },
        mapDispatchToProps = { _, _ ->
            setName = { newName ->
                AppDispatch.SetName.action(newName)
            }
            saveName = { newName -> AppDispatch.SaveName.action(newName)}
        }
    )(App::class.js.unsafeCast<RClass<AppProps>>())