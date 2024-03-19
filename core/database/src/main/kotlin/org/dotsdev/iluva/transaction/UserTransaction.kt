package org.dotsdev.iluva.transaction

import java.util.UUID
import org.dotsdev.iluva.dbQuery
import org.dotsdev.iluva.entity.UserEntity
import org.dotsdev.iluva.table.UserTable
import org.dotsdev.liuva.domain.User
import org.dotsdev.liuva.request.user.CreateUserRequest
import org.jetbrains.exposed.sql.or


class UserTransaction {
    suspend fun create(params: CreateUserRequest): User = dbQuery {
        UserEntity.new {
            firstName = params.firstName
            lastName = params.lastName
            email = params.email
            password = params.password
            phoneNumber = params.phoneNumber
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
        UserEntity.find { UserTable.email eq value or(UserTable.phoneNumber eq value) }.empty()
    }.not()

}
