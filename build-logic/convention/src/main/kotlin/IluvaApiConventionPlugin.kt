import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class IluvaApiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply {
            apply("org.jetbrains.kotlin.jvm")
            apply("com.google.devtools.ksp")
        }

        dependencies {
            implementation(project(":core:common"))
            implementation(project(":core:data"))
            implementation(project(":core:model"))
            implementation(project(":core:security"))

            implementation(libs.findLibrary("ktor.server.resources.jvm").get())
            implementation(libs.findLibrary("ktor.server.status.pages.jvm").get())
            implementation(libs.findLibrary("ktor.serialization.kotlinx.json.jvm").get())

            implementation(libs.findLibrary("arrow.core").get())
            implementation(libs.findLibrary("arrow.fx.coroutines").get())

            implementation(libs.findLibrary("kodein").get())
            implementation(libs.findLibrary("kodein.ktor.server.jvm").get())

            implementation(libs.findLibrary("ktor.swagger.ui").get())

            implementation(libs.findLibrary("thing").get())
        }
    }

}