package com.example.lovelypet.business;

import com.example.lovelypet.entity.PhotoRoom;
import com.example.lovelypet.entity.Room;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.FileException;
import com.example.lovelypet.exception.PhotoRoomException;
import com.example.lovelypet.exception.RoomException;
import com.example.lovelypet.model.RoomRequest;
import com.example.lovelypet.service.PhotoRoomService;
import com.example.lovelypet.service.RoomService;
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
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PhotoRoomBusiness {

    private final PhotoRoomService photoRoomService;

    private final RoomService roomService;

    private final String path = "src/main/resources/imageUpload/imageRoomUpload";

    public PhotoRoomBusiness(PhotoRoomService photoRoomService, RoomService roomService) {
        this.photoRoomService = photoRoomService;
        this.roomService = roomService;
    }

    //upload image
    public String storeImage(MultipartFile[] files, int id) throws BaseException {
        List<PhotoRoom> list = findByRoomId(id);
        if (list.size() + files.length - 1 > 3) {
            throw PhotoRoomException.imageOverload();
        }
        List<String> filenames = Arrays.stream(files)
                .map(file -> {
                    String filename;
                    try {
                        filename = uploadImage(file, id);
                    } catch (IOException | BaseException e) {
                        throw new RuntimeException(e);
                    }
                    return filename;
                })
                .collect(Collectors.toList());

        return "Files uploaded successfully: " + String.join(", ", filenames);
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
            throw RoomException.createRoomIdNull();
        }

        Optional<Room> optIdRoom = roomService.findById(id);
        if (optIdRoom.isEmpty()) {
            throw RoomException.notFound();
        }
        Room idRoom = optIdRoom.get();

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

        // Save the image file
        //file.transferTo(filePath);
        Path path = Paths.get(filePath);
        Files.write(path, file.getBytes());

        // Save the image information in the database
        photoRoomService.create(fileName, idRoom);

        return fileName;
    }

    // สร้างชื่อไฟล์ที่ไม่ซ้ำกัน
    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }

    public Optional<PhotoRoom> findById(int id) {
        return photoRoomService.findById(id);
    }

    public ResponseEntity<InputStreamResource> getImageById(RoomRequest id) {
        Optional<PhotoRoom> imageEntity = photoRoomService.findByName(id.getNamePhoto());
        if (imageEntity.isPresent()) {
            String filename = imageEntity.get().getPhotoRoomPartFile();
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

    public List<String> getImageUrl(RoomRequest id) throws BaseException {
        List<PhotoRoom> images = findByRoomId(id.getId()); // ดึงข้อมูลรูปภาพทั้งหมดจากฐานข้อมูล
        List<String> response = new ArrayList<>();
        for (PhotoRoom image : images) {
            // สร้าง URL สำหรับแสดงรูปภาพ
            String imageUrl = image.getPhotoRoomPartFile();
            response.add(imageUrl);
        }
        return response;
    }

    public List<PhotoRoom> findByRoomId(int id) throws BaseException {
        Optional<Room> opt = roomService.findById(id);
        if (opt.isEmpty()) {
            throw RoomException.notFound();
        }
        Room room = opt.get();
        return photoRoomService.findByRoomId(room);
    }


    ///////////////////////////////
    //delete image
    public String deleteImage(RoomRequest name) throws BaseException {
        Optional<PhotoRoom> opt = photoRoomService.findByName(name.getNamePhoto());
        if (opt.isEmpty()) {
            throw PhotoRoomException.notFound();
        }
        String fileName = opt.get().getPhotoRoomPartFile();
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

        photoRoomService.deleteByIdImage(opt.get().getId());


        return "Successfully deleted the" + fileName + "image.";
    }
    //////////////////////////////
}




