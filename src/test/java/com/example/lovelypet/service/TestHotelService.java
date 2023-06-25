package com.example.lovelypet.service;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.Pet;
import com.example.lovelypet.entity.PetType;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.service.PetService;
import com.example.lovelypet.service.PetTypeService;
import com.example.lovelypet.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestHotelService {

    @Autowired
    private HotelService hotelService;


    @Order(1)
    @Test
    void testCreate() throws BaseException {
        Hotel hotel = hotelService.create(
                TestCreateData.userName,
                TestCreateData.passWord,
                TestCreateData.name,
                TestCreateData.tel,
                TestCreateData.email,
                TestCreateData.location
        );

        //check not null
        Assertions.assertNotNull(hotel);
        Assertions.assertNotNull(hotel.getId());

        //check equals
        Assertions.assertEquals(TestCreateData.email, hotel.getEmail());
        Assertions.assertEquals(TestCreateData.userName, hotel.getHotelUsername());

        boolean isMatched = hotelService.matchPassword(TestCreateData.passWord,hotel.getPassword());
        Assertions.assertTrue(isMatched);

        Assertions.assertEquals(TestCreateData.name, hotel.getHotelName());
        Assertions.assertEquals(TestCreateData.tel, hotel.getHotelTel());

    }

    @Order(2)
    @Test
    void testUpdate() throws BaseException {
        Optional<Hotel> opt = hotelService.findLog(TestCreateData.userName);
        Assertions.assertTrue(opt.isPresent());

        Hotel hotel = opt.get();

        Hotel updatedUser = hotelService.updateName(hotel.getId(), TestUpdateData.name);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(TestUpdateData.name, updatedUser.getHotelName());
    }

    @Order(9)
    @Test
    void testDelete() throws BaseException {
        Optional<Hotel> opt = hotelService.findLog(TestCreateData.userName);
        Assertions.assertTrue(opt.isPresent());

        Hotel hotel = opt.get();
        hotelService.deleteByIdU(String.valueOf(hotel.getId()));

        Optional<Hotel> optDelete = hotelService.findLog(TestCreateData.userName);
        Assertions.assertTrue(optDelete.isEmpty());

    }

    interface TestCreateData {

        String email = "test@gmail.com";
        String passWord = "test";
        String name = "test1";
        String userName = "test1";
        String tel = "0876543210";
        String location = "144/4 ม.7 ต.ในเมือง อ.ในเมือง จ.ขอนแก่น";
    }

    interface TestUpdateData {

        String email = "1111@gmail.com";
        String passWord = "1111";
        String name = "1111";
        String userName = "11111";
        String phoneNumber = "111111";
    }


}
