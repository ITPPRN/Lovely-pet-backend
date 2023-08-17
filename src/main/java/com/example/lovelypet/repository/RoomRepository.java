package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, String> {

    Optional<Room> findById(int idU);


    List<Room> findByHotelId(Hotel hotel);

    List<Room> findByHotelIdAndStatus(Hotel hotel,String status);

    void deleteById(int id);

}

