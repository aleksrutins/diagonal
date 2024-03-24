package com.farthergate.diagonal.server

import com.farthergate.diagonal.Runner
import com.farthergate.diagonal.TestConstructor
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class Server(vararg val availableTests: TestConstructor<*>, val port: Int = 9090) {
    val runner = Runner(availableTests, ServerLogger(this))
    fun start() {
        embeddedServer(Netty, port = port) {
            routing {
                get("/") {
                    call.respondText("Hello, world!")
                }
            }
        }.start(wait = true)
    }
}