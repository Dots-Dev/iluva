plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)

    implementation(libs.kodein)

    implementation(libs.thing)

    implementation(libs.ktor.serialization.kotlinx.json.jvm)
}