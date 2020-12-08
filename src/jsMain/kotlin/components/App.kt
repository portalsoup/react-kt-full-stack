package components

import kotlinx.coroutines.MainScope
import react.RProps
import react.dom.div
import react.functionalComponent

private val scope = MainScope()

val App = functionalComponent<RProps> {
    div { +"I'm a component" }
}
