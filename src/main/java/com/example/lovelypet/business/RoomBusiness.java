package com.example.lovelypet.business;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.PhotoRoom;
import com.example.lovelypet.entity.Room;
import com.example.lovelypet.entity.RoomType;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.HotelException;
import com.example.lovelypet.exception.RoomException;
import com.example.lovelypet.model.RoomRequest;
import com.example.lovelypet.model.RoomResponseList;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.service.RoomService;
import com.example.lovelypet.service.RoomTypeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomBusiness {

    private final RoomService roomService;

    private final RoomTypeService roomTypeService;

    private final HotelService hotelService;

    private final PhotoRoomBusiness photoRoomBusiness;


    public RoomBusiness(RoomService roomService, RoomTypeService roomTypeService, HotelService hotelService, PhotoRoomBusiness photoRoomBusiness) {
        this.roomService = roomService;
        this.roomTypeService = roomTypeService;
        this.hotelService = hotelService;
        this.photoRoomBusiness = photoRoomBusiness;
    }

    public String addRoom(RoomRequest request) throws BaseException {
        int total = request.getTotal();
        String type = request.getType();

        Hotel hotelId = findHotelById(request.getHotelId());

        Optional<RoomType> optType = roomTypeService.findByName(type);
        if (optType.isEmpty()) {
            roomTypeService.create(type);
            optType = roomTypeService.findByName(type);
            if (optType.isEmpty()) {
                throw RoomException.notFoundRoomType();
            }
        }
        RoomType roomType = optType.get();

        for (int i = 1; i <= total; i++) {
            List<Room> opt = roomService.findByHotelId(hotelId);
            int roomNumber = opt.size() + 1;
            roomService.create(
                    hotelId,
                    roomType,
                    request.getPrice(),
                    request.getDetails(),
                    roomNumber
            );
        }

        return "successfully added  list room of" + total + " rooms has been.";
    }

    public String updateRoom(RoomRequest request) throws BaseException {

        String type = request.getType();

        Optional<RoomType> optType = roomTypeService.findByName(type);
        if (optType.isEmpty()) {
            roomTypeService.create(type);
            optType = roomTypeService.findByName(type);
            if (optType.isEmpty()) {
                throw RoomException.notFoundRoomType();
            }
        }
        RoomType roomType = optType.get();
        Room room = roomService.update(
                request.getId(),
                request.getDetails(),
                request.getPrice(),
                request.getStatus(),
                roomType
        );
        return "Successfully updated room NO." + room.getRoomNumber() + " information.";
    }


//    public String deleteU(RoomRequest id) throws BaseException {
//        Optional<Room> opt = roomService.findById(id.getId());
//        if (opt.isEmpty()) {
//            throw RoomException.notFound();
//        }
//        Room room = opt.get();
//
////        List<PhotoRoom> images = photoRoomBusiness.findByRoomId(id.getId());
////        for (PhotoRoom image : images) {
////            // ส่ง ID ของรูปภาพเพื่อทำการลบโดยตรง
////            photoRoomBusiness.deleteImageById(image.getId());
////        }
//
//        roomService.deleteByIdU(room.getId());
//
//        // ทำการตรวจสอบว่าห้องถูกลบออกจากฐานข้อมูลหรือไม่
//        Optional<Room> confirm = roomService.findById(room.getId());
//        if (confirm.isEmpty()) {
//            return "Delete room number " + room.getRoomNumber() + " success";
//        } else {
//            return "Delete room number " + room.getRoomNumber() + " fail";
//        }
//    }


    @Transactional
    public String deleteU(RoomRequest id) throws BaseException {
        Optional<Room> opt = roomService.findById(id.getId());
        if (opt.isEmpty()) {
            throw RoomException.notFound();
        }
        Room room = opt.get();

        List<PhotoRoom> images = photoRoomBusiness.findByRoomId(id.getId()); // ดึงข้อมูลรูปภาพทั้งหมดจากฐานข้อมูล
        for (PhotoRoom image : images) {
            RoomRequest data = new RoomRequest();
            data.setNamePhoto(image.getPhotoRoomPartFile());
            photoRoomBusiness.deleteImage(data);
        }
        roomService.deleteByIdU(room.getId());

        Optional<Room> confirm = roomService.findById(room.getId());
        if (confirm.isEmpty()) {
            return "Delete room number" + room.getRoomNumber() + "success";
        } else {
            return "Delete room number" + room.getRoomNumber() + "fail";
        }
    }

    public Hotel findHotelById(int id) throws BaseException {
        Optional<Hotel> opt = hotelService.findById(id);
        if (opt.isEmpty()) {
            throw HotelException.notFound();
        }
        return opt.get();
    }

    public Optional<Room> findById(int id) {
        return roomService.findById(id);
    }


    /////////////////////////////////////////////////
    public List<RoomResponseList> listRoom(RoomRequest request) throws BaseException {
        Optional<Hotel> opt = hotelService.findById(request.getHotelId());
        if (opt.isEmpty()) {
            throw HotelException.notFound();
        }
        List<Room> roomList = roomService.findByHotelId(opt.get());
        return resList(roomList);
    }

    public List<RoomResponseList> listStateRoom(RoomRequest request) throws BaseException {
        Optional<Hotel> opt = hotelService.findById(request.getHotelId());
        if (opt.isEmpty()) {
            throw HotelException.notFound();
        }
        List<Room> rooms = roomService.findByHotelIdAndState(opt.get(), request.getStatus());
        if (rooms.isEmpty()) {
            throw RoomException.notFound();
        }
        return resList(rooms);
    }

    public List<RoomResponseList> resList(List<Room> rooms) {
        List<RoomResponseList> response = new ArrayList<>();
        for (Room room : rooms) {
            RoomResponseList data = new RoomResponseList();
            data.setId(room.getId());
            data.setRoomNumber(room.getRoomNumber());
            data.setRoomDetails(room.getRoomDetails());
            data.setRoomPrice(room.getRoomPrice());
            data.setType(room.getRoomTypeId().getName());
            data.setStatus(room.getStatus());
            response.add(data);
        }
        return response;
    }
    ////////////////////////////////////////////////
}


//    public User findUserById(int id) throws BaseException {
//
//        Optional<User> opt = userService.findById(id);
//        if (opt.isEmpty()) {
//            throw UserException.notFound();
//        }
//        User response = opt.get();
//        return response;
//    }


