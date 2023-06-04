package com.example.lovelypet.service;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.HotelException;
import com.example.lovelypet.exception.UserException;
import com.example.lovelypet.repository.HotelRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                        String location
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
            return repository.save(entity);
        }
    }

    public Hotel updateName(int idU, String name) throws UserException {
        Optional<Hotel> opt = repository.findById(idU);
        if(opt.isEmpty()){
            throw  UserException.notFound();
        }
        Hotel hotel = opt.get();
        hotel.setHotelName(name);

        return repository.save(hotel);
    }

    public  void deleteByIdU(String idU){
        repository.deleteById(idU);
    }

    public Optional<Hotel> findLog(String userName) throws BaseException {

        Optional<Hotel> hotel = repository.findByHotelUsername(userName);
        return hotel;
    }

    public boolean matchPassword(String requestPass, String dataPass) {
        return passwordEncoder.matches(requestPass,dataPass);
    }
}
