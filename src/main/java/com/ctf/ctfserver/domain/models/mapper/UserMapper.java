package com.ctf.ctfserver.domain.models.mapper;

import com.ctf.ctfserver.domain.entities.User;
import com.ctf.ctfserver.domain.models.binding.UserRegisterBindingModel;
import com.ctf.ctfserver.domain.models.response.UserResponseModel;
import com.ctf.ctfserver.domain.models.service.UserServiceModel;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserServiceModel userServiceBindingModelToUserServiceModel(UserRegisterBindingModel userRegisterBindingModel);
    User userServiceModelToUser(UserServiceModel userServiceModel);
    UserServiceModel userToUserServiceModel(User user);
    UserResponseModel userToUserResponseModel(User user);
    UserResponseModel userServiceModelToUserResponseModel(UserServiceModel userServiceModel);
}
