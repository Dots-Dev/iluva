package org.dotsdev.iluva.transaction

import org.dotsdev.iluva.dbQuery
import org.dotsdev.iluva.table.UserTable
import org.dotsdev.liuva.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.upsert

class UserTransaction {
    suspend fun create(
        firstName: String,
        lastName: String?,
        email: String?,
        phoneNumber: String?
    ): String = dbQuery {
        UserTable.upsert {
            it[firstName] = firstName
            it[lastName] = lastName
            it[email] = email
            it[phoneNumber] = phoneNumber
        }
    }[UserTable.id].value.toString()

    suspend fun findByID(value: String): User? {
        TODO("Not yet implemented")
    }

    suspend fun findByPhoneNumber(value: String): User? {
        TODO("Not yet implemented")
    }

    suspend fun findByEmail(value: String): User? {
        TODO("Not yet implemented")
    }

}
