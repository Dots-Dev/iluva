package org.dotsdev.iluva.plugin

import com.sksamuel.cohort.Cohort
import com.sksamuel.cohort.HealthCheckRegistry
import com.sksamuel.cohort.hikari.HikariConnectionsHealthCheck
import com.sksamuel.cohort.threads.ThreadDeadlockHealthCheck
import io.ktor.server.application.Application
import io.ktor.server.application.install
import kotlinx.coroutines.Dispatchers
import org.dotsdev.iluva.database.IluvaDatabase
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureCohort() {
    val db by closestDI().instance<IluvaDatabase>()
    val healthCheck = HealthCheckRegistry(Dispatchers.Default) {
        register(HikariConnectionsHealthCheck(db.hikariDataSource, 1))
        register(ThreadDeadlockHealthCheck())
    }

    install(Cohort) {
        healthcheck("/healthcheck", healthCheck)
    }
}