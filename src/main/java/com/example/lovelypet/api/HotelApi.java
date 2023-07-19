package com.example.lovelypet.api;

import com.example.lovelypet.business.HotelBusiness;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel")
public class HotelApi {
    private final HotelBusiness hotelBusiness;

    public HotelApi(HotelBusiness hotelBusiness) {
        this.hotelBusiness = hotelBusiness;
    }


    @PostMapping("/register")
    public ResponseEntity<HotelRegisterResponse> register(@RequestBody HotelRegisterRequest reqUser) throws BaseException {
        HotelRegisterResponse response = hotelBusiness.register(reqUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/activate")
    public ResponseEntity<ActivateResponse> activate(@RequestBody ActivateRequest request) throws BaseException {
        ActivateResponse response = hotelBusiness.activate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-activate-email")
    public ResponseEntity<Void> resendActivationEmail(@RequestBody ResendActivateEmailRequest request) throws BaseException {
        hotelBusiness.resendActivationEmail(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws BaseException {
        String response = hotelBusiness.login(loginRequest);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        String response = hotelBusiness.refreshToken();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-normal")
    public ResponseEntity<String> updateNormalData(@RequestBody HotelUpdateRequest hotelUpdateRequest) throws BaseException {
        String response = hotelBusiness.updateNormalData(hotelUpdateRequest);
        return ResponseEntity.ok("");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody HotelUpdateRequest hotelUpdateRequest) throws BaseException {
        String response = hotelBusiness.resetPassword(hotelUpdateRequest);
        return ResponseEntity.ok(response);
    }



//////////////ยังไม่เสร็จ //////////////////////////////////////////////////////////
    // อัปโหลดรูป
    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage() throws BaseException {
        String response = hotelBusiness.uploadImage();
        return ResponseEntity.ok(response);
    }

    //ดึงรูป
    @PostMapping("/get-image")
    public ResponseEntity<String> getImage() throws BaseException {
        String response = hotelBusiness.getImage();
        return ResponseEntity.ok(response);
    }
/////////////////////////////////////////////////////////////////////////////////

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