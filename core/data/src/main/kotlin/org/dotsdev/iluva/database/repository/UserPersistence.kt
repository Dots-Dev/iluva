package org.dotsdev.iluva.database.repository

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import exception.AppException
import java.util.UUID
import kotlinx.datetime.Clock
import org.dotsdev.iluva.User
import org.dotsdev.iluva.database.DatabaseFactory
import org.dotsdev.iluva.database.entity.UserEntity
import org.dotsdev.iluva.database.table.UserTable
import org.jetbrains.exposed.sql.or
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.postgresql.util.PSQLException
import org.postgresql.util.PSQLState

class UserPersistence : KoinComponent {

    val db: DatabaseFactory by inject()
    suspend fun create(params: User): Either<Exception, User> = Either.catchOrThrow<PSQLException, User> {
        db.execute {
            UserEntity.new {
                firstName = params.firstName.orEmpty()
                lastName = params.lastName
                email = params.email
                phoneNumber = params.phoneNumber
                password = params.password
                createdAt = Clock.System.now()
            }
        }.toDomain()
    }.mapLeft {
        if (it.sqlState == PSQLState.UNIQUE_VIOLATION.state) AppException.Duplicate(
            if (params.email.isNullOrBlank()) "User with ${params.phoneNumber} already exists"
            else "User with ${params.email} already exists"
        )
        else throw it
    }

    suspend fun find(value: String): Either<Exception, User> = either {
        val user = db.execute {
            UserEntity.find {
                UserTable.email eq value or
                        (UserTable.phoneNumber eq value) or
                        (UserTable.id eq UUID.fromString(value))
            }.firstOrNull()
        }?.toDomain()
        ensureNotNull(user) { AppException.NotFound("User with $value attributes not found") }
    }

}
