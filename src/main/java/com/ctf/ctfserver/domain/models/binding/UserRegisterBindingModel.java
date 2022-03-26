package com.ctf.ctfserver.domain.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRegisterBindingModel {

    private String name;
    private String school;
    private String username;
    private String password;
    private String email;

}
