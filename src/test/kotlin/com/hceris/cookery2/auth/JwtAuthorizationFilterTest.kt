package com.hceris.cookery2.auth

import com.hceris.cookery2.asStream
import com.hceris.cookery2.readTextAndClose
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import strikt.api.expectThat
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletResponse

@ExtendWith(MockKExtension::class)
internal class JwtAuthorizationFilterTest {
    val subject = JwtAuthorizationFilter()
    val request = MockHttpServletRequest()

    @MockK
    lateinit var response: HttpServletResponse
    @RelaxedMockK
    lateinit var filterChain: FilterChain

    val jwt = "jwt".asStream().readTextAndClose()

    @BeforeEach
    fun setUp() {
        SecurityContextHolder.getContext().authentication = null
    }

    @Test
    fun `does not do anything if there is no authorization header`() {
        subject.doFilter(request, response, filterChain)
        expectThat(SecurityContextHolder.getContext().authentication).isNull()
    }

    @Test
    fun `sets the authorization if there is a proper header`() {
        request.addHeader(Headers.AUTHORIZATION, "Bearer: $jwt")
        subject.doFilter(request, response, filterChain)
        expectThat(SecurityContextHolder.getContext().authentication).isNotNull()
    }
}
