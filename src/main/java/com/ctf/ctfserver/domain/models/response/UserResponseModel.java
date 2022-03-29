package com.ctf.ctfserver.domain.models.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserResponseModel {
    private String id;
    private String name;
    private String school;
    private String username;
    private String email;
}
