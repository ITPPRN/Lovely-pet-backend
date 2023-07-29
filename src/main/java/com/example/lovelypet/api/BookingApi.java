package com.example.lovelypet.api;

import com.example.lovelypet.business.BookingBusiness;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.BookingRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/booking")
public class BookingApi {

    private final BookingBusiness bookingBusiness;

    public BookingApi(BookingBusiness bookingBusiness) {
        this.bookingBusiness = bookingBusiness;
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserve(@RequestBody BookingRequest request) throws BaseException, IOException {
        String response = bookingBusiness.reserve(request);
        return ResponseEntity.ok(response);
    }

    //ดึงรูป
    @GetMapping("/get-images")
    public ResponseEntity<InputStreamResource> getImageById(@RequestParam int id) {
        return bookingBusiness.getImageById(id);
    }

    @GetMapping("/get-images-url")
    public ResponseEntity<String> getImageUrl(@RequestParam int id) throws BaseException {
        String response = bookingBusiness.getImageUrl(id);
        return ResponseEntity.ok(response);
    }


}
