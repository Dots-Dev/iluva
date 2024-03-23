package org.dotsdev.iluva.database.table

import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object UserTable : UUIDTable("users") {
    val firstName: Column<String> = varchar("first_name", 50)
    val lastName: Column<String?> = varchar("last_name", 50).nullable()
    val email: Column<String?> = varchar("email", 50).uniqueIndex().nullable()
    val password: Column<String?> = varchar("password", 255).nullable()
    val phoneNumber: Column<String?> = varchar("phone_number", 20).uniqueIndex().nullable()
    val avatar: Column<String?> = text("avatar").nullable()
    val createdAt: Column<Instant> = timestamp("created_at")
    val updatedAt: Column<Instant?> = timestamp("updated_at").nullable()
}