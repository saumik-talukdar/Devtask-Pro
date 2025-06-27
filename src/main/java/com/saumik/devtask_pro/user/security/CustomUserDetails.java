package com.saumik.devtask_pro.user.security;

import com.saumik.devtask_pro.user.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@SuppressWarnings("serial")
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public User getUserEntity() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().name())
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // Spring will still call this even if login used email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Could customize later
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Could integrate with lockout feature
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Can be expanded with password expiration policy
    }

    @Override
    public boolean isEnabled() {
        return true; // Add isActive field to User if needed
    }
}
