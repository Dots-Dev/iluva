package org.dotsdev.iluva.plugin

import arrow.core.Either
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import config.Config
import io.ktor.http.HttpStatusCode
import io.ktor.http.auth.HttpAuthHeader
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.auth.parseAuthorizationHeader
import io.ktor.server.response.respond
import org.dotsdev.iluva.TokenProvider
import org.dotsdev.iluva.database.repository.UserPersistence
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.koin

fun Application.configureAuthentication() {
    koin {
        modules(appModule)
    }

    val config by inject<Config>()
    val helper by inject<TokenProvider>()
    val userPersistence by inject<UserPersistence>()

    install(Authentication) {
        jwt {
            realm = config.jwt.realm
            verifier(helper.verifier)
            challenge { _, _ ->
                val header = Either.catch { (call.request.parseAuthorizationHeader() as? HttpAuthHeader.Single) }
                    .getOrNull()?.blob
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
//            validate { credential ->
//                credential.payload.getClaim(helper.userIdClaim).asString()?.let { id ->
//                    val user = userPersistence.find(id)
//                    user?.let {
//                        UserPrincipal(it)
//                    }
//                }
//            }
        }
    }
}