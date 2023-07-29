package com.example.lovelypet.business;

import com.example.lovelypet.entity.*;
import com.example.lovelypet.exception.*;
import com.example.lovelypet.model.BookingRequest;
import com.example.lovelypet.service.*;
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
import java.time.LocalDateTime;
import java.util.*;


@Service
public class BookingBusiness {

    private final PetService petService;

    private final HotelService hotelService;

    private final RoomService roomService;

    private final UserService userService;

    private final BookingService bookingService;
    private final String part = "src/main/resources/imageUpload/payment";

    public BookingBusiness(PetService petService, HotelService hotelService, RoomService roomService, UserService userService, BookingService bookingService) {
        this.petService = petService;
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.userService = userService;
        this.bookingService = bookingService;
    }

    public String reserve(BookingRequest request) throws BaseException, IOException {

        //user
        Optional<User> optUser = userService.findById(request.getUserId());
        if (!optUser.isPresent()) {
            throw UserException.notFound();
        }

        //hotel
        Optional<Hotel> optHotel = hotelService.findById(request.getHotelId());
        if (!optHotel.isPresent()) {
            throw HotelException.notFound();
        }

        //room
        Optional<Room> optRoom = roomService.findById(request.getRoomId());
        if (!optRoom.isPresent()) {
            throw RoomException.notFound();
        }

        //pet
        Optional<Pet> optPet = petService.findById(request.getPetId());
        if (!optPet.isPresent()) {
            throw PetException.notFound();
        }

        //date start
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ParsePosition pos = new ParsePosition(0);
        Date startDate = dateFormat.parse(request.getStart(), pos);

        //date end
        ParsePosition pos2 = new ParsePosition(0);
        Date endDate = dateFormat.parse(request.getEnd(), pos2);

        //date now
        LocalDateTime date = LocalDateTime.now();

        String fileImage = null;
        if (request.getFile() == null || request.getFile().isEmpty()) {
            fileImage = uploadImage(request.getFile());
        }

        //add pet to database
        Booking response = bookingService.create(
                optUser.get(),
                optHotel.get(),
                optRoom.get(),
                optPet.get(),
                startDate,
                endDate,
                date,
                request.getPaymentMethod(),
                fileImage

        );

        return "response";
    }

    public String uploadImage(MultipartFile file) throws IOException, BaseException {

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

        return fileName;
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




