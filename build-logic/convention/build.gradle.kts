plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ktor.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("ktorApi") {
            id = "iluva.api"
            implementationClass = "IluvaApiConventionPlugin"
        }
    }
}