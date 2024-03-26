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
        register("api") {
            id = "iluva.api"
            implementationClass = "IluvaApiConventionPlugin"
        }
        register("test") {
            id = "iluva.test"
            implementationClass = "IluvaTestConventionPlugin"
        }
        register("koin") {
            id = "iluva.koin"
            implementationClass = "IluvaKoinConventionPlugin"
        }
    }
}