//package com.example.lovelypet.service;
//
//import com.example.lovelypet.entity.Hotel;
//import com.example.lovelypet.entity.Room;
//import com.example.lovelypet.entity.RoomType;
//import com.example.lovelypet.entity.User;
//import com.example.lovelypet.exception.BaseException;
//import com.example.lovelypet.exception.RoomException;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class TestRoomService {
//
//    @Autowired
//    private RoomService roomService;
//
//    @Autowired
//    private HotelService hotelService;
//
//    @Autowired
//    private RoomTypeService roomTypeService;
//
//    @Order(1)
//    @Test
//    void testCreate() throws BaseException {
//        Hotel hotel = hotelService.create(
//                TestHotelService.TestCreateData.userName,
//                TestHotelService.TestCreateData.passWord,
//                TestHotelService.TestCreateData.name,
//                TestHotelService.TestCreateData.tel,
//                TestHotelService.TestCreateData.email,
//                TestHotelService.TestCreateData.location
//        );
//
//        //check not null
//        Assertions.assertNotNull(hotel);
//        Assertions.assertNotNull(hotel.getId());
//
//        //check equals
//        Assertions.assertEquals(TestHotelService.TestCreateData.email, hotel.getEmail());
//        Assertions.assertEquals(TestHotelService.TestCreateData.userName, hotel.getHotelUsername());
//
//        boolean isMatched = hotelService.matchPassword(TestHotelService.TestCreateData.passWord, hotel.getPassword());
//        Assertions.assertTrue(isMatched);
//
//        Assertions.assertEquals(TestHotelService.TestCreateData.name, hotel.getHotelName());
//        Assertions.assertEquals(TestHotelService.TestCreateData.tel, hotel.getHotelTel());
//
//        RoomType roomType = roomTypeService.create("VIP");
//        Assertions.assertNotNull(roomType);
//        Assertions.assertEquals("VIP", roomType.getName());
//
//        Optional<Hotel> opt = hotelService.findById(1);
//        Assertions.assertTrue(opt.isPresent());
//        Hotel hotelOpt = opt.get();
//
//        Optional<RoomType> opt1 = roomTypeService.findByName(TestCreateDataRoom.type);
//        Assertions.assertTrue(opt.isPresent());
//        RoomType roomTypeOpt = opt1.get();
//
//
//        for (int i = 1; i <= TestCreateDataRoom.total; i++) {
//            Room room = roomService.create(
//                    hotelOpt,
//                    roomTypeOpt,
//                    TestCreateDataRoom.price
//            );
//            //check not null
//            Assertions.assertNotNull(room);
//            Assertions.assertNotNull(room.getId());
//            //valueNumber=0;
//
//        }
//    }
//
//    @Order(2)
//    @Test
//    void testUpdate() throws BaseException {
//        Optional<Room> opt = roomService.findById(2);
//        Assertions.assertTrue(opt.isPresent());
//
//        Room room = opt.get();
//
//        Room updateRoom = roomService.update(room.getId(),TestUpdateData.details,TestUpdateData.price,TestUpdateData.status);
//
//        Assertions.assertNotNull(updateRoom);
//    }
//
//    @Order(3)
//    @Test
//    void testUpdate1() throws BaseException {
//        Optional<Room> opt = roomService.findById(3);
//        Assertions.assertTrue(opt.isPresent());
//
//        Room room = opt.get();
//
//        Room updateRoom = roomService.update(room.getId(),null,TestUpdateData.p,TestUpdateData.status);
//
//        Assertions.assertNotNull(updateRoom);
//    }
//
//    @Order(9)
//    @Test
//    void testDelete() throws BaseException {
//        Optional<Room> opt = roomService.findById(4);
//        Assertions.assertTrue(opt.isPresent());
//
//        Room room = opt.get();
//       roomService.deleteByIdU(String.valueOf(room.getId()));
//
//        Optional<Room> optDelete = roomService.findById(4);
//        Assertions.assertTrue(optDelete.isEmpty());
//
//    }
//
//    interface TestCreateDataRoom {
//        int hotelId = 1;
//        int roomTypeId = 1;
//        float price = 1500;
//        int total = 10;
//
//        String type = "VIP";
//    }
//
//    interface TestUpdateData {
//        String details = "ห้องรวม";
//        String status = "unavailable";
//        float price = 250;
//
//        float p = 0;
//    }
//}
