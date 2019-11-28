package com.hceris.cookery2.auth

import arrow.core.Either
import com.auth0.jwt.exceptions.JWTVerificationException

/**
 * Base interface for a class that can verify JWT tokens
 */
interface Verifier {
    /**
     * @param jwt a jwt token
     * @return whether the token is valid or not
     */
    fun verify(jwt: String): Either<JWTVerificationException, TokenAuthentication>
}
