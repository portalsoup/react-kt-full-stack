package com.portalsoup.example.fullstack

import com.portalsoup.example.fullstack.utils.Retrier
import io.ktor.server.netty.Netty
import io.ktor.server.engine.*

fun main(args: Array<String>) {
    Retrier("database initialization") {
        DatabaseFactory.init()
    }
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}