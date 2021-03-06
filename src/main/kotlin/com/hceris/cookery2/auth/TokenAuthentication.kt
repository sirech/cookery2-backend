package com.hceris.cookery2.auth

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails

class TokenAuthentication(
        private val token: String,
        private val principal: UserDetails) : AbstractAuthenticationToken(principal.authorities) {
    override fun getPrincipal() = principal
    override fun getCredentials() = token
    override fun isAuthenticated() = true
}
