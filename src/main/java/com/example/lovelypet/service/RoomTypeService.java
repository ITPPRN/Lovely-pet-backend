package com.example.lovelypet.service;

import com.example.lovelypet.entity.RoomType;
import com.example.lovelypet.repository.RoomTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    public RoomType create(String roomTypeName) {

        RoomType entity = new RoomType();
        entity.setName(roomTypeName);
        return roomTypeRepository.save(entity);
    }

    public Optional<RoomType> findByName(String name) {

        return roomTypeRepository.findByName(name);
    }
}
