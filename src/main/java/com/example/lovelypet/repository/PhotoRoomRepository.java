package com.example.lovelypet.repository;

import com.example.lovelypet.entity.PhotoHotel;
import com.example.lovelypet.entity.PhotoRoom;
import com.example.lovelypet.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRoomRepository extends CrudRepository<PhotoRoom, String> {

    Optional<PhotoRoom> findById(int id);

    List<PhotoRoom> findByRoomId(Room id);

    void deleteById(int id);
    Optional<PhotoRoom> findByPhotoRoomPartFile(String name);



}