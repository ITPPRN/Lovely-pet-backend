package com.example.lovelypet.api;

import com.example.lovelypet.business.VerifyBusiness;
import com.example.lovelypet.entity.Verify;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
public class VerifyApi {

    private final VerifyBusiness verifyBusiness;

    public VerifyApi(VerifyBusiness verifyBusiness) {
        this.verifyBusiness = verifyBusiness;
    }

    @PostMapping("/home")
    public ResponseEntity<Verify> begined() throws BaseException {
        Verify response = verifyBusiness.begining();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest lrq) {

        return "";
    }
}
