package com.example.lovelypet.business;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.PhotoHotel;
import com.example.lovelypet.exception.*;
import com.example.lovelypet.model.PhotoHotelRequest;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.service.PhotoHotelService;
import com.example.lovelypet.util.SecurityUtil;
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
public class PhotoHotelBusiness {

    private final PhotoHotelService photoHotelService;

    private final HotelService hotelService;

    private final String path = "src/main/resources/imageUpload/imageHotelUpload";

    public PhotoHotelBusiness(PhotoHotelService photoHotelService, HotelService hotelService) {
        this.photoHotelService = photoHotelService;
        this.hotelService = hotelService;
    }


    // upload image
    public String storeImage(MultipartFile[] files, int id) throws BaseException {
        List<PhotoHotel> list = findByHotelId(id);
        if (list.size() + files.length - 1 > 5) {
            throw PhotoHotelException.imageOverload();
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
            throw HotelException.createHotelIdNull();
        }

        Optional<Hotel> optHotel = hotelService.findById(id);
        if (optHotel.isEmpty()) {
            throw HotelException.notFound();
        }
        Hotel idHotel = optHotel.get();

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

        try {
            // Save the image file
            //file.transferTo(filePath);
            Path path = Paths.get(filePath);
            Files.write(path, file.getBytes());

            // Save the image information in the database
            photoHotelService.create(fileName, idHotel);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + fileName, e);

        }
    }

    // สร้างชื่อไฟล์ที่ไม่ซ้ำกัน
    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }

    public Optional<PhotoHotel> findById(int id) {
        return photoHotelService.findById(id);
    }

    public ResponseEntity<InputStreamResource> getImageByName(PhotoHotelRequest name) {
        Optional<PhotoHotel> imageEntity = photoHotelService.findByName(name.getName());
        if (imageEntity.isPresent()) {
            String filename = imageEntity.get().getPhotoHotelFile();
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

    public List<String> getImageUrl(PhotoHotelRequest id) throws BaseException {
        List<PhotoHotel> images = findByHotelId(id.getIdHotel()); // ดึงข้อมูลรูปภาพทั้งหมดจากฐานข้อมูล
        List<String> response = new ArrayList<>();
        for (PhotoHotel image : images) {
            // สร้าง URL สำหรับแสดงรูปภาพ
            String imageUrl = image.getPhotoHotelFile();
            response.add(imageUrl);
        }
        return response;
    }


    //get id on token
    private int getCurrentId() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }
        String hotelId = opt.get();
        return Integer.parseInt(hotelId);
    }

    public List<PhotoHotel> findByHotelId(int id) throws BaseException {
        Optional<Hotel> opt = hotelService.findById(id);
        if (opt.isEmpty()) {
            throw HotelException.notFound();
        }
        Hotel hotel = opt.get();
        return photoHotelService.findByHotelId(hotel);
    }

    //delete image
    public String deleteImage(PhotoHotelRequest name) throws BaseException {
        Optional<PhotoHotel> opt = photoHotelService.findByName(name.getName());
        if (opt.isEmpty()) {
            throw PhotoHotelException.notFound();
        }
        String fileName = opt.get().getPhotoHotelFile();
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

        photoHotelService.deleteByIdImage(opt.get().getId());


        return "Successfully deleted the" + fileName + "image.";
    }
    //////////////////////////////////////////////

    /////////////////////////////////////////////
}




