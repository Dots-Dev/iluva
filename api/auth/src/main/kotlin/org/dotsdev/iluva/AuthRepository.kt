package org.dotsdev.iluva

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.ensureNotNull
import org.dotsdev.iluva.database.transaction.UserTransaction
import org.dotsdev.iluva.dto.request.LoginRequest
import org.dotsdev.iluva.dto.request.RegisterRequest
import org.dotsdev.iluva.exception.UserAlreadyRegisteredException
import org.dotsdev.iluva.exception.UserNotFoundException

class AuthRepository(private val userTransaction: UserTransaction) {
    suspend fun findUserByEmail(request: LoginRequest): Either<Exception, User> = either {
        ensureNotNull(userTransaction.findByEmail(request.email)) { UserNotFoundException("User not found") }
    }

    suspend fun findUserById(id: String): Either<Exception, User> = either {
        ensureNotNull(userTransaction.findByID(id)) { UserNotFoundException("User not found") }
    }

    suspend fun createUser(request: RegisterRequest): Either<Exception, String> = either {
        ensure(userTransaction.isExists(request.email).not()) { UserAlreadyRegisteredException("User already registered") }
        userTransaction.create(request.toDomain())
    }
}