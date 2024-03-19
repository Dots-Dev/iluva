package org.dotsdev.iluva.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.Routing

fun Application.configureRouting() {
    install(Routing) {

    }
}