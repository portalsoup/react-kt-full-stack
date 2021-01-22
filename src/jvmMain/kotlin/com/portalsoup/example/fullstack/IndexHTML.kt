package com.portalsoup.example.fullstack

import kotlinx.html.*

fun HTML.index() {
    head {
        title("Hello from Ktor!")
    }
    body {
        div {
            id = "root"
        }
        script(src = "/assets/output.js") {}
    }
}