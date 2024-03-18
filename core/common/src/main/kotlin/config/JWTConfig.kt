package config

data class JWTConfig(
    val expiration: Int,
    val issuer: String,
    val secret: String
)
