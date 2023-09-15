package com.example.lovelypet.business;

import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.AddPetRequest;
import com.example.lovelypet.model.AdditionalServiceRequest;
import com.example.lovelypet.model.HotelRegisterRequest;
import com.example.lovelypet.model.RoomRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestRoomBusiness {

    @Autowired
    private RoomBusiness roomBusiness;

    @Autowired
    private HotelBusiness hotelBusiness;

    @Autowired
    private PetBusiness petBusiness;

    @Autowired
    private PhotoRoomBusiness photoRoomBusiness;

    @Autowired
    private AdditionalServiceBusiness additionalServiceBusiness;


    @Order(1)
    @Test
    void testAddHotel() throws BaseException, IOException {
        HotelRegisterRequest hotelRequest = new HotelRegisterRequest();
        hotelRequest.setHotelTel("0687945123");
        hotelRequest.setHotelUsername("test01Hotel1111");
        hotelRequest.setHotelName("Pet Book11");
        hotelRequest.setEmail("ittipol092363183211@gmail.com");
        hotelRequest.setPassword("test01");
        hotelRequest.setLocation("bangkok");
        hotelRequest.setAdditionalNotes("hey i love you \n hare we go \n lest right");
        hotelBusiness.register(hotelRequest);
    }


    @Order(2)
    @Test
    void testAddRoom() throws BaseException, IOException {
        RoomRequest roomRequest = new RoomRequest();
        roomRequest.setTotal(20);
        roomRequest.setType("VIP");
        roomRequest.setPrice(300);
        roomRequest.setHotelId(1);
        roomRequest.setDetails("solo");
        roomBusiness.addRoom(roomRequest);
    }
    @Order(3)
    @Test
    void testAddAdditionalService() throws BaseException, IOException {
        AdditionalServiceRequest addRe = new AdditionalServiceRequest();
        addRe.setId(1);
        addRe.setName("Food");
        addRe.setPrice(150);
        additionalServiceBusiness.addService(addRe);
    }

    @Order(3)
    @Test
    void testAddPet() throws BaseException, IOException {

        AddPetRequest add = new AddPetRequest();
        add.setBirthday("10/02/2566");
        add.setName("bob");
        add.setId(1);
        add.setType("DOG");
        petBusiness.addMyPet(add);
    }
}

