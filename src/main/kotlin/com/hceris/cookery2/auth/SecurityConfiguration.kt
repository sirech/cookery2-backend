package com.hceris.cookery2.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Configuration
class SecurityConfiguration(
    private val filter: JwtAuthorizationFilter,
    @Value("\${auth.enabled:true}") private val authEnabled: Boolean
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors { }
            .csrf { it.disable() }

        if (!authEnabled) {
            http.authorizeHttpRequests { it.anyRequest().permitAll() }
            return http.build()
        }

        http.authorizeHttpRequests {
            it.requestMatchers(HttpMethod.GET, "/**").permitAll()
                .requestMatchers("/pact").permitAll()
                .anyRequest().hasAuthority("create:recipes")
        }
            .addFilterBefore(filter, BasicAuthenticationFilter::class.java)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        return http.build()
    }
}
