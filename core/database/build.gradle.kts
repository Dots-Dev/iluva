plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)

    implementation(libs.bundles.exposed)
    implementation(libs.hikari)
    implementation(libs.postgresql)

    implementation(libs.flyway.core)
    implementation(libs.flyway.database.pgsql)
}