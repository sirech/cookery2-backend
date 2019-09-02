package com.hceris.cookery2.auth

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthorizationFilter(val verifier: Verifier) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        request.getHeader(Headers.AUTHORIZATION)?.let { header ->
            jwt(header)?.let { jwt ->
                authentication(jwt)?.let { auth ->
                    SecurityContextHolder.getContext().authentication = auth
                }
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun jwt(header: String): String? {
        if (!header.startsWith("Bearer ")) {
            return null
        }

        return header.split(" ").last()
    }

    private fun authentication(jwt: String): TokenAuthentication? {
        return verifier.verify(jwt)
    }
}
