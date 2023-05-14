package com.example.lovelypet.business;

import com.example.lovelypet.entity.User;
import com.example.lovelypet.entity.Verify;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.UserRegisterRequest;
import com.example.lovelypet.service.VerifyService;
import org.springframework.stereotype.Service;

@Service
public class VerifyBusiness {

    private final VerifyService verifyService;

    public VerifyBusiness(VerifyService verifyService) {
        this.verifyService = verifyService;
    }

    public Verify begining() throws BaseException {
        Verify verify = verifyService.create();
        // TODO: mapper
        return verify;
    }
}
