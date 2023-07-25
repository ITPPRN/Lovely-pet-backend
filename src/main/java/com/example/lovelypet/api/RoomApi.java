package com.example.lovelypet.api;

import com.example.lovelypet.business.PhotoRoomBusiness;
import com.example.lovelypet.business.RoomBusiness;
import com.example.lovelypet.entity.PhotoRoom;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.RoomRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("/upload-image")
    public ResponseEntity<PhotoRoom> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam int id) throws BaseException, IOException {
        PhotoRoom response = photoRoomBusiness.uploadImage(file, id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete-room")
    public ResponseEntity<String> deleteRoom(@RequestParam int id) throws BaseException {
        String response = roomBusiness.deleteU(id);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/get-images")
    public ResponseEntity<InputStreamResource> getImageById(@RequestParam int id) {
        return photoRoomBusiness.getImageById(id);
    }

//////////////////////// ยังไม่เสร็จ ////////////////////////////////


    @GetMapping("/image-url")
    public ResponseEntity<Resource> getUrlImageById(@RequestParam int id) throws MalformedURLException {
        // ดึงข้อมูลรูปภาพจากฐานข้อมูล
        Optional<PhotoRoom> imageOptional = photoRoomBusiness.findById(id);
        if (imageOptional.isPresent()) {
            PhotoRoom image = imageOptional.get();
            // สร้าง URL ของรูปภาพ
            String imageUrl = "http://192.168.1.102/src/main/resources/imageRoomUpload/" + image.getPhotoRoomPartFile();
            //imageUrl.add("https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.simplilearn.com%2Fice9%2Ffree_resources_article_thumb%2Fwhat_is_image_Processing.jpg&tbnid=dDLJLDH24Qls4M&vet=12ahUKEwjKlZ-ctqCAAxXqzaACHXQ_AMkQMygBegUIARCcAQ..i&imgrefurl=https%3A%2F%2Fwww.simplilearn.com%2Fimage-processing-article&docid=NMmM-IXyCkU2hM&w=848&h=477&q=image&ved=2ahUKEwjKlZ-ctqCAAxXqzaACHXQ_AMkQMygBegUIARCcAQ"
            //);// ส่ง URL กลับไปยัง client
            return ResponseEntity.status(HttpStatus.OK).body(new UrlResource(imageUrl));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/imagess")
    public ResponseEntity<List<String>> getAllImages(@RequestParam int id) {
        List<PhotoRoom> images = photoRoomBusiness.findByRoomId(id); // ดึงข้อมูลรูปภาพทั้งหมดจากฐานข้อมูล
        List<String> response = new ArrayList<>();
        for (PhotoRoom image : images) {
            // สร้าง URL สำหรับแสดงรูปภาพ
            String imageUrl = "src/main/resources/imageRoomUpload/"+image.getPhotoRoomPartFile();
            response.add(imageUrl);
        }
        return ResponseEntity.ok(response);
    }

///////////////////////////////////////////////////
}
