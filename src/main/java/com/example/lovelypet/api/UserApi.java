package com.example.lovelypet.api;

import com.example.lovelypet.business.UserBusiness;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserApi {
    private final UserBusiness userBusiness;

    public UserApi(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }


    @PostMapping("register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody UserRegisterRequest reqUser) throws BaseException {
        UserRegisterResponse response = userBusiness.register(reqUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/activate")
    public ResponseEntity<ActivateResponse>activate(@RequestBody ActivateRequest request) throws BaseException {
        ActivateResponse response = userBusiness.activate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-activate-email")
    public ResponseEntity<Void>resendActivationEmail(@RequestBody ResendActivateEmailRequest request) throws BaseException {
         userBusiness.resendActivationEmail(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws BaseException {
        String response = userBusiness.login(loginRequest);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        String response = userBusiness.refreshToken();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-normal")
    public ResponseEntity<String> update(@RequestBody UserUpdateRequest updateRequest) throws BaseException {
        String response = userBusiness.updateNormalData(updateRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody UserUpdateRequest updateRequest) throws BaseException {
        String response = userBusiness.resetPassword(updateRequest);
        return ResponseEntity.ok(response);
    }
}
//
//
///*
//    //ฟิว อินเจคชัน
//    //@Autowired
//    //private TestBusiness business;
//
//    private final UserBusiness business;
//    @Autowired
//    private UserRepository userProfileRepository;
//
//    public UserApi(UserBusiness business) {
//        this.business = business;
//    }
//
//
//    @GetMapping("/register")
//    public ResponseEntity<User> register(RegisterRequest userName, RegisterRequest passWord, RegisterRequest name, RegisterRequest email, RegisterRequest phoneNumber) throws BaseException {
//        User response = business.register(userName, passWord, name, email, phoneNumber);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/list-by-username")
//    public Object lisByUser(String userName) {
//        APIResponse res = new APIResponse();
//        List<User> lst = userProfileRepository.findByUserName(userName);
//        res.setData(lst);
//        return res.getData();
//    }
//
//*/
//TEST