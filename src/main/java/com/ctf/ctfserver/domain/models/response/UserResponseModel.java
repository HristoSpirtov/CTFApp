package com.ctf.ctfserver.domain.models.response;

import com.ctf.ctfserver.domain.models.service.RoleServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
public class UserResponseModel {
    private String id;
    private String name;
    private String school;
    private String username;
    private String email;
    private boolean isVerified;
    private boolean isHidden;
    private boolean isBanned;
    private Collection<RoleServiceModel> roles;
}
