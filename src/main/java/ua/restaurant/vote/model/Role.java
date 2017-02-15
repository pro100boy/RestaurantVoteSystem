package ua.restaurant.vote.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * User: Galushkin Pavel
 * Date: 15.02.2017
 */

public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
