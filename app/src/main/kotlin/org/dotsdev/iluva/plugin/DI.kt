package org.dotsdev.iluva.plugin

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import config.Config
import di.commonModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.dotsdev.iluva.authModule
import org.dotsdev.iluva.di.dataModule
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger

internal val appModule = module {
    single { serverConfig() }
}

fun Application.configureDI() {
    install(Koin) {
        SLF4JLogger()
        modules(listOf(
            appModule,
            authModule,
            commonModule,
            dataModule
        ))
    }
}

private fun serverConfig() = ConfigLoaderBuilder.default()
    .addResourceSource("/application.yaml")
    .build()
    .loadConfigOrThrow<Config>()