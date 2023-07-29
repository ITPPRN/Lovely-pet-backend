package com.example.lovelypet.api;

import com.example.lovelypet.business.HotelBusiness;
import com.example.lovelypet.business.PhotoHotelBusiness;
import com.example.lovelypet.entity.PhotoHotel;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelApi {
    private final HotelBusiness hotelBusiness;

    private final PhotoHotelBusiness photoHotelBusiness;

    public HotelApi(HotelBusiness hotelBusiness, PhotoHotelBusiness photoHotelBusiness) {
        this.hotelBusiness = hotelBusiness;
        this.photoHotelBusiness = photoHotelBusiness;
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

    //อัพรูป
    @PostMapping("/upload-image")
    public ResponseEntity<PhotoHotel> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam int id) throws BaseException, IOException {
        PhotoHotel response = photoHotelBusiness.uploadImage(file, id);
        return ResponseEntity.ok(response);
    }

    //ดึงรูป
    @GetMapping("/get-images")
    public ResponseEntity<InputStreamResource> getImageById(@RequestParam int id) {
        return photoHotelBusiness.getImageById(id);
    }

    @GetMapping("/get-images-url")
    public ResponseEntity<List<String>> getImageUrl(@RequestParam int id) throws BaseException {
        List<String> response = photoHotelBusiness.getImageUrl(id);
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