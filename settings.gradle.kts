pluginManagement {
    includeBuild("build-logic")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            mavenCentral()
            gradlePluginPortal()
        }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "liuva"
include(":app")
include(listOf(
    ":core:common",
    ":core:database",
    ":core:model",
    ":core:security"
))
include(listOf(
    ":api:auth",
    ":api:user"
))
