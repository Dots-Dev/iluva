plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.iluva.koin)
}

group = "org.dotsdev"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.cio.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.security)

    implementation(projects.api.auth)

    implementation(libs.ktor.serialization.kotlinx.json.jvm)
    implementation(libs.ktor.server.auth.jvm)
    implementation(libs.ktor.server.auth.jwt.jvm)
    implementation(libs.ktor.server.call.logging.jvm)
    implementation(libs.ktor.server.cio.jvm)
    implementation(libs.ktor.server.config.yaml.jvm)
    implementation(libs.ktor.server.content.negotiation.jvm)
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.cors.jvm)
    implementation(libs.ktor.server.resources.jvm)
    implementation(libs.ktor.server.status.pages.jvm)
    implementation(libs.logback.classic)

    implementation(libs.ktor.swagger.ui)
    implementation(libs.json.schema.serialization)

    implementation(libs.cohort.core)
    implementation(libs.cohort.hikari)

    implementation(libs.arrow.suspendapp)
    implementation(libs.arrow.suspendapp.ktor)

    implementation(libs.bundles.arrow)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)

    implementation(libs.hoplite.core)
    implementation(libs.hoplite.yaml)

    implementation(libs.hikari)

    testImplementation(projects.core.testing)
}