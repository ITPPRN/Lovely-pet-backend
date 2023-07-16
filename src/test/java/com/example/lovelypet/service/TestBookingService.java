//package com.example.lovelypet.service;
//
//import com.example.lovelypet.entity.*;
//import com.example.lovelypet.exception.BaseException;
//import com.example.lovelypet.util.SecurityUtil;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.text.ParsePosition;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.Optional;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class TestBookingService {
//
//    @Autowired
//    private BookingService bookingService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private HotelService hotelService;
//
//    @Autowired
//    private RoomTypeService roomTypeService;
//
//    @Autowired
//    private RoomService roomService;
//
//    @Autowired
//    private PetService petService;
//
//    @Autowired
//    private PetTypeService petTypeService;
//
//
//    @Order(1)
//    @Test
//    void testCreate() throws BaseException {
//
//        // USER
//        String token = SecurityUtil.generateToken();
//        User user = userService.create(
//                "test",
//                "1412eff",
//                "testName",
//                "test@gmail.com",
//                "0025566487",
//                token,
//
//        );
//        //check not null
//        Assertions.assertNotNull(user);
//        Assertions.assertNotNull(user.getId());
//
//        //Object User
//        Optional<User> optUser = userService.findLog("test");
//        Assertions.assertTrue(optUser.isPresent());
//        User userOpt = optUser.get();
//
//        //PET
//        PetType pt = petTypeService.create("DOG");
//        Assertions.assertNotNull(pt);
//        Optional<PetType> objPt = petTypeService.findByName("DOG");
//        PetType petType = objPt.get();
//        Pet pet = petService.create(userOpt, "Panda", "", petType);
//        Assertions.assertNotNull(pet);
//
//        //Object Pet
//        Optional<Pet> optPet = petService.findByIdAndUserId(1, userOpt);
//        Assertions.assertTrue(optPet.isPresent());
//        Pet petOpt = optPet.get();
//
//
//        //HOTEL
//        Hotel hotel = hotelService.create("testUserHOtel",
//                "res11555",
//                "Ari",
//                "1123586",
//                "twst@gmail.com",
//                "112 จ.ขอนแก่น"
//        );
//
//        //check not null
//        Assertions.assertNotNull(hotel);
//        Assertions.assertNotNull(hotel.getId());
//
//        RoomType roomType = roomTypeService.create("VIP");
//        Assertions.assertNotNull(roomType);
//        Assertions.assertEquals("VIP", roomType.getName());
//
//        Optional<Hotel> opt = hotelService.findById(1);
//        Assertions.assertTrue(opt.isPresent());
//        Hotel hotelOpt = opt.get();
//
//        //Room
//        Optional<RoomType> opt1 = roomTypeService.findByName(TestRoomService.TestCreateDataRoom.type);
//        Assertions.assertTrue(opt.isPresent());
//        RoomType roomTypeOpt = opt1.get();
//
//
//        for (int i = 1; i <= TestRoomService.TestCreateDataRoom.total; i++) {
//            Room room = roomService.create(
//                    hotelOpt,
//                    roomTypeOpt,
//                    TestRoomService.TestCreateDataRoom.price
//            );
//            //check not null
//            Assertions.assertNotNull(room);
//            Assertions.assertNotNull(room.getId());
//            //valueNumber=0;
//        }
//        //Object Room
//        Optional<Room> roomOpt1 = roomService.findByIdAndHotelId(1, hotelOpt);
//        Assertions.assertTrue(roomOpt1.isPresent());
//        Room optRoom1 = roomOpt1.get();
//
//        Optional<Room> roomOpt2 = roomService.findByIdAndHotelId(2, hotelOpt);
//        Assertions.assertTrue(roomOpt2.isPresent());
//        Room optRoom2 = roomOpt2.get();
//
//        Optional<Room> roomOpt10 = roomService.findByIdAndHotelId(10, hotelOpt);
//        Assertions.assertTrue(roomOpt10.isPresent());
//        Room optRoom10 = roomOpt10.get();
//
//        //////   1
//        Booking booking = bookingService.create(
//                userOpt,
//                hotelOpt,
//                optRoom1,
//                petOpt,
//                TestCreateData.start,
//                TestCreateData.end,
//                TestCreateData.date.atStartOfDay()
//        );
//        //check not null
//        Assertions.assertNotNull(booking);
//        Assertions.assertNotNull(booking.getId());
//
//        int id = optRoom1.getId();
//        Optional<Room> update = roomService.findById(id);
//        Assertions.assertTrue(update.isPresent());
//
//        Room roomUpdate = update.get();
//
//        Room updateRoom = roomService.update(id, "", 0, "unavailable");
//
//        Assertions.assertNotNull(updateRoom);
//
//        //////   2
//        Booking booking2 = bookingService.create(
//                userOpt,
//                hotelOpt,
//                optRoom2,
//                petOpt,
//                TestCreateData.start,
//                TestCreateData.end,
//                TestCreateData.date.atStartOfDay()
//        );
//        //check not null
//        Assertions.assertNotNull(booking2);
//        Assertions.assertNotNull(booking2.getId());
//
//        int id2 = optRoom2.getId();
//        Optional<Room> update2 = roomService.findById(id2);
//        Assertions.assertTrue(update2.isPresent());
//
//        Room roomUpdate2 = update2.get();
//
//        Room updateRoom2 = roomService.update(id2, "", 0, "unavailable");
//
//        Assertions.assertNotNull(updateRoom2);
//
//        //////   10
//        Booking booking10 = bookingService.create(
//                userOpt,
//                hotelOpt,
//                optRoom10,
//                petOpt,
//                TestCreateData.start,
//                TestCreateData.end,
//                TestCreateData.date.atStartOfDay()
//        );
//        //check not null
//        Assertions.assertNotNull(booking10);
//        Assertions.assertNotNull(booking10.getId());
//
//        int id10 = optRoom10.getId();
//        Optional<Room> update10 = roomService.findById(id10);
//        Assertions.assertTrue(update10.isPresent());
//
//        Room roomUpdate10 = update10.get();
//
//        Room updateRoom10 = roomService.update(id10, "", 0, "unavailable");
//
//        Assertions.assertNotNull(updateRoom10);
////
////        );
////
////        //check not null
////        Assertions.assertNotNull(hotel);
////        Assertions.assertNotNull(hotel.getId());
////
////        //check equals
////        Assertions.assertEquals(TestCreateData.email, hotel.getEmail());
////        Assertions.assertEquals(TestCreateData.userName, hotel.getHotelUsername());
////
////        boolean isMatched = hotelService.matchPassword(TestCreateData.passWord, hotel.getPassword());
////        Assertions.assertTrue(isMatched);
////
////        Assertions.assertEquals(TestCreateData.name, hotel.getHotelName());
////        Assertions.assertEquals(TestCreateData.tel, hotel.getHotelTel());
//
//    }
//
////    @Order(2)
////    @Test
////    void testUpdate() throws BaseException {
////        Optional<Hotel> opt = hotelService.findLog(TestCreateData.userName);
////        Assertions.assertTrue(opt.isPresent());
////
////        Hotel hotel = opt.get();
////
////        Hotel updatedUser = hotelService.updateName(hotel.getId(), TestUpdateData.name);
////
////        Assertions.assertNotNull(updatedUser);
////        Assertions.assertEquals(TestUpdateData.name, updatedUser.getHotelName());
////    }
////
////    @Order(9)
////    @Test
////    void testDelete() throws BaseException {
////        Optional<Hotel> opt = hotelService.findLog(TestCreateData.userName);
////        Assertions.assertTrue(opt.isPresent());
////
////        Hotel hotel = opt.get();
////        hotelService.deleteByIdU(String.valueOf(hotel.getId()));
////
////        Optional<Hotel> optDelete = hotelService.findLog(TestCreateData.userName);
////        Assertions.assertTrue(optDelete.isEmpty());
////
////    }
//
//    interface TestCreateData {
//        // กำหนดค่าวันที่แบบสตริง
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        ParsePosition pos = new ParsePosition(0);
//        ParsePosition pos1 = new ParsePosition(0);
//        Date start = dateFormat.parse("21/06/2023", pos);
//        Date end = dateFormat.parse("23/06/2023", pos1);
//
//        LocalDate date = LocalDate.now();
//
//        int n = 1;
//    }
//}
