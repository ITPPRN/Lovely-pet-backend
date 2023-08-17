package com.example.lovelypet.service;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.Room;
import com.example.lovelypet.entity.RoomType;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.RoomException;
import com.example.lovelypet.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
            String Details,
            int roomNumber
    ) throws BaseException {

        //validate
        if (Objects.isNull(hotelId)) {
            throw RoomException.createHotelIdNull();
        }
        if (Objects.isNull(roomType)) {
            throw RoomException.createRoomIdNull();
        }
        if (roomPrice == 0.0) {
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
        entity.setRoomNumber(roomNumber);
        return roomRepository.save(entity);
    }

    public Room update(int id, String details, float price, String status, RoomType type) throws BaseException {
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

        if (!Objects.isNull(type)) {
            room.setRoomTypeId(type);
        }

        return roomRepository.save(room);
    }

    public Optional<Room> findById(int id) {
        return roomRepository.findById(id);
    }


    public void deleteByIdU(int idU) {
        roomRepository.deleteById(idU);
    }

    public List<Room> findByHotelId(Hotel hotel) {
        return roomRepository.findByHotelId(hotel);
    }


    public List<Room> findByHotelIdAndState(Hotel hotel, String status) {
        return roomRepository.findByHotelIdAndStatus(hotel, status);
    }


}
