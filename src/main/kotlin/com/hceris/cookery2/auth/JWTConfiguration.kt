package com.hceris.cookery2.auth

import arrow.core.toOption
import com.nimbusds.jose.jwk.JWKSet
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URL

@Configuration
class JWTConfiguration {
    @Value("\${auth.jwks}")
    lateinit var jwks: String

    @ConditionalOnProperty(value = ["auth.enabled"], havingValue = "true")
    @Bean
    fun verifier(): Verifier {
        val keySet = JWKSet.load(URL(jwks))
        return RemoteVerifier(keySet)
    }

    @ConditionalOnProperty(value = ["auth.enabled"], havingValue = "false")
    @Bean("verifier")
    fun verifierMock() = object: Verifier {
        override fun verify(jwt: String) =
                TokenAuthentication(jwt, User("me", listOf("profiles", "create:recipes"))).toOption()
    }
}
