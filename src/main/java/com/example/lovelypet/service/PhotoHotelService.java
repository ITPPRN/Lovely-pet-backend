package com.example.lovelypet.service;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.PhotoHotel;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.PhotoHotelException;
import com.example.lovelypet.repository.PhotoHotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PhotoHotelService {

    private final PhotoHotelRepository photoHotelRepository;


    public PhotoHotelService(PhotoHotelRepository photoHotelRepository) {
        this.photoHotelRepository = photoHotelRepository;
    }


    public PhotoHotel create(
            String partFile,
            Hotel hotelId
    ) throws BaseException {

        //validate
        if (Objects.isNull(partFile)) {
            throw PhotoHotelException.createPartFileNull();
        }

        if (Objects.isNull(hotelId)) {
            throw PhotoHotelException.createHotelIdNull();
        }

        //verify


        PhotoHotel entity = new PhotoHotel();
        entity.setPhotoHotelFile(partFile);
        entity.setHotelId(hotelId);
        return photoHotelRepository.save(entity);
    }

    public Optional<PhotoHotel> findById(int id) {
        return photoHotelRepository.findById(id);
    }

    public Optional<PhotoHotel> findByName(String name) {
        return photoHotelRepository.findByPhotoHotelFile(name);
    }

    public void deleteByIdImage(int id) {
        photoHotelRepository.deleteById(id);
    }
    public List<PhotoHotel> findByHotelId(Hotel hotel) throws BaseException {
        return photoHotelRepository.findByHotelId(hotel);
    }

}
