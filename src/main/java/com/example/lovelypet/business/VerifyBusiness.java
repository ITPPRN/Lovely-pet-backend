package com.example.lovelypet.business;

import com.example.lovelypet.entity.Verify;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.VerifyException;
import com.example.lovelypet.model.LoginRequest;
import com.example.lovelypet.service.TokenService;
import com.example.lovelypet.service.VerifyService;
import com.example.lovelypet.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class VerifyBusiness {

    private final VerifyService verifyService;

    private final TokenService tokenService;

    public VerifyBusiness(VerifyService verifyService, TokenService tokenService) {
        this.verifyService = verifyService;
        this.tokenService = tokenService;
    }

    public void begin() throws BaseException {
        verifyService.create();
    }



    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw VerifyException.unauthorized();
        }

        String userId = opt.get();

        Optional<Verify> optVerify = verifyService.findById(Integer.parseInt(userId));
        if (optVerify.isEmpty()) {
            throw VerifyException.notFound();
        }

        Verify verify = optVerify.get();
        return tokenService.tokenizeVerify(verify);
    }

    public String login(LoginRequest loginRequest) throws BaseException {
        String op = loginRequest.getPassWord();
        String ou = loginRequest.getUserName();
        if (Objects.isNull(op)) {
            throw VerifyException.createPasswordNull();
        }
        if (Objects.isNull(ou)) {
            throw VerifyException.createUserNameNull();
        }

        Optional<Verify> opt = verifyService.findLog(loginRequest.getUserName());
        if (opt.isEmpty()) {
            throw VerifyException.loginFailUserNameNotFound();
        }
        Verify verify = opt.get();
        if (!verifyService.matchPassword(loginRequest.getPassWord(), verify.getPassWord())) {
            throw VerifyException.loginFailPasswordIncorrect();

        } else {
            return tokenService.tokenizeVerify(verify);
        }
    }
}
