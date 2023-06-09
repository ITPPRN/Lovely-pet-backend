package com.example.lovelypet.api;

import com.example.lovelypet.business.HotelBusiness;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel")
public class HotelApi {
    private final HotelBusiness hotelBusiness;

    public HotelApi(HotelBusiness hotelBusiness) {
        this.hotelBusiness = hotelBusiness;
    }


    @PostMapping("register")
    public ResponseEntity<HotelRegisterResponse> register(@RequestBody HotelRegisterRequest reqUser) throws BaseException {
        HotelRegisterResponse response = hotelBusiness.register(reqUser);
        return ResponseEntity.ok(response);
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