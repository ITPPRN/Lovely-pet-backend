package com.example.lovelypet.business;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.PhotoHotel;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.FileException;
import com.example.lovelypet.exception.RoomException;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.service.PhotoHotelService;
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
public class PhotoHotelBusiness {

    private final PhotoHotelService photoHotelService;

    private final HotelService hotelService;

    private final String part = "src/main/resources/imageUpload/imageHotelUpload";

    public PhotoHotelBusiness(PhotoHotelService photoHotelService, HotelService hotelService) {
        this.photoHotelService = photoHotelService;
        this.hotelService = hotelService;
    }

    // เมธอดในการอ่านรูปภาพและเก็บข้อมูลลงในตัวแปร byte[]
    public static byte[] readImage(String filePath) {
        File imageFile = new File(filePath);
        byte[] imageData = new byte[(int) imageFile.length()];

        try (FileInputStream fis = new FileInputStream(imageFile)) {
            fis.read(imageData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageData;
    }

    public PhotoHotel uploadImage(MultipartFile file, int id) throws IOException, BaseException {

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

        Optional<Hotel> optHotel = hotelService.findById(id);
        Hotel idHotel = optHotel.get();

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
        PhotoHotel response = photoHotelService.create(fileName, idHotel);

        return response;
    }

    // สร้างชื่อไฟล์ที่ไม่ซ้ำกัน
    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }

    public Optional<PhotoHotel> findById(int id) {
        return photoHotelService.findById(id);
    }

    public ResponseEntity<InputStreamResource> getImageById(int id) {
        Optional<PhotoHotel> imageEntity = photoHotelService.findById(id);
        if (imageEntity.isPresent()) {
            String filename = imageEntity.get().getPhotoHotelFile();
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

    public List<String> getImageUrl(int id) throws BaseException {
        List<PhotoHotel> images = findByHotelId(id); // ดึงข้อมูลรูปภาพทั้งหมดจากฐานข้อมูล
        List<String> response = new ArrayList<>();
        for (PhotoHotel image : images) {
            // สร้าง URL สำหรับแสดงรูปภาพ
            String imageUrl = part + File.separator + image.getPhotoHotelFile();
            response.add(imageUrl);
        }
        return response;
    }

    public List<PhotoHotel> findByHotelId(int id) throws BaseException {
        Optional<Hotel> opt = hotelService.findById(id);
        Hotel hotel = opt.get();
        return photoHotelService.findByHotelId(hotel);
    }
}




