package com.example.lovelypet.api;

import com.example.lovelypet.business.PhotoRoomBusiness;
import com.example.lovelypet.business.RoomBusiness;
import com.example.lovelypet.entity.PhotoRoom;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.RoomRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomApi {

    private final RoomBusiness roomBusiness;
    private final PhotoRoomBusiness photoRoomBusiness;

    public RoomApi(RoomBusiness roomBusiness, PhotoRoomBusiness photoRoomBusiness) {
        this.roomBusiness = roomBusiness;
        this.photoRoomBusiness = photoRoomBusiness;
    }

    @PostMapping("/add-room")
    public ResponseEntity<String> addRoom(@RequestBody RoomRequest request) throws BaseException {
        String response = roomBusiness.addRoom(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-room")
    public ResponseEntity<String> updateRoom(@RequestBody RoomRequest request) throws BaseException {
        String response = roomBusiness.updateRoom(request);
        return ResponseEntity.ok(response);
    }

    //อัพรูป
    @PostMapping("/upload-image")
    public ResponseEntity<PhotoRoom> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam int id) throws BaseException, IOException {
        PhotoRoom response = photoRoomBusiness.uploadImage(file, id);
        return ResponseEntity.ok(response);
    }

    //ดึงรูป
    @GetMapping("/get-images")
    public ResponseEntity<InputStreamResource> getImageById(@RequestParam int id) {
        return photoRoomBusiness.getImageById(id);
    }

    @GetMapping("/get-images-url")
    public ResponseEntity<List<String>> getImageUrl(@RequestParam int id) {
        List<String> response = photoRoomBusiness.getImageUrl(id);
        return ResponseEntity.ok(response);
    }

    //ลบห้อง
    @PostMapping("/delete-room")
    public ResponseEntity<String> deleteRoom(@RequestParam int id) throws BaseException {
        String response = roomBusiness.deleteU(id);
        return ResponseEntity.ok(response);

    }

}
