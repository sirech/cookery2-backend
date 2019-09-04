package com.hceris.cookery2.auth

import com.hceris.cookery2.asStream
import com.hceris.cookery2.readTextAndClose
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletResponse

@ExtendWith(MockKExtension::class)
internal class JwtAuthorizationFilterTest {
    val request = MockHttpServletRequest()

    @MockK
    lateinit var response: HttpServletResponse
    @RelaxedMockK
    lateinit var filterChain: FilterChain

    @MockK
    lateinit var verifier: Verifier
    val jwt = "jwt".asStream().readTextAndClose()
    val token = TokenAuthentication(jwt, User("me", listOf("do:stuff")))

    lateinit var subject: JwtAuthorizationFilter

    @BeforeEach
    fun setUp() {
        SecurityContextHolder.getContext().authentication = null
        subject = JwtAuthorizationFilter(verifier)
    }

    @Test
    fun `does not do anything if there is no authorization header`() {
        subject.doFilter(request, response, filterChain)
        expectThat(SecurityContextHolder.getContext().authentication).isNull()
    }

    @Test
    fun `does not do anything if the token cannot be verified`() {
        every { verifier.verify(jwt) } returns null
        subject.doFilter(request, response, filterChain)
        expectThat(SecurityContextHolder.getContext().authentication).isNull()
    }

    @Test
    fun `does not do anything if the token is malformed`() {
        request.addHeader(Headers.AUTHORIZATION, "Bearer: $jwt")
        subject.doFilter(request, response, filterChain)
        expectThat(SecurityContextHolder.getContext().authentication).isNull()
    }

    @Test
    fun `sets the authorization if there is a proper header`() {
        request.addHeader(Headers.AUTHORIZATION, "Bearer $jwt")
        every { verifier.verify(jwt) } returns token

        subject.doFilter(request, response, filterChain)
        expectThat(SecurityContextHolder.getContext().authentication)
                .isNotNull()
                .isEqualTo(token)
    }

    @Test
    fun `calls the next step in the filter`() {
        subject.doFilter(request, response, filterChain)
        verify { filterChain.doFilter(any(), response) }
    }
}
