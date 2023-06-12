package com.example.lovelypet.api;

import com.example.lovelypet.business.VerifyBusiness;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verify")
public class VerifyApi {

    private final VerifyBusiness verifyBusiness;

    public VerifyApi(VerifyBusiness verifyBusiness) {
        this.verifyBusiness = verifyBusiness;
    }

    @PostMapping("/home")
    public void begin() throws BaseException {
        verifyBusiness.begin();
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws BaseException {
        String response = verifyBusiness.login(loginRequest);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        String response = verifyBusiness.refreshToken();
        return ResponseEntity.ok(response);
    }
}
