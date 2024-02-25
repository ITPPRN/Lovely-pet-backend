package com.example.lovelypet.api;

import com.example.lovelypet.business.BookingBusiness;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    //อัพรูป
    @PostMapping("/upload-slip")
    public ResponseEntity<String> uploadSlipForBooking(@RequestParam("file") MultipartFile file,@RequestParam int id) throws BaseException, IOException {
        String response = bookingBusiness.uploadSlip(file,id);
        return ResponseEntity.ok(response);
    }

    //ดึงรูป
    @PostMapping("/get-images")
    public ResponseEntity<InputStreamResource> getImageById(@RequestBody BookingRequest id) {
        return bookingBusiness.getImageById(id);
    }

    @PostMapping("/get-images-url")
    public ResponseEntity<String> getImageUrl(@RequestBody BookingRequest id) throws BaseException {
        String response = bookingBusiness.getImageUrl(id);
        return ResponseEntity.ok(response);
    }

    //get data

    @PostMapping("/list-booking-all")
    public ResponseEntity<List<BookingListResponse>> listBookingAll() throws BaseException {
        List<BookingListResponse> response = bookingBusiness.allListBooking();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/list-booking-all-for-user")
    public ResponseEntity<List<BookingListResponse>> listBookingAllForUser() throws BaseException {
        List<BookingListResponse> response = bookingBusiness.allListBookingForUser();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/list-booking")
    public ResponseEntity<List<BookingListResponse>> listBooking(@RequestBody String state) throws BaseException {
        List<BookingListResponse> response = bookingBusiness.listBooking(state);
        return ResponseEntity.ok(response);
    }



    @PostMapping("/get-booking")
    public ResponseEntity<BookingListResponse> getBooking(@RequestBody BookingRequest id) throws BaseException {
        BookingListResponse response = bookingBusiness.getBooking(id);
        return ResponseEntity.ok(response);
    }

    //consider booking
    @PostMapping("/consider-booking")
    public ResponseEntity<String> considerBooking(@RequestBody ConsiderBookingRequest request) throws BaseException {
        String response = bookingBusiness.considerBooking(request);
        return ResponseEntity.ok(response);
    }

    //update
    @PostMapping("/update")
    public ResponseEntity<String> updateData(@RequestBody BookingRequest request) throws BaseException, IOException {
        String response = bookingBusiness.updateProfile(request);
        return ResponseEntity.ok(response);
    }

    //cancel
    @PostMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestBody BookingRequest id) throws BaseException {
        String response = bookingBusiness.cancelBooking(id);
        return ResponseEntity.ok(response);
    }

    //delete
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody BookingRequest id) throws BaseException {
        String response = bookingBusiness.deleteBooking(id);
        return ResponseEntity.ok(response);
    }

    // booking for user
    @PostMapping("booking-for-user")
    public ResponseEntity<String> bookingForUser(@RequestBody BookingByClinicRequest request) throws BaseException, IOException {
        String response = bookingBusiness.reserveByClinic(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("list-booking-by-clinic")
    public  ResponseEntity<List<BookingByClinicListResponse>> bookingByClinic(@RequestParam String request)throws BaseException{
        List<BookingByClinicListResponse> response = bookingBusiness.listBookingByClinic(request);
        return ResponseEntity.ok(response);
    }


    ///////////////////////////////////

    ///////////////////////////////////
}
