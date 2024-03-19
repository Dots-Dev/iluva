package org.dotsdev.liuva.request.user

data class CreateUserRequest(
    val firstName: String,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val phoneNumber: String? = null
)