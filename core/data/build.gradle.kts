plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.iluva.koin)
    alias(libs.plugins.iluva.test)
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(projects.core.security)

    implementation(libs.bundles.exposed)
    implementation(libs.hikari)
    implementation(libs.postgresql)

    implementation(libs.bundles.arrow)

    implementation(libs.flyway.core)
    implementation(libs.flyway.database.pgsql)
}