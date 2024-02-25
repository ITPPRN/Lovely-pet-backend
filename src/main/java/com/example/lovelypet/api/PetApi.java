package com.example.lovelypet.api;

import com.example.lovelypet.business.PetBusiness;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.AddPetRequest;
import com.example.lovelypet.model.PetProfileResponse;
import com.example.lovelypet.model.UpdatePetRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetApi {

    private final PetBusiness petBusiness;

    public PetApi(PetBusiness petBusiness) {
        this.petBusiness = petBusiness;
    }

    @PostMapping("/add-pet")
    public ResponseEntity<PetProfileResponse> addMyPet(@RequestBody AddPetRequest request) throws BaseException {
        PetProfileResponse response = petBusiness.addMyPet(request);
        return ResponseEntity.ok(response);
    }

    //อัพรูป
    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam int id) throws BaseException, IOException {
        String response = petBusiness.uploadImage(file, id);
        return ResponseEntity.ok(response);
    }

    //ดึงรูป
    @PostMapping("/get-images")
    public ResponseEntity<InputStreamResource> getImageById(@RequestBody AddPetRequest id) {
        return petBusiness.getImageById(id);
    }

    @PostMapping("/get-images-url")
    public ResponseEntity<String> getImageUrl(@RequestBody AddPetRequest id) throws BaseException {
        String response = petBusiness.getImageUrl(id);
        return ResponseEntity.ok(response);
    }

    //update
    @PostMapping("/update-pet")
    public ResponseEntity<PetProfileResponse> updatePet(@RequestBody UpdatePetRequest request) throws BaseException {
        PetProfileResponse response = petBusiness.updatePet(request);
        return ResponseEntity.ok(response);
    }

    //get date
    @PostMapping("/get-profile-pet")
    public ResponseEntity<PetProfileResponse> getProfilePet(@RequestBody AddPetRequest id) throws BaseException {
        PetProfileResponse response = petBusiness.getProfilePet(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-my-pet")
    public ResponseEntity<List<PetProfileResponse>> getMyPet() throws BaseException {
        List<PetProfileResponse> response = petBusiness.getMyPet();
        return ResponseEntity.ok(response);
    }

    //delete
    @PostMapping("/delete-pet")
    public ResponseEntity<String> deletePet(@RequestBody AddPetRequest id) throws BaseException {
        String response = petBusiness.deletePet(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete-image")
    public ResponseEntity<String> deleteImage(@RequestBody AddPetRequest id) throws BaseException {
        String response = petBusiness.deleteImage(id);
        return ResponseEntity.ok(response);
    }

    ////////////////////////////////
    /////////////////////////////////////
}
