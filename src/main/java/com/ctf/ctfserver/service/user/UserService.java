package com.ctf.ctfserver.service.user;

import com.ctf.ctfserver.domain.entities.User;
import com.ctf.ctfserver.domain.models.service.UserServiceModel;
import com.ctf.ctfserver.exception.domain.UserNotFoundException;
import com.ctf.ctfserver.exception.domain.UsernameExistsException;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);
    List<UserServiceModel> getUsers();

    UserServiceModel register(UserServiceModel userServiceModel) throws UserNotFoundException, UsernameExistsException;

    void deleteUsers(List<UserServiceModel> userServiceModels);

    void editUsers(List<UserServiceModel> userServiceModels);

    UserServiceModel getUserById(String id);
}
