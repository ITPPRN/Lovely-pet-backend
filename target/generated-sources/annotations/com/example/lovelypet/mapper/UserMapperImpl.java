package com.example.lovelypet.mapper;

import com.example.lovelypet.entity.User;
import com.example.lovelypet.model.UserProfileResponse;
import com.example.lovelypet.model.UserRegisterResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2567-02-25T02:26:06+0700",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserRegisterResponse toUserRegisterResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();

        userRegisterResponse.setId( user.getId() );
        userRegisterResponse.setUserName( user.getUserName() );
        userRegisterResponse.setName( user.getName() );
        userRegisterResponse.setEmail( user.getEmail() );
        userRegisterResponse.setPhoneNumber( user.getPhoneNumber() );

        return userRegisterResponse;
    }

    @Override
    public UserProfileResponse toUserProfileResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserProfileResponse userProfileResponse = new UserProfileResponse();

        userProfileResponse.setName( user.getName() );
        userProfileResponse.setEmail( user.getEmail() );
        userProfileResponse.setPhoneNumber( user.getPhoneNumber() );
        userProfileResponse.setUserPhoto( user.getUserPhoto() );

        return userProfileResponse;
    }
}
