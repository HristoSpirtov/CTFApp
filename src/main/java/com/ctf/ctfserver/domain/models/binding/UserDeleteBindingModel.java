package com.ctf.ctfserver.domain.models.binding;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDeleteBindingModel {
    private String id;
    private String name;
    private String school;
    private String username;
    private String email;
    private boolean isVerified;
    private boolean isHidden;
    private boolean isBanned;
}
