package org.dotsdev.iluva

import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import model.HttpResponse
import org.dotsdev.iluva.dto.request.LoginRequest
import org.dotsdev.iluva.dto.request.RegisterRequest
import org.slf4j.LoggerFactory
import so.kciter.thing.validator.ValidationResult

class AuthController(
    private val tokenProvider: TokenProvider,
    private val passwordEncryptor: IPasswordEncryptor,
    private val authRepository: AuthRepository,
) {
    private val logger by lazy { LoggerFactory.getLogger(this::class.java) }
    suspend fun login(call: ApplicationCall) {
        val request = call.receive<LoginRequest>()

        request.normalize()

        when (val result = request.validate()) {
            is ValidationResult.Valid -> {
                val user = authRepository.findUser(request)

                user.fold({ throw it }) { call.respond(HttpResponse.ok(it)) }
            }

            is ValidationResult.Invalid -> {
                throw BadRequestException(result.errors.first().message)
            }
        }
    }

    suspend fun register(call: ApplicationCall) {
        val request = call.receive<RegisterRequest>()

        request.normalize()

        when (val result = request.validate()) {
            is ValidationResult.Valid -> {
                val newUser = authRepository.createUser(request)

                newUser.fold({ throw it }) { call.respond(HttpResponse.ok(it)) }
            }
            is ValidationResult.Invalid -> throw BadRequestException(result.errors.first().message)
        }
    }

}