package org.dotsdev.iluva.database.transaction

import extension.isNotNull
import java.util.UUID
import kotlinx.datetime.Clock
import org.dotsdev.iluva.User
import org.dotsdev.iluva.database.dbQuery
import org.dotsdev.iluva.database.entity.UserEntity
import org.dotsdev.iluva.database.table.UserTable
import org.jetbrains.exposed.sql.or


class UserTransaction {
    suspend fun create(params: User): User = dbQuery {
        UserEntity.new {
            firstName = params.firstName.orEmpty()
            lastName = params.lastName
            email = params.email
            password = params.password
            phoneNumber = params.phoneNumber
            createdAt = Clock.System.now()
        }
    }.toDomain()

    suspend fun findByID(value: String): User? = dbQuery {
        UserEntity.findById(UUID.fromString(value))
    }?.toDomain()

    suspend fun findByPhoneNumber(value: String): User? = dbQuery {
        UserEntity.find { UserTable.phoneNumber eq value }.firstOrNull()
    }?.toDomain()

    suspend fun findByEmail(value: String): User? = dbQuery {
        UserEntity.find { UserTable.email eq value }.firstOrNull()
    }?.toDomain()

    suspend fun isExists(value: String): Boolean = dbQuery {
        UserEntity.find { UserTable.email eq value or(UserTable.phoneNumber eq value) }.firstOrNull()
    }.isNotNull()

}
