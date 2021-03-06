package com.ctf.ctfserver.domain.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
public class UserServiceModel {
    private String id;
    private String name;
    private String school;
    private String username;
    private String password;
    private String email;
    private boolean isVerified;
    private boolean isHidden;
    private boolean isBanned;
    private Collection<RoleServiceModel> roles;
}
