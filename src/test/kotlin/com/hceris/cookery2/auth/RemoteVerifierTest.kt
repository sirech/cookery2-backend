package com.hceris.cookery2.auth

import arrow.core.None
import arrow.core.Some
import com.hceris.cookery2.asStream
import com.hceris.cookery2.readTextAndClose
import com.nimbusds.jose.jwk.JWKSet
import org.junit.jupiter.api.Test
import org.springframework.security.core.Authentication
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isA
import strikt.assertions.isEqualTo

internal class RemoteVerifierTest {

    val jwt = "jwt".asStream().readTextAndClose()
    val keySet = JWKSet.load("jwks.json".asStream())

    @Test
    fun `verify does not work with a invalid jwt token`() {
        expectThat(RemoteVerifier(keySet).verify(jwt)).isA<None>()
    }

    @Test
    fun `verify does not work if no token was passed`() {
        expectThat(RemoteVerifier(keySet).verify("")).isA<None>()
    }

    @Test
    fun `verify works if the expiration is not taken into account`() {
        val hundredYears = 3600L * 24 * 365 * 100
        expectThat(RemoteVerifier(keySet, hundredYears).verify(jwt)).isA<Some<Authentication>>().get { t }.and {
            get { name }.isEqualTo("google-oauth2|111460419457288935787")
            get { authorities.map { it.authority } }.contains("create:recipes")
        }
    }
}
