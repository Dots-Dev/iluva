package org.dotsdev.iluva.plugin

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.httpMethod
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import model.HttpResponse
import org.dotsdev.iluva.exception.authStatusPages

fun Application.configureStatusPage() {
    install(StatusPages) {
        status(HttpStatusCode.MethodNotAllowed) { call, _ ->
            call.respondText(
                "Method ${call.request.httpMethod.value} is not allowed",
                status = HttpStatusCode.MethodNotAllowed
            )
        }
        exception<BadRequestException> { call, cause ->
            call.respond(HttpResponse.badRequest(cause.message.orEmpty()))
        }
        authStatusPages()
    }
}