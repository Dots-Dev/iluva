
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class IluvaTestConventionPlugin :Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        pluginManager.apply("io.kotest")

        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }

        dependencies {
            testImplementation(libs.findLibrary("ktor.server.tests.jvm").get())
            testImplementation(libs.findLibrary("kotlin.test.junit").get())
            testImplementation(libs.findBundle("kotest").get())
        }

    }
}