package org.dotsdev.iluva.database

import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException
import config.Config
import config.DatabaseConfig
import config.DatabaseType
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import org.dotsdev.iluva.JWTConfig

class IluvaDatabaseTest : BehaviorSpec({
    val baseConfig = Config(
        env = "test",
        database = DatabaseConfig(
            host = "localhost",
            maxPollSize = 10,
            password = "postgres",
            name = "postgres",
            schema = "iluva",
            type = DatabaseType.POSTGRES,
            username = "postgres",
            port = "5432",
            isReadOnly = false
        ),
        jwt = JWTConfig(
            secret = "secret_key",
            issuer = "issuer",
            realm = "realm",
            audience = "audience"
        )
    )
    context("should connect to database") {
        given("valid config object") {
            `when`("initiate database") {
                val db = DatabaseFactoryImpl(baseConfig).testInitDatabase()
                then("database should not be null") {
                    db shouldNotBe null
                }
            }
        }
    }
    context("should throws an exception") {
        given("invalid database credentials") {
            val invalidCreds = baseConfig.database.copy(username = "invalid", password = "invalid")
            `when`("initiate database") {
                then("throws an exception") {
                    shouldThrow<PoolInitializationException> {
                        DatabaseFactoryImpl(baseConfig.copy(database = invalidCreds))
                    }
                }
            }
        }
    }
})