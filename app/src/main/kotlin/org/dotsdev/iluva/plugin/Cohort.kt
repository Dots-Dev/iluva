package org.dotsdev.iluva.plugin

import com.sksamuel.cohort.Cohort
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureCohort() {
    install(Cohort) {
        operatingSystem = true
        jvmInfo = true
    }
}