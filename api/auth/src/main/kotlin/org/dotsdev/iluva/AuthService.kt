package org.dotsdev.iluva

import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import model.HttpResponse
import org.dotsdev.iluva.dto.request.LoginRequest
import org.dotsdev.iluva.dto.request.RegisterRequest
import org.dotsdev.iluva.dto.response.AuthResponse
import org.slf4j.LoggerFactory
import so.kciter.thing.validator.ValidationResult

class AuthService(
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
                val user = authRepository.findUserByEmail(request)

                user.fold({ throw it }) {
                    if (passwordEncryptor.validate(request.password, it.password.orEmpty()).not()) {
                        throw BadRequestException("Invalid credentials")
                    }
                    val token = tokenProvider.create(it.id.toString())
                    call.respond(HttpResponse.ok(AuthResponse(token.accessToken, token.refreshToken)))
                }
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
                val hashedPassword = passwordEncryptor.encrypt(request.password)
                val newUser = authRepository.createUser(request.copy(password = hashedPassword))

                newUser.fold({ throw it }) {
                    val token = tokenProvider.create(it)
                    call.respond(HttpResponse.ok(AuthResponse(token.accessToken, token.refreshToken)))
                }
            }
            is ValidationResult.Invalid -> throw BadRequestException(result.errors.first().message)
        }
    }

}