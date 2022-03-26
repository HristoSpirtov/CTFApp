package com.ctf.ctfserver.service.role;


import com.ctf.ctfserver.domain.models.service.RoleServiceModel;

import java.util.Collection;

public interface RoleService {
    void seedRolesInDb();

    Collection<RoleServiceModel> finAllRoles();

    RoleServiceModel findByRole(String role);
}
