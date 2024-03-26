plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.iluva.koin)
    alias(libs.plugins.iluva.test)
}

dependencies {
    implementation(projects.core.security)

    implementation(libs.arrow.core.serialization)
    implementation(libs.ktor.serialization.kotlinx.json.jvm)
}