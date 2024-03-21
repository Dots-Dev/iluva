package org.dotsdev.iluva

import java.util.UUID

data class User(
    val id: UUID? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val password: String? = null,
    val avatar: String? = null,
) {
    fun create(
        firstName: String,
        lastName: String?,
        email: String? = null,
        password: String? = null,
        phoneNumber: String? = null,
        avatar: String? = null,
    ) = User(
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        password = password,
        avatar = avatar
    )
}

typealias Users = List<User>