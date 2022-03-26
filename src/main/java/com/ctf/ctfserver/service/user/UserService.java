package com.ctf.ctfserver.service.user;

import com.ctf.ctfserver.domain.entities.User;
import com.ctf.ctfserver.domain.models.service.UserServiceModel;
import com.ctf.ctfserver.exception.domain.UserNotFoundException;
import com.ctf.ctfserver.exception.domain.UsernameExistsException;

import java.util.List;

public interface UserService {
//    User saveUser(User user);
//    Role saveRole(Role role);
//    void addRoleToUser(String username, String roleName);
    User findUserByUsername(String username);
    List<User> getUsers();

    UserServiceModel register(UserServiceModel userServiceModel) throws UserNotFoundException, UsernameExistsException;
}
