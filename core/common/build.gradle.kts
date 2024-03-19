plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    api(libs.arrow.core)
    api(libs.arrow.fx.coroutines)

    api(libs.kodein)
}