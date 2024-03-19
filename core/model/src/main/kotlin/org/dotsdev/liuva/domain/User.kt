package org.dotsdev.liuva.domain

import java.util.UUID

data class User(
    val id: UUID,
    val firstName: String,
    val lastName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val avatar: String? = null
)

typealias Users = List<User>