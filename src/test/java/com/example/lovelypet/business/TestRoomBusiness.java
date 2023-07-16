package com.example.lovelypet.business;

import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.RoomRequest;
import com.example.lovelypet.service.HotelService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestRoomBusiness {

    @Autowired
    private RoomBusiness roomBusiness;

    @Autowired
    private HotelService hotelService;


    @Order(1)
    @Test
    void testAddRoom() throws BaseException {

        hotelService.create(
                "11111",
                "11111",
                "ggggg",
                "22222",
                "111111",
                "64648"
        );

        RoomRequest request = new RoomRequest();
        request.setType("Normal");
        request.setTotal(15);
        request.setHotelId(1);
        request.setPrice(300);
        request.setDetails("Fan room");

        roomBusiness.addRoom(request);
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
}

