package com.example.lovelypet.business;

import com.example.lovelypet.entity.PhotoRoom;
import com.example.lovelypet.entity.Room;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.FileException;
import com.example.lovelypet.exception.RoomException;
import com.example.lovelypet.service.PhotoRoomService;
import com.example.lovelypet.service.RoomService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
public class PhotoRoomBusiness {

    private final PhotoRoomService photoRoomService;

    private final RoomService roomService;

    private final String part = "src/main/resources/imageUpload/imageRoomUpload";

    public PhotoRoomBusiness(PhotoRoomService photoRoomService, RoomService roomService) {
        this.photoRoomService = photoRoomService;
        this.roomService = roomService;
    }

    public PhotoRoom uploadImage(MultipartFile file, int id) throws IOException, BaseException {

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

        Optional<Room> optIdRoom = roomService.findById(id);
        Room idRoom = optIdRoom.get();

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
        PhotoRoom response = photoRoomService.create(fileName, idRoom);

        return response;
    }

    // สร้างชื่อไฟล์ที่ไม่ซ้ำกัน
    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }

    public Optional<PhotoRoom> findById(int id) {
        return photoRoomService.findById(id);
    }

    public ResponseEntity<InputStreamResource> getImageById(int id) {
        Optional<PhotoRoom> imageEntity = photoRoomService.findById(id);
        if (imageEntity.isPresent()) {
            String filename = imageEntity.get().getPhotoRoomPartFile();
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

    public List<String> getImageUrl(int id) {
        List<PhotoRoom> images = findByRoomId(id); // ดึงข้อมูลรูปภาพทั้งหมดจากฐานข้อมูล
        List<String> response = new ArrayList<>();
        for (PhotoRoom image : images) {
            // สร้าง URL สำหรับแสดงรูปภาพ
            String imageUrl = part + File.separator + image.getPhotoRoomPartFile();
            response.add(imageUrl);
        }
        return response;
    }

    public List<PhotoRoom> findByRoomId(int id) {
        Optional<Room> opt = roomService.findById(id);
        Room room = opt.get();
        return photoRoomService.findByRoomId(room);
    }
}



