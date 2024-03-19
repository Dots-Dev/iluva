plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)

    api(libs.ktor.server.auth.jwt.jvm)

    implementation(libs.bcrypt)
}