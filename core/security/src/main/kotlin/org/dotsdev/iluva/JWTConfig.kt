package org.dotsdev.iluva

data class JWTConfig(
    val issuer: String,
    val secret: String,
    val realm: String,
    val audience: String
)
