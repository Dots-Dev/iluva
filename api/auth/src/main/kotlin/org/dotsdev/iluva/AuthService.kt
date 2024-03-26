package org.dotsdev.iluva

import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import org.dotsdev.iluva.dto.request.RegisterRequest
import org.slf4j.LoggerFactory

class AuthService(
    private val tokenProvider: TokenProvider,
    private val passwordEncryptor: PasswordEncryptor,
    private val authRepository: AuthRepository,
) {
    private val logger by lazy { LoggerFactory.getLogger(this::class.java) }

    suspend fun register(call: ApplicationCall) {
        val request = call.receive<RegisterRequest>()
    }

}