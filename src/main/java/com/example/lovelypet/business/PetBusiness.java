package com.example.lovelypet.business;

import com.example.lovelypet.entity.Pet;
import com.example.lovelypet.entity.PetType;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.FileException;
import com.example.lovelypet.exception.PetException;
import com.example.lovelypet.exception.UserException;
import com.example.lovelypet.mapper.PetMapper;
import com.example.lovelypet.model.AddPetRequest;
import com.example.lovelypet.model.PetProfileResponse;
import com.example.lovelypet.model.UpdatePetRequest;
import com.example.lovelypet.service.PetService;
import com.example.lovelypet.service.PetTypeService;
import com.example.lovelypet.service.UserService;
import com.example.lovelypet.util.SecurityUtil;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
@Service
public class PetBusiness {

    private final PetService petService;
    private final PetMapper petMapper;
    private final PetTypeService petTypeService;
    private final UserService userService;
    private final String path = "src/main/resources/imageUpload/imagePet";
    public SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

    public PetBusiness(PetService petService, PetMapper petMapper, PetTypeService petTypeService, UserService userService) {
        this.petService = petService;
        this.petMapper = petMapper;
        this.petTypeService = petTypeService;
        this.userService = userService;
    }

    public PetProfileResponse addMyPet(AddPetRequest request) throws BaseException {

        //user
        Optional<String> opt1 = SecurityUtil.getCurrentUserId();
        if (opt1.isEmpty()) {
            throw UserException.unauthorized();
        }
        String userId = opt1.get();
        Optional<User> optUser = userService.findById(Integer.parseInt(userId));
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }
        User user = optUser.get();

        //pet type
        Optional<PetType> optionalPetType = petTypeService.findByName(request.getType());
        if (optionalPetType.isEmpty()) {
            petTypeService.create(request.getType());
            optionalPetType = petTypeService.findByName(request.getType());
            if (optionalPetType.isEmpty()) {
                throw PetException.notFound();
            }
        }
        PetType type = optionalPetType.get();

        //date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ParsePosition pos = new ParsePosition(0);
        Date birthday = dateFormat.parse(request.getBirthday(), pos);

