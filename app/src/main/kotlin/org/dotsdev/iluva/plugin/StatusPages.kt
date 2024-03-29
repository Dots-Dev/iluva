package org.dotsdev.iluva.plugin

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.httpMethod
import io.ktor.server.response.respond
import model.HttpResponse

fun Application.configureStatusPage() {
    install(StatusPages) {
        status(HttpStatusCode.MethodNotAllowed) { call, _ ->
            call.respond(
                HttpStatusCode.MethodNotAllowed,
                HttpResponse.error(
                    "Method ${call.request.httpMethod.value} is not allowed",
                    HttpStatusCode.MethodNotAllowed
                )
            )
        }
        status(HttpStatusCode.UnsupportedMediaType) { call, cause ->
            call.respond(
                HttpStatusCode.UnsupportedMediaType,
                HttpResponse.error(cause.description, HttpStatusCode.UnsupportedMediaType)
            )
        }
        exception<BadRequestException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, HttpResponse.badRequest(cause.message.orEmpty()))
        }
        status(HttpStatusCode.Unauthorized) { call, _ ->
            call.respond(HttpStatusCode.Unauthorized, HttpResponse.unauthorized("Unauthorized"))
        }
    }
}