package org.dotsdev.iluva.router

import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.resource
import io.ktor.server.routing.Route
import org.dotsdev.iluva.AuthService
import org.dotsdev.iluva.dto.request.LoginRequest
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI


fun Route.authRouter() {
    val controller by closestDI().instance<AuthService>()
    resource<Auth> {
        post<Auth.Login>({
            description = "Login Endpoint"
            request {
                HttpStatusCode.OK to body<LoginRequest> {
                    required = true
                    example("Email Login", LoginRequest("mail@mail.com", "123456"))
                }
            }
            response {

            }
        }) { controller.login(context) }
        post<Auth.Register>({}) { controller.register(context) }
    }
}