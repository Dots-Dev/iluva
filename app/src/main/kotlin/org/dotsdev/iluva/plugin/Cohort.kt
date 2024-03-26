package org.dotsdev.iluva.plugin

import com.sksamuel.cohort.Cohort
import com.sksamuel.cohort.HealthCheckRegistry
import com.sksamuel.cohort.threads.ThreadDeadlockHealthCheck
import io.ktor.server.application.Application
import io.ktor.server.application.install
import kotlinx.coroutines.Dispatchers
import org.dotsdev.iluva.database.DatabaseFactory
import org.koin.ktor.ext.inject

fun Application.configureCohort() {
    val db by inject<DatabaseFactory>()
    val healthCheck = HealthCheckRegistry(Dispatchers.Default) {
        register(ThreadDeadlockHealthCheck())
    }

    install(Cohort) {
        healthcheck("/healthcheck", healthCheck)
    }
}