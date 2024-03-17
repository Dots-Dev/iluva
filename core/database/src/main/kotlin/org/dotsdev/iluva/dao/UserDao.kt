package org.dotsdev.iluva.dao

import java.util.UUID
import org.dotsdev.liuva.User

interface UserDao {
    suspend fun storeUser(
        email: String?,
        phoneNumber: String?,
        firstName: String,
        lastName: String?,
        password: String?
    ): User

    suspend fun findByID(userId: UUID): User?
    suspend fun findByEmail(email: String): User?
    suspend fun findByPhoneNumber(phoneNumber: String): User?
    suspend fun isUserRegistered(email: String?, phoneNumber: String?): Boolean
}