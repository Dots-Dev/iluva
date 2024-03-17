package org.dotsdev.iluva.table

import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.dotsdev.iluva.dao.UserDao
import org.dotsdev.iluva.entity.UserEntity
import org.dotsdev.liuva.User
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UserTable : UUIDTable("users"), UserDao {
    val firstName: Column<String> = varchar("first_name", 50)
    val lastName: Column<String?> = varchar("last_name", 50).nullable()
    val email: Column<String?> = varchar("email", 50).uniqueIndex().nullable()
    val password: Column<String?> = varchar("password", 50).nullable()
    val phoneNumber: Column<String?> = varchar("phone_number", 20).uniqueIndex().nullable()
    val avatar: Column<String?> = text("avatar").nullable()
    val createdAt: Column<Instant> = timestamp("created_at")
    val updatedAt: Column<Instant?> = timestamp("updated_at").nullable()

    override suspend fun storeUser(
        email: String?,
        phoneNumber: String?,
        firstName: String,
        lastName: String?,
        password: String?
    ): User =
        newSuspendedTransaction(Dispatchers.IO) {
            UserEntity.new {
                this.firstName = firstName
                this.lastName = lastName
                this.email = email
                this.password = password
                this.phoneNumber = phoneNumber
                this.createdAt = Clock.System.now()
            }.toModel()
        }

    override suspend fun findByID(userId: UUID): User? =
        newSuspendedTransaction(Dispatchers.IO) {
            UserEntity.findById(userId)
        }?.toModel()

    override suspend fun findByEmail(email: String): User? =
        newSuspendedTransaction(Dispatchers.IO) {
            UserEntity.find {
                UserTable.email eq email
            }.firstOrNull()
        }?.toModel()

    override suspend fun findByPhoneNumber(phoneNumber: String): User? =
        newSuspendedTransaction(Dispatchers.IO) {
            UserEntity.find {
                UserTable.phoneNumber eq phoneNumber
            }.firstOrNull()
        }?.toModel()

    override suspend fun isUserRegistered(email: String?, phoneNumber: String?): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            UserEntity.find {
                UserTable.email eq email or (UserTable.phoneNumber eq phoneNumber)
            }.firstOrNull()
        } != null

}