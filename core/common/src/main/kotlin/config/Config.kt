package config

data class Config(
    val env: String,
    val database: DatabaseConfig,
    val jwt: JWTConfig
)