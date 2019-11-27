package com.hceris.cookery2.auth

import arrow.fx.IO

/**
 * Base interface for a class that can verify JWT tokens
 */
interface Verifier {
    /**
     * @param jwt a jwt token
     * @return whether the token is valid or not
     */
    fun verify(jwt: String): IO<TokenAuthentication>
}
