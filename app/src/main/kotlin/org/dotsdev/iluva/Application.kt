package org.dotsdev.iluva

import io.ktor.server.application.Application
import io.ktor.server.cio.EngineMain
import org.dotsdev.iluva.plugins.configureRouting
import org.dotsdev.iluva.plugins.configureSerialization


fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureRouting()
}
