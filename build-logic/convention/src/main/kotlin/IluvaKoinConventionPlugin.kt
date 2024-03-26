import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class IluvaKoinConventionPlugin :Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        dependencies {
            implementation(platform(libs.findLibrary("koin.bom").get()))
            implementation(libs.findLibrary("koin.core").get())
            implementation(libs.findLibrary("koin.core.coroutines").get())
            implementation(libs.findLibrary("koin.ktor").get())

            testImplementation(platform(libs.findLibrary("koin.bom").get()))
            testImplementation(libs.findLibrary("koin.test").get())

        }
    }
}