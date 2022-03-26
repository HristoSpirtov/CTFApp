package com.ctf.ctfserver.service.role;

import com.ctf.ctfserver.domain.entities.Role;
import com.ctf.ctfserver.domain.models.mapper.RoleMapper;
import com.ctf.ctfserver.domain.models.service.RoleServiceModel;
import com.ctf.ctfserver.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.save(new Role("ROLE_USER"));
            this.roleRepository.save(new Role("ROLE_ROOT"));
        }
    }

    @Override
    public Collection<RoleServiceModel> finAllRoles() {
        return this.roleRepository.findAll().stream()
                .map(r -> RoleMapper.INSTANCE.roleToRoleServiceModel(r))
                .collect(Collectors.toList());
    }

    @Override
    public RoleServiceModel findByRole(String role) {
        return RoleMapper.INSTANCE.roleToRoleServiceModel(roleRepository.findByRole(role));
    }
}
