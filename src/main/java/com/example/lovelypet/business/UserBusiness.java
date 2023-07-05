package com.example.lovelypet.business;

import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.UserException;
import com.example.lovelypet.mapper.UserMapper;
import com.example.lovelypet.model.LoginRequest;
import com.example.lovelypet.model.UserRegisterRequest;
import com.example.lovelypet.model.UserRegisterResponse;
import com.example.lovelypet.model.UserUpdateRequest;
import com.example.lovelypet.service.TokenService;
import com.example.lovelypet.service.UserService;
import com.example.lovelypet.util.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserBusiness {

    private final UserService userService;

    private final TokenService tokenService;

    private final UserMapper userMapper;

    public UserBusiness(UserService userService, TokenService tokenService, UserMapper userMapper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
    }

    public UserRegisterResponse register(UserRegisterRequest reqUser) throws BaseException {
        User user = userService.create(reqUser.getUserName(), reqUser.getPassWord(), reqUser.getName(), reqUser.getEmail(), reqUser.getPhoneNumber());
        UserRegisterResponse ures = userMapper.toUserRegisterResponse(user);
        return ures;
    }

    public String updateNormalData(UserUpdateRequest updateRequest) throws BaseException {

        User updatedUser = userService.updateNormalData(
                updateRequest.getId(),
                updateRequest.getName(),
                updateRequest.getPhoneNumber()
        );
        return "";
    }

    public String resetPassword(UserUpdateRequest updateRequest) throws BaseException {

        String newPassword = updateRequest.getNewPassWord();

        if (Objects.isNull(newPassword)) {
            throw UserException.createPasswordNull();
        }
        Optional<User> opt = userService.findById(updateRequest.getId());
        if (opt.isEmpty()) {
            throw UserException.loginFailUserNameNotFound();
        }
        User user = opt.get();
        if (!userService.matchPassword(updateRequest.getOldPassword(), user.getPassWord())) {
            throw UserException.passwordIncorrect();

        }
        User updatedUser = userService.resetPassword(
                updateRequest.getId(),
                updateRequest.getNewPassWord()
        );
        return "";
    }

    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(Integer.parseInt(userId));
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optUser.get();
        return tokenService.tokenize(user);
    }

    public String login(LoginRequest loginRequest) throws BaseException {
        String op = loginRequest.getPassWord();
        String ou = loginRequest.getUserName();
        if (Objects.isNull(op)) {
            throw UserException.createPasswordNull();
        }
        if (Objects.isNull(ou)) {
            throw UserException.createUserNameNull();
        }

        Optional<User> opt = userService.findLog(loginRequest.getUserName());
        if (opt.isEmpty()) {
            throw UserException.loginFailUserNameNotFound();
        }
        User user = opt.get();
        if (!userService.matchPassword(loginRequest.getPassWord(), user.getPassWord())) {
            throw UserException.loginFailPasswordIncorrect();

        } else {
            return tokenService.tokenize(user);
        }
    }


}


/* example mapper
public UserRegisterResponse login(LoginRequest loginRequest) throws BaseException {
        String op = loginRequest.getPassWord();
        String ou = loginRequest.getUserName();
        if (Objects.isNull(op)) {
            throw UserException.createPasswordNull();
        }
        if (Objects.isNull(ou)) {
            throw UserException.createUserNameNull();
        }

        Optional<User> opt = userService.findLog(loginRequest.getUserName());
        if (opt.isEmpty()) {
            throw UserException.loginFailUserNameNotFound();
        }
        User user = opt.get();
        if (!userService.matchPassword(loginRequest.getPassWord(), user.getPassWord())) {
            throw UserException.loginFailPasswordIncorrect();

        } else {
            UserRegisterResponse ures = userMapper.toUserRegisterResponse(user);
            return ures;
        }
    }*/
