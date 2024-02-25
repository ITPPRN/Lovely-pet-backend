package com.example.lovelypet.mapper;

import com.example.lovelypet.entity.User;
import com.example.lovelypet.model.UserProfileResponse;
import com.example.lovelypet.model.UserRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegisterResponse toUserRegisterResponse(User user);

    UserProfileResponse toUserProfileResponse(User user);
}
