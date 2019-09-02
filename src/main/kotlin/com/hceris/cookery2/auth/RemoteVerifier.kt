package com.hceris.cookery2.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey

/**
 * Implements [Verifier] by using a [JWKSet] keySet to verify the signature of the provided token
 */
class RemoteVerifier(private val keySet: JWKSet, private val leeway: Long = 10) : Verifier {
    private fun key() = keySet.keys.first() as RSAKey
    private fun algorithm(key: RSAKey) = Algorithm.RSA256(key.toRSAPublicKey(), null)
    private fun verifier(algorithm: Algorithm) = JWT
            .require(algorithm)
            .acceptExpiresAt(leeway)
            .build()

    override fun verify(jwt: String): TokenAuthentication? {
        return verifier(algorithm(key())).run {
            try {
                verify(jwt).asToken()
            } catch (e: JWTVerificationException) {
                null
            }
        }
    }

    private fun DecodedJWT.scopes() = getClaim("scope")
            .asString()
            .split(" ")

    private fun DecodedJWT.asToken() =
            TokenAuthentication(token, User(subject, scopes()))
}
