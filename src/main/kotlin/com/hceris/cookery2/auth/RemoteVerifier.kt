package com.hceris.cookery2.auth

import arrow.fx.IO
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey

/**
 * Implements [Verifier] by using a [JWKSet] keySet to verify the signature of the provided token
 */
class RemoteVerifier(private val keySet: JWKSet, private val leeway: Long = 10) : Verifier {
    companion object {
        private fun key(keySet: JWKSet) = keySet.keys.first() as RSAKey
        private fun algorithm(key: RSAKey) = Algorithm.RSA256(key.toRSAPublicKey(), null)
        private fun verifier(algorithm: Algorithm, leeway: Long) = JWT
                .require(algorithm)
                .acceptExpiresAt(leeway)
                .build()

    }

    override fun verify(jwt: String): IO<TokenAuthentication> {
        val key = key(keySet)
        val algorithm = algorithm(key)
        val verifier = verifier(algorithm, leeway)
        return IO { verifier.verify(jwt).asToken() }
    }

}

private fun DecodedJWT.scopes() = getClaim("scope")
        .asString()
        .split(" ")

private fun DecodedJWT.asToken() =
        TokenAuthentication(token, User(subject, scopes()))
