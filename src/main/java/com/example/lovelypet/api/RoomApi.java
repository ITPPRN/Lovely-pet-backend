package com.example.lovelypet.api;

import com.example.lovelypet.business.RoomBusiness;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.RoomRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomApi {

    private final RoomBusiness roomBusiness;

    public RoomApi(RoomBusiness roomBusiness) {
        this.roomBusiness = roomBusiness;
    }

    @PostMapping("add-room")
    public ResponseEntity<String> addRoom(@RequestBody RoomRequest request) throws BaseException {
        String response = roomBusiness.addRoom(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("update-room")
    public ResponseEntity<String> updateRoom(@RequestBody RoomRequest request) throws BaseException {
        String response = roomBusiness.updateRoom(request);
        return ResponseEntity.ok(response);
    }

}