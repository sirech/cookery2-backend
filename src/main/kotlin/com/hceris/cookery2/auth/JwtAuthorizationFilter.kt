package com.hceris.cookery2.auth

import arrow.core.Option
import arrow.core.extensions.fx
import arrow.core.getOrElse
import arrow.core.maybe
import arrow.core.toOption
import arrow.fx.IO
import arrow.fx.extensions.fx
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthorizationFilter(val verifier: Verifier) : OncePerRequestFilter() {
    companion object {
        private fun String.asJwt() = startsWith("Bearer ")
                .maybe { split(" ").last() }
    }

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain) {

        val token = Option.fx {
            val (header) = request.getHeader(Headers.AUTHORIZATION).toOption()
            val (jwt) = header.asJwt()
            val (token) = verifier.verify(jwt).toOption()
            SecurityContextHolder.getContext().authentication = token
        }

        filterChain.doFilter(request, response)
    }
}
