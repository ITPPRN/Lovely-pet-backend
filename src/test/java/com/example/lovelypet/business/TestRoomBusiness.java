package com.example.lovelypet.business;

import com.example.lovelypet.exception.BaseException;
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
    private PhotoRoomBusiness photoRoomBusiness;


    @Order(1)
    @Test
    void testAddRoom() throws BaseException, IOException {

        HotelRegisterRequest request1 = new HotelRegisterRequest();
        request1.setEmail("564");
        request1.setPassword("5555");
        request1.setLocation("5555");
        request1.setHotelName("5555");
        request1.setHotelUsername("555");
        request1.setHotelTel("333");
        hotelBusiness.register(request1);

        HotelRegisterRequest request2 = new HotelRegisterRequest();
        request2.setEmail("5641");
        request2.setPassword("5515");
        request2.setLocation("55515");
        request2.setHotelName("55155");
        request2.setHotelUsername("5155");
        request2.setHotelTel("3313");
        hotelBusiness.register(request2);

        RoomRequest request = new RoomRequest();
        request.setType("Normal");
        request.setTotal(15);
        request.setHotelId(1);
        request.setPrice(300);
        request.setDetails("Fan room");
        roomBusiness.addRoom(request);

        RoomRequest request3 = new RoomRequest();
        request3.setType("VIP");
        request3.setTotal(15);
        request3.setHotelId(2);
        request3.setPrice(300);
        request3.setDetails("Fan room");
        roomBusiness.addRoom(request3);
    }

    @Order(2)
    @Test
    void testUpdateRoom() throws BaseException {

        RoomRequest request = new RoomRequest();
        request.setType("VIP");
        request.setTotal(5);
        request.setId(1);
        request.setPrice(1500);
        request.setDetails("Air Condition room");

        for (int i = 1; i <= request.getTotal(); i++) {
            request.setId(i);
            if (i%2 == 0){
                request.setStatus("unavailable");
            }else {
                request.setStatus("Empty");
            }
            roomBusiness.updateRoom(request);
        }
    }

//    @Order(3)
//    @Test
//    void testListPhotoRoomRoom() throws BaseException {
//
//      photoRoomBusiness.listImage(1);
//    }
//    @Order(3)
//    @Test
//    void testDeleteRoom() throws BaseException {
//        roomBusiness.deleteU(1);
//    }
}

