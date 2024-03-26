import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class IluvaApiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply {
            apply("org.jetbrains.kotlin.jvm")
            apply("com.google.devtools.ksp")
            apply("iluva.test")
        }

        dependencies {
            implementation(project(":core:common"))
            implementation(project(":core:data"))
            implementation(project(":core:model"))
            implementation(project(":core:security"))

            implementation(libs.findLibrary("ktor.server.resources.jvm").get())
            implementation(libs.findLibrary("ktor.server.status.pages.jvm").get())
            implementation(libs.findLibrary("ktor.server.auth.jvm").get())
            implementation(libs.findLibrary("ktor.serialization.kotlinx.json.jvm").get())

            implementation(libs.findLibrary("ktor.swagger.ui").get())

            implementation(libs.findBundle("arrow").get())
        }
    }

}