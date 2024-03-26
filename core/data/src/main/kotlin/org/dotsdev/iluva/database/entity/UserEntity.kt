package org.dotsdev.iluva.database.entity

import java.util.UUID
import org.dotsdev.iluva.User
import org.dotsdev.iluva.database.table.StoreTable
import org.dotsdev.iluva.database.table.UserTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(UserTable)

    var firstName by UserTable.firstName
    var lastName by UserTable.lastName
    var email by UserTable.email
    var password by UserTable.password
    var phoneNumber by UserTable.phoneNumber
    var avatar by UserTable.avatar
    var createdAt by UserTable.createdAt
    var updatedAt by UserTable.updatedAt
    val stores by StoreEntity referrersOn StoreTable.user

    fun toDomain() = User(
        id = id.value,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        password = password,
        avatar = avatar
    )
}