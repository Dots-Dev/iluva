package org.dotsdev.iluva.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val phoneNumber: String? = null,
    val email: String? = null,
    val password: String? = null
)