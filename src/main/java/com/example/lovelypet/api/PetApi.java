package com.example.lovelypet.api;

import com.example.lovelypet.business.PetBusiness;
import com.example.lovelypet.entity.Pet;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.AddPetRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/pet")
public class PetApi {

    private final PetBusiness petBusiness;

    public PetApi(PetBusiness petBusiness) {
        this.petBusiness = petBusiness;
    }

    @PostMapping("/add-pet")
    public ResponseEntity<String> addMyPet(@RequestBody AddPetRequest request) throws BaseException {
        String response = petBusiness.addMyPet(request);
        return ResponseEntity.ok(response);
    }

    //อัพรูป
    @PostMapping("/upload-image")
    public ResponseEntity<Pet> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam int id) throws BaseException, IOException {
        Pet response = petBusiness.uploadImage(file, id);
        return ResponseEntity.ok(response);
    }

    //ดึงรูป
    @GetMapping("/get-images")
    public ResponseEntity<InputStreamResource> getImageById(@RequestParam int id) {
        return petBusiness.getImageById(id);
    }

    @GetMapping("/get-images-url")
    public ResponseEntity<String> getImageUrl(@RequestParam int id) throws BaseException {
        String response = petBusiness.getImageUrl(id);
        return ResponseEntity.ok(response);
    }


}
