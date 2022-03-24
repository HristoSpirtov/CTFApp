package com.ctf.ctfserver.service;

import com.ctf.ctfserver.domain.User;
import com.ctf.ctfserver.exception.domain.UserNotFoundException;
import com.ctf.ctfserver.exception.domain.UsernameExistsException;

import java.util.List;

public interface UserService {
//    User saveUser(User user);
//    Role saveRole(Role role);
//    void addRoleToUser(String username, String roleName);
    User findUserByUsername(String username);
    List<User> getUsers();

    User register(String name, String username) throws UserNotFoundException, UsernameExistsException;
}
