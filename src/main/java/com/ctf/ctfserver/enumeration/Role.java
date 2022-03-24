package com.ctf.ctfserver.enumeration;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static com.ctf.ctfserver.constant.Authority.*;

@AllArgsConstructor
public enum Role {
    ROLE_USER(USER_AUTHORITIES),
    ROLE_HR(HR_AUTHORITIES),
    ROLE_MANAGER(MANAGER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

    private String[] authorities;

    public List<String> getAuthorities() {
        return Arrays.asList(this.authorities);
    }
}
