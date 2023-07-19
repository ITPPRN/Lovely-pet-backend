package com.example.lovelypet.service;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.HotelException;
import com.example.lovelypet.repository.HotelRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
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
            Date tokenExpireDate
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
            return repository.save(entity);
        }
    }

    public Hotel updateName(int idU, String name) throws BaseException {
        Optional<Hotel> opt = repository.findById(idU);
        if (opt.isEmpty()) {
            throw HotelException.notFound();
        }
        Hotel hotel = opt.get();
        hotel.setHotelName(name);

        return repository.save(hotel);
    }

    public void deleteByIdU(String idU) {
        repository.deleteById(idU);
    }

    public Optional<Hotel> findLog(String userName) throws BaseException {

        Optional<Hotel> hotel = repository.findByHotelUsername(userName);
        return hotel;
    }

    public boolean matchPassword(String requestPass, String dataPass) {
        return passwordEncoder.matches(requestPass, dataPass);
    }

    public Optional<Hotel> findById(int idU) throws BaseException {
        Optional<Hotel> hotel = repository.findById(idU);
        return hotel;
    }

    public Hotel resetPassword(int id, String newPassword) throws BaseException {
        Optional<Hotel> opt = repository.findById(id);
        Hotel hotel = opt.get();
        hotel.setPassword(newPassword);
        return repository.save(hotel);
    }

    public Hotel updateNormalData(int id, String name, String location, String hotelTel) throws BaseException {
        Optional<Hotel> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw HotelException.notFound();
        }
        Hotel hotel = opt.get();
        if (!Objects.isNull(name)) {
            hotel.setHotelName(name);
        }
        if (!Objects.isNull(location)) {
            hotel.setLocation(location);
        }
        if (!Objects.isNull(hotelTel)) {
            hotel.setHotelTel(hotelTel);
        }

        return repository.save(hotel);
    }

    public Hotel update(Hotel hotel) throws BaseException {
        return repository.save(hotel);
    }

    public Optional<Hotel> findByToken(String token) throws BaseException {
        Optional<Hotel> hotel = repository.findByToken(token);
        return hotel;
    }

    public Optional<Hotel> findByEmail(String email) throws BaseException {
        Optional<Hotel> hotel = repository.findByToken(email);
        return hotel;
    }
}
