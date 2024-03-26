plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.iluva.koin)
    alias(libs.plugins.iluva.test)
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.ktor.server.auth.jwt.jvm)

    implementation(libs.bcrypt)
}