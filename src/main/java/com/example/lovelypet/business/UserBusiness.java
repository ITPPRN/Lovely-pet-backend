package com.example.lovelypet.business;

import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.UserException;
import com.example.lovelypet.mapper.UserMapper;
import com.example.lovelypet.model.*;
import com.example.lovelypet.service.TokenService;
import com.example.lovelypet.service.UserService;
import com.example.lovelypet.util.SecurityUtil;
import io.netty.util.internal.StringUtil;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class UserBusiness {

    private final UserService userService;

    private final TokenService tokenService;

    private final UserMapper userMapper;

    private final EmailBusiness emailBusiness;

    public UserBusiness(UserService userService, TokenService tokenService, UserMapper userMapper, EmailBusiness emailBusiness) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
        this.emailBusiness = emailBusiness;
    }

    public UserRegisterResponse register(UserRegisterRequest reqUser) throws BaseException {
        String token = SecurityUtil.generateToken();
        User user = userService.create(
                reqUser.getUserName(),
                reqUser.getPassWord(),
                reqUser.getName(),
                reqUser.getEmail(),
                reqUser.getPhoneNumber(),
                token,
                nextXMinute(30)
        );
        UserRegisterResponse ures = userMapper.toUserRegisterResponse(user);
        sendEmail(user);
        return ures;
    }

    private void sendEmail(User user) {


        // TODO: generate token
        String token = user.getToken();

        try {
            emailBusiness.sendActivateUserEmail(user.getEmail(), user.getName(), token);
        } catch (BaseException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public String updateNormalData(UserUpdateRequest updateRequest) throws BaseException {

        User updatedUser = userService.updateNormalData(
                updateRequest.getId(),
                updateRequest.getName(),
                updateRequest.getPhoneNumber()
        );
        return "";
    }

    public ActivateResponse activate(ActivateRequest request) throws BaseException {
        String token = request.getToken();
        if (StringUtil.isNullOrEmpty(token)){
            throw UserException.activateNoToken();
        }
        Optional<User> opt = userService.findByToken(token);
        if (opt.isEmpty()){
            throw UserException.activateFail();
        }

        User user = opt.get();
        if (user.isActivated()){
            throw UserException.activateAlready();
        }

        Date now = new Date();
        Date expireDate = user.getTokenExpire();
        if (now.after(expireDate)){
            //TODO: re-mail
            throw UserException.activateTokenExpire();
        }
        user.setActivated(true);
        userService.update(user);

        ActivateResponse response = new ActivateResponse();
        response.setSuccess(true);
        return response;
    }

    public void resendActivationEmail(ResendActivateEmailRequest request) throws BaseException{
        String email = request.getEmail();
        if (StringUtil.isNullOrEmpty(email)){
            throw UserException.resendActivationEmailNoEmail();
        }

        Optional<User> opt = userService.findByEmail(email);
        if(opt.isEmpty()){
            throw UserException.resendActivationEmailNoEmailEmailNotFound();
        }

        User user = opt.get();

        if (user.isActivated()){
            throw UserException.activateAlready();
        }
        user.setToken(SecurityUtil.generateToken());
        user.setTokenExpire(nextXMinute(30));
        userService.update(user);
        sendEmail(user);
    }

    private Date nextXMinute(int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,minute);
        return calendar.getTime();
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
        //verify password
        if (!userService.matchPassword(loginRequest.getPassWord(), user.getPassWord())) {
            throw UserException.loginFailPasswordIncorrect();

        }
        if (!user.isActivated()){
            throw UserException.loginFailUserUnactivated();

        }else {
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
