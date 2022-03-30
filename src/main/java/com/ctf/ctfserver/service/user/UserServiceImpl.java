package com.ctf.ctfserver.service.user;

import com.ctf.ctfserver.domain.entities.User;
import com.ctf.ctfserver.domain.UserPrincipal;
import com.ctf.ctfserver.domain.models.mapper.UserMapper;
import com.ctf.ctfserver.domain.models.service.UserServiceModel;
import com.ctf.ctfserver.exception.domain.UserNotFoundException;
import com.ctf.ctfserver.exception.domain.UsernameExistsException;
import com.ctf.ctfserver.repository.UserRepository;
import com.ctf.ctfserver.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.info("User {} not found in the database", username);
            throw new UsernameNotFoundException(String.format("User %s not found in the database", username));
        } else {
            log.info("User {} found in the database", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        });
        return new UserPrincipal(user);
    }

    @Override
    public User findUserByUsername(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {

        this.roleService.seedRolesInDb();
        if (this.userRepository.count() == 0) {
            userServiceModel.setRoles(this.roleService.finAllRoles());
        } else {
            userServiceModel.setRoles(new ArrayList<>());
            userServiceModel.getRoles().add(this.roleService.findByRole("ROLE_USER"));
        }
        User user = UserMapper.INSTANCE.userServiceModelToUser(userServiceModel);
        user.setPassword(encodePassword(userServiceModel.getPassword()));

        return UserMapper.INSTANCE.userToUserServiceModel(this.userRepository.save(user));
    }

    private String encodePassword(String password) {
        return this.passwordEncoder.encode(password);
    }


}
