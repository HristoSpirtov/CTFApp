package com.ctf.ctfserver.domain.models.mapper;

import com.ctf.ctfserver.domain.entities.Role;
import com.ctf.ctfserver.domain.models.service.RoleServiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleServiceModel roleToRoleServiceModel(Role role);
}
