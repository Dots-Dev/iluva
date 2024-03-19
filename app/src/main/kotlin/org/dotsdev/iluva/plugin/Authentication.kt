package org.dotsdev.iluva.plugin

import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import config.Config
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import org.dotsdev.iluva.TokenProvider
import org.dotsdev.iluva.principal.UserPrincipal
import org.dotsdev.iluva.transaction.UserTransaction
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureAuthentication() {
    val config by closestDI().instance<Config>()
    val helper by closestDI().instance<TokenProvider>()
    val userTransaction by closestDI().instance<UserTransaction>()

    install(Authentication) {
        jwt("jwt") {
            realm = config.jwt.realm
            verifier(helper.verifier)
            challenge { _, _ ->
                val header = call.request.headers["Authorization"]
                header?.let {
                    if (it.isNotEmpty()) {
                        try {
                            if (it.contains("Bearer", true).not()) throw JWTDecodeException("")
                            val jwt = it.replace("Bearer ", "")
                            helper.verifier.verify(jwt)
                            ""
                        } catch (e: TokenExpiredException) {
                            call.respond(
                                HttpStatusCode.Unauthorized,
                            )
                        } catch (e: SignatureVerificationException) {
                            call.respond(HttpStatusCode.BadRequest)
                        } catch (e: JWTDecodeException) {
                            call.respond(HttpStatusCode.BadRequest)
                        }
                    } else call.respond(
                        HttpStatusCode.BadRequest
                    )
                } ?: call.respond(
                    HttpStatusCode.Unauthorized
                )
            }
            validate { credential ->
                credential.payload.getClaim(helper.userIdClaim).asString()?.let { id ->
                    val user = userTransaction.findByID(id)
                    user?.let {
                        UserPrincipal(it)
                    }
                }
            }
        }
    }
}