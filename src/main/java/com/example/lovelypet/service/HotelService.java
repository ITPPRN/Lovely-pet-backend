package com.example.lovelypet.service;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.HotelException;
import com.example.lovelypet.repository.HotelRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HotelService {

    private final HotelRepository repository;
    private final PasswordEncoder passwordEncoder;

    public HotelService(HotelRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Hotel create(
            String hotelUsername,
            String hotelPassword,
            String hotelName,
            String hotelTel,
            String email,
            String location,
            String token,
            Date tokenExpireDate,
            String additionalNotes
    ) throws BaseException {

        //validate
        if (Objects.isNull(hotelUsername)) {
            throw HotelException.createUserNameNull();
        }
        if (Objects.isNull(hotelPassword)) {
            throw HotelException.createPassWordNull();
        }
        if (Objects.isNull(hotelName)) {
            throw HotelException.createNameNull();
        }
        if (Objects.isNull(hotelTel)) {
            throw HotelException.createTelNull();
        }
        if (Objects.isNull(email)) {
            throw HotelException.createEmailNull();
        }
        if (Objects.isNull(location)) {
            throw HotelException.createAddressNull();
        }
        //verify
        if (repository.existsByEmail(email)) {
            throw HotelException.createEmailDuplicated();
        }
        if (repository.existsByHotelUsername(hotelUsername)) {
            throw HotelException.createUserNameDuplicated();
        }


        //save
        else {
            Hotel entity = new Hotel();
            entity.setHotelUsername(hotelUsername);
            entity.setPassword(passwordEncoder.encode(hotelPassword));
            entity.setEmail(email);
            entity.setHotelName(hotelName);
            entity.setLocation(location);
            entity.setHotelTel(hotelTel);
            entity.setRating(0.0F);
            entity.setVerify("waite");
            entity.setOpenState("CLOSE");
            entity.setToken(token);
            entity.setTokenExpire(tokenExpireDate);
            if (Objects.nonNull(additionalNotes)) {
                entity.setAdditionalNotes(additionalNotes);
            }
            return repository.save(entity);
        }
    }

    public void deleteByIdU(int idU) {
        repository.deleteById(idU);
    }

    public Optional<Hotel> findLog(String userName) {
        return repository.findByHotelUsername(userName);
    }

    public boolean matchPassword(String requestPass, String dataPass) {
        return passwordEncoder.matches(requestPass, dataPass);
    }

    public Optional<Hotel> findById(int idU) throws BaseException {
        return repository.findById(idU);
    }

    public Hotel update(Hotel hotel) throws BaseException {
        return repository.save(hotel);
    }

    public Optional<Hotel> findByToken(String token) {
        return repository.findByToken(token);
    }

    public Optional<Hotel> findByDateDelete(Date date) {
        return repository.findByDateDeleteAccount(date);
    }

    public Optional<Hotel> findByEmail(String email) {
        return repository.findByToken(email);
    }

    public List<Hotel> findByStateVerifyAndStateOpen(String stateVerify, String stateOpen) {
        return repository.findByOpenStateAndVerifyOrderByRatingDesc(stateOpen, stateVerify);
    }

    public List<Hotel> findByVerify(String stateVerify) {
        return repository.findByVerify(stateVerify);
    }
    ////////////////////////////////
    ////////////////////////////////
}
