package org.dotsdev.iluva.router

import io.github.smiley4.ktorswaggerui.dsl.resources.post
import io.ktor.server.resources.resource
import io.ktor.server.routing.Route
import org.dotsdev.iluva.AuthService
import org.koin.ktor.ext.inject


fun Route.authRouter() {
    val service by inject<AuthService>()
    resource<Auth> {
//        post<Auth.Login>({
//            description = "Login Endpoint"
//            request {
//                HttpStatusCode.OK to body<LoginWithEmailRequest> {
//                    required = true
//                    example("Email Login", LoginWithEmailRequest("mail@mail.com", "123456"))
//                }
//            }
//            response {
//
//            }
//        }) { service.login(context) }
        post<Auth.Register>({}) { service.register(context) }
    }
}