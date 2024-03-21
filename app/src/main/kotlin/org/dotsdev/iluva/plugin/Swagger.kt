package org.dotsdev.iluva.plugin

import com.github.ricky12awesome.jss.encodeToSchema
import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.server.application.Application
import io.ktor.server.application.install
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

fun Application.configureSwaggerUI() {
    val json = Json {
        prettyPrint = true
        encodeDefaults = true
    }
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "api/docs"
            forwardRoot = false
        }
        specAssigner = { _, _ -> "v1" }
        spec("v1") {
            info {
                version = "1.0.0"
            }
        }
        info {
            title = "Iluva API Docs"
            version = "1.0.0"
            description = "Iluva Api Documentation"
        }
        encoding {
            schemaEncoder { type ->
                json.encodeToSchema(serializer(type), generateDefinitions = false)
            }
            exampleEncoder { type, example ->
                json.encodeToString(serializer(type!!), example)
            }
        }
        server {
            url = "http://localhost:8080"
            description = "Development Server"
        }
    }
}