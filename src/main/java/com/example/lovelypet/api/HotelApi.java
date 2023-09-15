package com.example.lovelypet.api;

import com.example.lovelypet.business.HotelBusiness;
import com.example.lovelypet.business.PhotoHotelBusiness;
import com.example.lovelypet.entity.AdditionalServices;
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
    public ResponseEntity<HotelRegisterResponse> register(@RequestBody HotelRegisterRequest reqUser) throws BaseException, IOException {
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

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        String response = hotelBusiness.refreshToken();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-normal")
    public ResponseEntity<HotelResponse> updateNormalData(@RequestBody HotelUpdateRequest hotelUpdateRequest) throws BaseException {
        HotelResponse response = hotelBusiness.updateNormalData(hotelUpdateRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-verify")
    public ResponseEntity<HotelResponse> updateVerifyState(@RequestBody HotelVerifyRequest hotelUpdateRequest) throws BaseException {
        HotelResponse response = hotelBusiness.updateVerifyState(hotelUpdateRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody HotelUpdateRequest hotelUpdateRequest) throws BaseException {
        String response = hotelBusiness.resetPassword(hotelUpdateRequest);
        return ResponseEntity.ok(response);
    }

    //อัพรูป
    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile[] file, @RequestParam int id) throws BaseException, IOException {
        String response = photoHotelBusiness.storeImage(file, id);
        return ResponseEntity.ok(response);
    }

    //ดึงรูป
    @PostMapping("/get-images")
    public ResponseEntity<InputStreamResource> getImageById(@RequestBody PhotoHotelRequest id) {
        return photoHotelBusiness.getImageById(id);
    }

    @PostMapping("/get-images-url")
    public ResponseEntity<List<String>> getImageUrl() throws BaseException {
        List<String> response = photoHotelBusiness.getImageUrl();
        return ResponseEntity.ok(response);
    }

    //get hotel
    @PostMapping("/list-hotel-to-service")
    public ResponseEntity<List<HotelResponse>> listHotelToService() throws BaseException {
        List<HotelResponse> response = hotelBusiness.listHotelByState2();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-hotel-to-verify")
    public ResponseEntity<List<HotelResponse>> getHotelToVerify() throws BaseException {
        List<HotelResponse> response = hotelBusiness.getHotelByVerify();
        return ResponseEntity.ok(response);
    }

    //delete image
    @PostMapping("/delete-image-hotel")
    public ResponseEntity<String> deleteImageHotel(@RequestBody PhotoHotelRequest name) throws BaseException {
        String response = photoHotelBusiness.deleteImage(name);
        return ResponseEntity.ok(response);
    }

    //delete account request
    @PostMapping("/delete-account-request")
    public ResponseEntity<String> deleteAccountRequest() throws BaseException {
        String response = hotelBusiness.deleteAccountRequest();
        return ResponseEntity.ok(response);
    }

    ///////////////////////////////////////////////
    //////////////////////////////////////////////

}


