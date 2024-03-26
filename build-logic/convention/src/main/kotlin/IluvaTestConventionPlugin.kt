import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class IluvaTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("io.kotest")

        dependencies {
            testImplementation(libs.findBundle("kotest").get())
            testImplementation(libs.findLibrary("kotlin.test.junit").get())
            testImplementation(libs.findLibrary("mockk").get())
        }
    }
}