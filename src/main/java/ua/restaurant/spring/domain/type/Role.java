package ua.restaurant.spring.domain.type;

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
