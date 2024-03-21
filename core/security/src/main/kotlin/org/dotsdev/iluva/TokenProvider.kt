package org.dotsdev.iluva

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import config.JWTConfig
import io.ktor.server.auth.jwt.JWTCredential
import java.util.Date

interface ITokenProvider {
    fun verifyType(token: String): String
    fun verify(token: String): String?
    fun expiration(token: String): Date
    fun isAudienceMatches(credential: JWTCredential): Boolean
}

enum class TokenType { ACCESS, REFRESH }

class TokenProvider(private val config: JWTConfig) : ITokenProvider {

    val verifier: JWTVerifier = JWT.require(Algorithm.HMAC512(config.secret))
        .withAudience(config.audience)
        .withIssuer(config.issuer)
        .build()

    private val algorithm = Algorithm.HMAC512(config.secret)
    private val validityInMs: Long = 1200000L // 20 minutes
    private val refreshValidityInMs: Long = 3600000L * 24L * 30L // 30 days
    private val authSubject = "Authentication"
    private val tokenTypeClaim = "TokenType"
    val userIdClaim = "UserID"

    override fun verifyType(token: String): String = verifier.verify(token).claims[tokenTypeClaim]!!.asString()

    override fun verify(token: String): String? = verifier.verify(token).claims[userIdClaim]?.asString()

    override fun expiration(token: String): Date = verifier.verify(token).expiresAt

    override fun isAudienceMatches(credential: JWTCredential) = credential.payload.audience.contains(config.audience)

    private fun createAccessToken(id: String, expiration: Date) = JWT.create()
        .withSubject(authSubject)
        .withAudience(config.audience)
        .withIssuer(config.issuer)
        .withClaim(userIdClaim, id)
        .withClaim(tokenTypeClaim, TokenType.ACCESS.name)
        .withExpiresAt(expiration)
        .sign(algorithm)

    private fun createRefreshToken(id: String, expiration: Date) = JWT.create()
        .withSubject(authSubject)
        .withAudience(config.audience)
        .withIssuer(config.issuer)
        .withClaim(userIdClaim, id)
        .withClaim(tokenTypeClaim, TokenType.REFRESH.name)
        .withExpiresAt(expiration)
        .sign(algorithm)

    private fun getExpiration(validity: Long = validityInMs) = Date(System.currentTimeMillis() + validity)
}