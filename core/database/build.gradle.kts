plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.bundles.exposed)
}