package config

import org.dotsdev.iluva.JWTConfig

data class Config(
    val env: String,
    val database: DatabaseConfig,
    val jwt: JWTConfig
)