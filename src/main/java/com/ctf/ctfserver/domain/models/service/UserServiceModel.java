package com.ctf.ctfserver.domain.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Collection<RoleServiceModel> roles;
}
