package com.example.lovelypet.service;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.Room;
import com.example.lovelypet.entity.RoomType;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.RoomException;
import com.example.lovelypet.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    public Room create(
            Hotel hotelId,
            RoomType roomType,
            float roomPrice,
            String Details
    ) throws BaseException {

        //validate
        if (Objects.isNull(hotelId)) {
            throw RoomException.createHotelIdNull();
        }
        if (Objects.isNull(roomType)) {
            throw RoomException.createRoomIdNull();
        }
        if (Objects.isNull(roomPrice)) {
            throw RoomException.createRoomPriceNull();
        }
        //verify
        //create
        Room entity = new Room();
        entity.setHotelId(hotelId);
        entity.setRoomTypeId(roomType);
        entity.setRoomPrice(roomPrice);
        entity.setStatus("empty");
        if (!Objects.isNull(Details)) {
            entity.setRoomDetails(Details);
        }
        return roomRepository.save(entity);
    }

    public Room update(int id, String details, float price, String status) throws BaseException {
        Optional<Room> opt = roomRepository.findById(id);
        if (opt.isEmpty()) {
            throw RoomException.notFound();
        }
        Room room = opt.get();

        if (!Objects.isNull(details)) {
            room.setRoomDetails(details);
        }

        if (price != 0) {
            room.setRoomPrice(price);
        }

        if (!Objects.isNull(status)) {
            room.setStatus(status);
        }


        return roomRepository.save(room);
    }

    public Optional<Room> findById(int id) {
        return roomRepository.findById(id);
    }

    public Optional<Room> findByIdAndHotelId(int id, Hotel hotelId) {
        return roomRepository.findByIdAndHotelId(id, hotelId);
    }

    public void deleteByIdU(String idU) {
        roomRepository.deleteById(idU);
    }


}
/* public int getLastRoomNumber() {
        return roomRepository.findMaxRoomNumber();
    }*/