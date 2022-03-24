package com.ctf.ctfserver.service;

import com.ctf.ctfserver.domain.User;
import com.ctf.ctfserver.domain.UserPrincipal;
import com.ctf.ctfserver.exception.domain.UserNotFoundException;
import com.ctf.ctfserver.exception.domain.UsernameExistsException;
import com.ctf.ctfserver.repository.UserRepository;
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

import static com.ctf.ctfserver.enumeration.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        user.getAuthorities().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        return new UserPrincipal(user);
    }

//    @Override
//    public User saveUser(User user) {
//        log.info("Saving new user {} to the database", user.getName());
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }

//    @Override
//    public Role saveRole(Role role) {
//        log.info("Saving new role {} to the database", role.getName());
//        return roleRepository.save(role);
//    }

//    @Override
//    public void addRoleToUser(String username, String roleName) {
//        log.info("Adding role {} to user {}", roleName, username);
//        User user = userRepository.findByUsername(username);
//        Role role = roleRepository.findByName(roleName);
//        user.getRoles().add(role);
//    }

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
    public User register(String name, String username) throws UserNotFoundException, UsernameExistsException {
        validateNewUsername("", username);
        User user = new User();
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        user.setName(name);
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        userRepository.save(user);
        log.info("New user password: " + password);
        return user;
    }

    private String encodePassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }


    private User validateNewUsername(String currentUsername, String newUsername) throws UserNotFoundException, UsernameExistsException {
        User userByNewUsername = findUserByUsername(newUsername);

        if(StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null) {
                throw new UserNotFoundException("No user found bu username" + currentUsername);
            }
            if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistsException("Username already exists");
            }
            return currentUser;
        } else {
            if(userByNewUsername != null) {
                throw new UsernameExistsException("username already exists");
            }
            return null;
        }
    }


}
