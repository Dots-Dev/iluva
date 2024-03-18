package org.dotsdev.iluva

import arrow.continuations.SuspendApp
import arrow.fx.coroutines.resourceScope
import config.Config
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import kotlinx.coroutines.awaitCancellation
import org.dotsdev.iluva.plugin.configureCohort
import org.dotsdev.iluva.plugin.configureDI
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun main(args: Array<String>) = SuspendApp {
    resourceScope {
        EngineMain.main(args)
        awaitCancellation()
    }
}

fun Application.module() {
    configureDI()
    configureCohort()
}
