package com.ctf.ctfserver.repository;

import com.ctf.ctfserver.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByRole(String role);
}
