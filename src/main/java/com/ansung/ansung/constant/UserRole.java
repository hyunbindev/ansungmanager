package com.ansung.ansung.constant;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
	ROLE_MANAGER;
	public GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }
}