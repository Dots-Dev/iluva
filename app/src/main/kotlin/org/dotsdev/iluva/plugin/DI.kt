package org.dotsdev.iluva.plugin

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import config.Config
import io.ktor.server.application.Application
import org.dotsdev.iluva.authModule
import org.dotsdev.iluva.di.dataModule
import org.dotsdev.iluva.securityModule
import org.kodein.di.bindSingleton
import org.kodein.di.ktor.di

fun Application.configureDI() {
    di {
        bindSingleton { serverConfig() }
        import(dataModule)
        import(securityModule)
        import(authModule)
    }
}

private fun serverConfig() = ConfigLoaderBuilder.default()
    .addResourceSource("/application.yaml")
    .build()
    .loadConfigOrThrow<Config>()