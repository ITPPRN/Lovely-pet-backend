package com.example.lovelypet.api;

import com.example.lovelypet.business.UserBusiness;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserApi {
    private final UserBusiness userBusiness;

    public UserApi(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test()  {
        return ResponseEntity.ok("server Start");
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody UserRegisterRequest reqUser) throws BaseException {
        UserRegisterResponse response = userBusiness.register(reqUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<UserProfileResponse> getMyUserProfile() throws BaseException {
        UserProfileResponse response = userBusiness.getMyUserProfile();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activate")
    public ResponseEntity<ActivateResponse> activate(@RequestParam String request) throws BaseException {
        ActivateResponse response = userBusiness.activate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-activate-email")
    public ResponseEntity<Void> resendActivationEmail(@RequestBody ResendActivateEmailRequest request) throws BaseException {
        userBusiness.resendActivationEmail(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws BaseException {
        String response = userBusiness.login(loginRequest);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        String response = userBusiness.refreshToken();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-normal")
    public ResponseEntity<UserRegisterResponse> updateProfile(@RequestBody UserUpdateRequest updateRequest) throws BaseException {
        UserRegisterResponse response = userBusiness.updateNormalData(updateRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody UserUpdateRequest updateRequest) throws BaseException {
        String response = userBusiness.resetPassword(updateRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/not-reset-password")
    private ResponseEntity<String> notResetPassword(@RequestBody UserUpdateRequest request) throws BaseException {
        String response = userBusiness.notResetPassword(request);
        return ResponseEntity.ok(response);
    }

    //อัพรูป
    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws BaseException, IOException {
        String response = userBusiness.uploadImage(file);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload-image-register")
    public ResponseEntity<String> uploadImageForRegister(@RequestParam("file") MultipartFile file,@RequestParam int id) throws BaseException, IOException {
        String response = userBusiness.uploadImageForRegister(file,id);
        return ResponseEntity.ok(response);
    }

    //ดึงรูป
    @PostMapping("/get-images")
    public ResponseEntity<InputStreamResource> getImageById() throws BaseException {
        return userBusiness.getImageById();
    }

    @PostMapping("/get-images-url")
    public ResponseEntity<String> getImageUrl() throws BaseException {
        String response = userBusiness.getImageUrl();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete-account-request")
    public ResponseEntity<String> deleteAccountRequest() throws BaseException {
        String response = userBusiness.deleteRequest();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete-image")
    public ResponseEntity<String> deleteImage() throws BaseException {
        String response = userBusiness.deleteImage();
        return ResponseEntity.ok(response);
    }
    //////////////////////////////////////


    //////////////////////////////////////////////
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