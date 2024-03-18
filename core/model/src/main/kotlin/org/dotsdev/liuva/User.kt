package org.dotsdev.liuva

data class User(
    val id: String,
    val firstName: String,
    val lastName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val avatar: String? = null
)

typealias Users = List<User>