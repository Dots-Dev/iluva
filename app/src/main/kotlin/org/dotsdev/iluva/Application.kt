package org.dotsdev.iluva

import arrow.continuations.SuspendApp
import arrow.fx.coroutines.resourceScope
import io.ktor.server.application.Application
import io.ktor.server.cio.EngineMain
import kotlinx.coroutines.awaitCancellation
import org.dotsdev.iluva.plugin.configureAuthentication
import org.dotsdev.iluva.plugin.configureCohort
import org.dotsdev.iluva.plugin.configureContentNegotiation
import org.dotsdev.iluva.plugin.configureDI
import org.dotsdev.iluva.plugin.configureRouting
import org.dotsdev.iluva.plugin.configureStatusPage
import org.dotsdev.iluva.plugin.configureSwaggerUI

fun main(args: Array<String>) = SuspendApp {
    resourceScope {
        EngineMain.main(args)
        awaitCancellation()
    }
}

fun Application.module() {
    configureDI()
    configureAuthentication()
    configureCohort()
    configureContentNegotiation()
    configureStatusPage()
    configureRouting()
    configureSwaggerUI()
}
