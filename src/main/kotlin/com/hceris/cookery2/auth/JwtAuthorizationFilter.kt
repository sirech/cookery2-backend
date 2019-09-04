package com.hceris.cookery2.auth

import arrow.core.None
import arrow.core.Some
import arrow.core.extensions.option.monad.binding
import arrow.core.toOption
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthorizationFilter(val verifier: Verifier) : OncePerRequestFilter() {
    companion object {
        private fun String.asJwt() = if (startsWith("Bearer "))
            Some(split(" ").last())
        else
            None
    }

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain) {

        binding {
            val (header) = request.getHeader(Headers.AUTHORIZATION).toOption()
            val (jwt) = header.asJwt()
            val (token) = authentication(jwt).toOption()
            SecurityContextHolder.getContext().authentication = token
        }

        filterChain.doFilter(request, response)
    }


    private fun authentication(jwt: String): TokenAuthentication? {
        return verifier.verify(jwt)
    }
}
