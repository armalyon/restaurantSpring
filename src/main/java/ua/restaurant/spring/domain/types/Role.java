package ua.restaurant.spring.domain.types;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    SUPERADMIN,
    ADMIN,
    CLIENT;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
