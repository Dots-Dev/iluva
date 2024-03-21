package org.dotsdev.iluva.exception

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.respond
import model.HttpResponse

fun StatusPagesConfig.authStatusPages() {
    exception<UserNotFoundException> { call, cause ->
        call.respond(HttpResponse.notFound(cause.message.orEmpty()))
    }
    exception<UserAlreadyRegisteredException> { call, cause ->
        call.respond(
            HttpStatusCode.Conflict,
            HttpResponse.error(cause.message.orEmpty(), code = HttpStatusCode.Conflict)
        )
    }
}