        //add pet to database
        Pet pet = petService.create(
                user,
                request.getName(),
                type,
                birthday
        );
        return res(pet, user);

    }

    public String uploadImage(MultipartFile file, int id) throws IOException, BaseException {

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

        if (id == 0) {
            throw PetException.uploadIdNull();
        }

        Optional<Pet> opt = petService.findById(id);
        if (opt.isEmpty()) {
            throw PetException.notFound();
        }
        Pet pet = opt.get();
        //Check if there are no pictures yet.
        if (Objects.nonNull(pet.getPetPhoto())) {
            throw PetException.imageAlreadyExists();
        }


        // สร้างชื่อไฟล์ที่ไม่ซ้ำกัน
        String fileName = generateUniqueFileName(file.getOriginalFilename());

        String filePath = path + File.separator + fileName;
        //File filePath = new File(uploadDir, fileName);


        // สร้างไดเร็กทอรีถ้ายังไม่มี
        File directory = new File(path);
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            // ตรวจสอบผลลัพธ์
            if (!success) {
                throw FileException.failedToCreateDirectory();
            }
        }

        Path path = Paths.get(filePath);
        Files.write(path, file.getBytes());

        // Save the image information in the database

        pet.setPetPhoto(fileName);
        Pet petUpdate = petService.update(pet);
        return "Image" + petUpdate.getPetPhoto() + "has been successfully uploaded.";
    }

    // สร้างชื่อไฟล์ที่ไม่ซ้ำกัน
    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }

    public ResponseEntity<InputStreamResource> getImageById(AddPetRequest id) {
        Optional<Pet> imageEntity = petService.findById(id.getId());
        if (imageEntity.isPresent()) {
            String filename = imageEntity.get().getPetPhoto();
            File imageFile = new File(path + File.separator + filename);

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

    public String getImageUrl(AddPetRequest id) throws BaseException {
        Optional<Pet> images = petService.findById(id.getId()); // ดึงข้อมูลรูปภาพทั้งหมดจากฐานข้อมูล
        if (images.isEmpty()) {
            throw PetException.notFound();
        }
        return path + File.separator + images.get().getPetPhoto();

    }

    public PetProfileResponse updatePet(UpdatePetRequest request) throws BaseException {
        if (request.getId() == 0) {
            throw PetException.updateIdNull();
        }
        Optional<Pet> opt = petService.findById(request.getId());
        if (opt.isEmpty()) {
            throw PetException.notFound();
        }
        Pet pet = opt.get();

        if (Objects.nonNull(request.getName())) {
            pet.setPetName(request.getName());
        }
        if (Objects.nonNull(request.getType())) {
            //pet type
            Optional<PetType> optionalPetType = petTypeService.findByName(request.getType());
            if (optionalPetType.isEmpty()) {
                petTypeService.create(request.getType());
                optionalPetType = petTypeService.findByName(request.getType());
                if (optionalPetType.isEmpty()) {
                    throw PetException.notFound();
                }
            }
            PetType type = optionalPetType.get();
            pet.setPetTypeId(type);
        }

        if (Objects.nonNull(request.getBirthday())) {
            //date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            ParsePosition pos = new ParsePosition(0);
            Date birthday = dateFormat.parse(request.getBirthday(), pos);
            pet.setBirthday(birthday);
        }
        Pet petUpdate = petService.update(pet);
        return res(petUpdate, petUpdate.getUserId());
    }

    public PetProfileResponse getProfilePet(AddPetRequest id) throws BaseException {
        Optional<Pet> opt = petService.findById(id.getId()); // ดึงข้อมูลรูปภาพทั้งหมดจากฐานข้อมูล
        if (opt.isEmpty()) {
            throw PetException.notFound();
        }
        Pet pet = opt.get();
        return res(pet, pet.getUserId());
    }

    public List<PetProfileResponse> getMyPet() throws BaseException {
        //user
        Optional<String> opt1 = SecurityUtil.getCurrentUserId();
        if (opt1.isEmpty()) {
            throw UserException.unauthorized();
        }
        String userId = opt1.get();
        Optional<User> optUser = userService.findById(Integer.parseInt(userId));
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }
        User user = optUser.get();

        List<Pet> opt = petService.findByUserId(user);
        if (opt.isEmpty()) {
            throw PetException.notFound();
        }

        return resList(opt, user);
    }

    //find
    public Optional<Pet> findById(int id) throws BaseException {
        return petService.findById(id);
    }

    //delete

    public String deleteImage(AddPetRequest id) throws BaseException {

        Optional<Pet> optPet = findById(id.getId());
        if (optPet.isEmpty()) {
            throw PetException.notFound();
        }
        Pet pet = optPet.get();
        String fileName = pet.getPetPhoto();
        String filePath = path + File.separator + fileName;

        // สร้างอ็อบเจ็กต์ File จาก path ของไฟล์
        File imageFile = new File(filePath);

        // ตรวจสอบว่าไฟล์มีอยู่จริงหรือไม่ และลบไฟล์ออกจากเครื่อง server
        if (imageFile.exists()) {
            boolean deleted = imageFile.delete();
            if (!deleted) {
                throw FileException.deleteImageFailed();
            }
        } else {
            throw FileException.deleteNoFile();
        }

        pet.setPetPhoto(null);
        petService.update(pet);

        return "Successfully deleted the" + fileName + "image.";
    }

    public String deletePet(AddPetRequest id) throws BaseException {
        Optional<Pet> opt = petService.findById(id.getId());
        if (opt.isEmpty()) {
            throw PetException.notFound();
        }
        Pet pet = opt.get();
        String name = pet.getPetName();
        String fileName = pet.getPetPhoto();
        String filePath = path + File.separator + fileName;

        // สร้างอ็อบเจ็กต์ File จาก path ของไฟล์
        File imageFile = new File(filePath);

        // ตรวจสอบว่าไฟล์มีอยู่จริงหรือไม่ และลบไฟล์ออกจากเครื่อง server
        if (imageFile.exists()) {
            boolean deleted = imageFile.delete();
            if (!deleted) {
                throw FileException.deleteImageFailed();
            }
        } else {
            throw FileException.deleteNoFile();
        }
        petService.deleteById(id.getId());
        return "Successfully deleted" + name + "pet account.";
    }

    private PetProfileResponse res(Pet pet, User user) throws PetException {        //pet type
        Optional<PetType> optionalPetType = petTypeService.findByIdPet(pet.getPetTypeId().getId());
        if (optionalPetType.isEmpty()) {
            throw PetException.notFoundType();
        }
        PetType type = optionalPetType.get();

        PetProfileResponse data = new PetProfileResponse();
        data.setId(pet.getId());
        data.setPetName(pet.getPetName());
        data.setBirthday(formatDate.format(pet.getBirthday()));
        data.setPetTypeId(type.getId());
        data.setUserOwner(user.getId());
        data.setPhotoPath(pet.getPetPhoto());
        return data;

    }

    private List<PetProfileResponse> resList(List<Pet> opt, User user) throws PetException {
        List<PetProfileResponse> response = new ArrayList<>();
        for (Pet pet : opt) {

            PetProfileResponse data = new PetProfileResponse();
            data.setId(pet.getId());
            data.setPetName(pet.getPetName());
            data.setBirthday(formatDate.format(pet.getBirthday()));
            data.setPetTypeId(pet.getPetTypeId().getId());
            data.setUserOwner(user.getId());
            data.setPhotoPath(pet.getPetPhoto());
            response.add(data);
        }
        return response;
    }

    /////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////

}




