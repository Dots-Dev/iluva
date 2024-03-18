plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
}

group = "org.dotsdev"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.database)

    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.content.negotiation.jvm)
    implementation(libs.ktor.server.netty.jvm)
    implementation(libs.ktor.serialization.kotlinx.json.jvm)
    implementation(libs.ktor.server.config.yaml.jvm)
    implementation(libs.logback.classic)

    implementation(libs.cohort.core)

    implementation(libs.arrow.suspendapp)
    implementation(libs.arrow.suspendapp.ktor)

    implementation(libs.kodein.ktor.server.jvm)

    implementation(libs.hoplite.core)
    implementation(libs.hoplite.yaml)

    testImplementation(libs.ktor.server.tests.jvm)
    testImplementation(libs.kotlin.test.junit)
}