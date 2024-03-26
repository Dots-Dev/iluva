plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotest)
    alias(libs.plugins.iluva.test)
}

dependencies {
    api(platform(libs.koin.bom))
    api(libs.koin.test)
    api(libs.bundles.kotest.arrow)
    api(libs.ktor.server.tests.jvm)
    api(libs.kotest.assertions.ktor)
    api(libs.kotest.extensions.koin)
    implementation(libs.kotest.extensions.test.containers)
    implementation(libs.test.containers.postgresql)
}