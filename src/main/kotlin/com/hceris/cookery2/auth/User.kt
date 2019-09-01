package com.hceris.cookery2.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class User: UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf<GrantedAuthority>()
    }

    override fun isEnabled() = true

    override fun getUsername(): String {
        return "dude"
    }

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = ""

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}
