package com.example.lovelypet.business;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.UserException;
import com.example.lovelypet.mapper.HotelMapper;
import com.example.lovelypet.model.*;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.service.TokenService;
import com.example.lovelypet.service.UserService;
import com.example.lovelypet.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class HotelBusiness {

    private final HotelService hotelService;

    private final TokenService tokenService;

    private final HotelMapper hotelMapper;

    public HotelBusiness(HotelService hotelService, TokenService tokenService, HotelMapper hotelMapper) {
        this.hotelService = hotelService;
        this.tokenService = tokenService;
        this.hotelMapper = hotelMapper;
    }

    public HotelRegisterResponse register(HotelRegisterRequest request) throws BaseException {
        Hotel hotel = hotelService.create(
                request.getHotelUsername(),
                request.getPassword(),
                request.getHotelName(),
                request.getHotelTel(),
                request.getEmail(),
                request.getLocation());
        HotelRegisterResponse hRes = hotelMapper.toHotelRegisterResponse(hotel);
        return hRes;
   }

    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if(opt.isEmpty()){
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<Hotel> optUser = hotelService.findById(Integer.parseInt(userId));
        if(opt.isEmpty()){
            throw UserException.notFound();
        }

        Hotel hotel = optUser.get();
        return tokenService.tokenizeHotel(hotel);
    }

    public String login(LoginRequest loginRequest) throws BaseException {
        String op = loginRequest.getPassWord();
        String ou = loginRequest.getUserName();
        if (Objects.isNull(op)) {
            throw UserException.createPasswordNull();
        }
        if (Objects.isNull(ou)) {
            throw UserException.createUserNameNull();
        }

        Optional<Hotel> opt = hotelService.findLog(loginRequest.getUserName());
        if (opt.isEmpty()) {
            throw UserException.loginFailUserNameNotFound();
        }
        Hotel hotel = opt.get();
        if (!hotelService.matchPassword(loginRequest.getPassWord(), hotel.getPassword())) {
            throw UserException.loginFailPasswordIncorrect();

        } else {
            return tokenService.tokenizeHotel(hotel);
        }
    }

    public String updateNormalData(HotelUpdateRequest updateRequest) throws BaseException {

        Hotel updatedUser = hotelService.updateNormalData(
                updateRequest.getId(),
                updateRequest.getHotelName(),
                updateRequest.getLocation(),
                updateRequest.getHotelTel()
        );
        return "";
    }

    public String resetPassword(HotelUpdateRequest updateRequest) throws BaseException {

        String newPassword = updateRequest.getNewPassword();

        if (Objects.isNull(newPassword)) {
            throw UserException.createPasswordNull();
        }
        Optional<Hotel> opt = hotelService.findById(updateRequest.getId());
        if (opt.isEmpty()) {
            throw UserException.loginFailUserNameNotFound();
        }
        Hotel hotel = opt.get();
        if (!hotelService.matchPassword(updateRequest.getOldPassword(), hotel.getPassword())) {
            throw UserException.passwordIncorrect();

        }
        Hotel updatedHotel = hotelService.resetPassword(
                updateRequest.getId(),
                updateRequest.getNewPassword()
        );
        return "";
    }


}


/* example mapper
public UserRegisterResponse login(LoginRequest loginRequest) throws BaseException {
        String op = loginRequest.getPassWord();
        String ou = loginRequest.getUserName();
        if (Objects.isNull(op)) {
            throw UserException.createPasswordNull();
        }
        if (Objects.isNull(ou)) {
            throw UserException.createUserNameNull();
        }

        Optional<User> opt = userService.findLog(loginRequest.getUserName());
        if (opt.isEmpty()) {
            throw UserException.loginFailUserNameNotFound();
        }
        User user = opt.get();
        if (!userService.matchPassword(loginRequest.getPassWord(), user.getPassWord())) {
            throw UserException.loginFailPasswordIncorrect();

        } else {
            UserRegisterResponse ures = userMapper.toUserRegisterResponse(user);
            return ures;
        }
    }*/
