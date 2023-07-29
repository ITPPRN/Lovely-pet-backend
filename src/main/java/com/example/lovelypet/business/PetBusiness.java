package com.example.lovelypet.business;

import com.example.lovelypet.entity.Pet;
import com.example.lovelypet.entity.PetType;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.FileException;
import com.example.lovelypet.exception.RoomException;
import com.example.lovelypet.model.AddPetRequest;
import com.example.lovelypet.service.PetService;
import com.example.lovelypet.service.PetTypeService;
import com.example.lovelypet.service.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class PetBusiness {

    private final PetService petService;

    private final PetTypeService petTypeService;

    private final UserService userService;
    private final String part = "src/main/resources/imageUpload/imagePet";

    public PetBusiness(PetService petService, PetTypeService petTypeService, UserService userService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.userService = userService;
    }

    public String addMyPet(AddPetRequest request) throws BaseException {

        //user
        Optional<User> optUser = userService.findById(request.getUserId());
        User user = optUser.get();

        //pet type
        Optional<PetType> optionalPetType = petTypeService.findByName(request.getType());
        if (!optionalPetType.isPresent()) {
            petTypeService.create(request.getType());
            optionalPetType = petTypeService.findByName(request.getType());
        }
        PetType type = optionalPetType.get();

        //date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ParsePosition pos = new ParsePosition(0);
        Date birthday = dateFormat.parse(request.getBirthday(), pos);

        //add pet to database
        Pet response = petService.create(
                user,
                request.getName(),
                request.getPetPhoto(),
                type,
                birthday
        );

        return "response";
    }

    public Pet uploadImage(MultipartFile file, int id) throws IOException, BaseException {

        //validate request
        if (file == null) {
            throw FileException.fileNull();
        }

        if (file.getSize() > 1048576 * 5) {
            throw FileException.fileMaxSize();
        }
        String contentType = file.getContentType();
        if (contentType == null) {
            throw FileException.unsupported();
        }

        List<String> supportedType = Arrays.asList("image/jpeg", "image/png");
        if (!supportedType.contains(contentType)) {
            throw FileException.unsupported();
        }

        if (Objects.isNull(id)) {
            throw RoomException.createRoomIdNull();
        }

        // สร้างชื่อไฟล์ที่ไม่ซ้ำกัน
        String fileName = generateUniqueFileName(file.getOriginalFilename());

        String filePath = part + File.separator + fileName;
        //File filePath = new File(uploadDir, fileName);


        // สร้างไดเร็กทอรีถ้ายังไม่มี
        File directory = new File(part);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the image file
        //file.transferTo(filePath);
        Path path = Paths.get(filePath);
        Files.write(path, file.getBytes());

        // Save the image information in the database
        Optional<Pet> opt = petService.findById(id);
        Pet pet = opt.get();
        pet.setPetPhoto(fileName);
        Pet response = petService.update(pet);

        return response;
    }

    // สร้างชื่อไฟล์ที่ไม่ซ้ำกัน
    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }

    public ResponseEntity<InputStreamResource> getImageById(int id) {
        Optional<Pet> imageEntity = petService.findById(id);
        if (imageEntity.isPresent()) {
            String filename = imageEntity.get().getPetPhoto();
            File imageFile = new File(part + File.separator + filename);

            try {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(imageFile));
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + filename)
                        .contentType(MediaType.IMAGE_JPEG)
                        .contentLength(imageFile.length())
                        .body(resource);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public String getImageUrl(int id) throws BaseException {
        Optional<Pet> images = petService.findById(id); // ดึงข้อมูลรูปภาพทั้งหมดจากฐานข้อมูล
        String response = part + File.separator + images.get().getPetPhoto();
        return response;
    }

}




