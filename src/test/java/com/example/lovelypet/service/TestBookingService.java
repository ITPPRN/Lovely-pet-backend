package com.example.lovelypet.service;

import com.example.lovelypet.entity.Booking;
import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestBookingService {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;


    @Order(1)
    @Test
    void testCreate() throws BaseException {
        Optional<User> opt = userService.findLog(TestUserService.TestCreateData.userName);
//        Booking booking = bookingService.create(
//
//        );
//
//        //check not null
//        Assertions.assertNotNull(hotel);
//        Assertions.assertNotNull(hotel.getId());
//
//        //check equals
//        Assertions.assertEquals(TestCreateData.email, hotel.getEmail());
//        Assertions.assertEquals(TestCreateData.userName, hotel.getHotelUsername());
//
//        boolean isMatched = hotelService.matchPassword(TestCreateData.passWord, hotel.getPassword());
//        Assertions.assertTrue(isMatched);
//
//        Assertions.assertEquals(TestCreateData.name, hotel.getHotelName());
//        Assertions.assertEquals(TestCreateData.tel, hotel.getHotelTel());

    }

//    @Order(2)
//    @Test
//    void testUpdate() throws BaseException {
//        Optional<Hotel> opt = hotelService.findLog(TestCreateData.userName);
//        Assertions.assertTrue(opt.isPresent());
//
//        Hotel hotel = opt.get();
//
//        Hotel updatedUser = hotelService.updateName(hotel.getId(), TestUpdateData.name);
//
//        Assertions.assertNotNull(updatedUser);
//        Assertions.assertEquals(TestUpdateData.name, updatedUser.getHotelName());
//    }
//
//    @Order(9)
//    @Test
//    void testDelete() throws BaseException {
//        Optional<Hotel> opt = hotelService.findLog(TestCreateData.userName);
//        Assertions.assertTrue(opt.isPresent());
//
//        Hotel hotel = opt.get();
//        hotelService.deleteByIdU(String.valueOf(hotel.getId()));
//
//        Optional<Hotel> optDelete = hotelService.findLog(TestCreateData.userName);
//        Assertions.assertTrue(optDelete.isEmpty());
//
//    }

    interface TestCreateData {
        // กำหนดค่าวันที่แบบสตริง
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ParsePosition pos = new ParsePosition(0);
        ParsePosition pos1 = new ParsePosition(0);
        Date start =dateFormat.parse("21/06/2023",pos) ;
        Date end =dateFormat.parse("23/06/2023",pos1) ;

        LocalDate date = LocalDate.now();

        int n = 1;
    }

    interface TestUpdateData {


        String email = "1111@gmail.com";
        String passWord = "1111";
        String name = "1111";
        String userName = "11111";
        String phoneNumber = "111111";
    }


}
