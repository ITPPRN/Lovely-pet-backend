package com.example.lovelypet.business;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.HotelException;
import com.example.lovelypet.mapper.HotelMapper;
import com.example.lovelypet.model.*;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.service.PhotoHotelService;
import com.example.lovelypet.service.TokenService;
import com.example.lovelypet.util.SecurityUtil;
import io.netty.util.internal.StringUtil;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class HotelBusiness {

    private final HotelService hotelService;

    private final TokenService tokenService;

    private final HotelMapper hotelMapper;

    private final EmailBusiness emailBusiness;

    private final PhotoHotelService photoHotelService;

    public HotelBusiness(HotelService hotelService, TokenService tokenService, HotelMapper hotelMapper, EmailBusiness emailBusiness, PhotoHotelService photoHotelService) {
        this.hotelService = hotelService;
        this.tokenService = tokenService;
        this.hotelMapper = hotelMapper;
        this.emailBusiness = emailBusiness;
        this.photoHotelService = photoHotelService;
    }

    public HotelRegisterResponse register(HotelRegisterRequest request) throws BaseException {
        String token = SecurityUtil.generateToken();
        Hotel hotel = hotelService.create(
                request.getHotelUsername(),
                request.getPassword(),
                request.getHotelName(),
                request.getHotelTel(),
                request.getEmail(),
                request.getLocation(),
                token,
                nextXMinute(30)
        );
        HotelRegisterResponse hRes = hotelMapper.toHotelRegisterResponse(hotel);
        sendEmail(hotel);
        return hRes;
   }

    private Date nextXMinute(int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,minute);
        return calendar.getTime();
    }

    private void sendEmail(Hotel hotel) {


        // TODO: generate token
        String token = hotel.getToken();

        try {
            emailBusiness.sendActivateHotelEmail(hotel.getEmail(), hotel.getHotelName(), token);
        } catch (BaseException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if(opt.isEmpty()){
            throw HotelException.unauthorized();
        }

        String userId = opt.get();

        Optional<Hotel> optUser = hotelService.findById(Integer.parseInt(userId));
        if(opt.isEmpty()){
            throw HotelException.notFound();
        }

        Hotel hotel = optUser.get();
        return tokenService.tokenizeHotel(hotel);
    }

    public ActivateResponse activate(ActivateRequest request) throws BaseException {
        String token = request.getToken();
        if (StringUtil.isNullOrEmpty(token)){
            throw HotelException.activateNoToken();
        }
        Optional<Hotel> opt = hotelService.findByToken(token);
        if (opt.isEmpty()){
            throw HotelException.activateFail();
        }

        Hotel hotel = opt.get();
        if (hotel.isActivated()){
            throw HotelException.activateAlready();
        }

        Date now = new Date();
        Date expireDate = hotel.getTokenExpire();
        if (now.after(expireDate)){
            //TODO: re-mail
            throw HotelException.activateTokenExpire();
        }
        hotel.setActivated(true);
        hotelService.update(hotel);

        ActivateResponse response = new ActivateResponse();
        response.setSuccess(true);
        return response;
    }

    public void resendActivationEmail(ResendActivateEmailRequest request) throws BaseException{
        String email = request.getEmail();
        if (StringUtil.isNullOrEmpty(email)){
            throw HotelException.resendActivationEmailNoEmail();
        }

        Optional<Hotel> opt = hotelService.findByEmail(email);
        if(opt.isEmpty()){
            throw HotelException.resendActivationEmailNoEmailEmailNotFound();
        }

        Hotel hotel = opt.get();

        if (hotel.isActivated()){
            throw HotelException.activateAlready();
        }
        hotel.setToken(SecurityUtil.generateToken());
        hotel.setTokenExpire(nextXMinute(30));
        hotelService.update(hotel);
        sendEmail(hotel);
    }

    public String login(LoginRequest loginRequest) throws BaseException {
        String op = loginRequest.getPassWord();
        String ou = loginRequest.getUserName();
        if (Objects.isNull(op)) {
            throw HotelException.createPassWordNull();
        }
        if (Objects.isNull(ou)) {
            throw HotelException.createUserNameNull();
        }

        Optional<Hotel> opt = hotelService.findLog(loginRequest.getUserName());
        if (opt.isEmpty()) {
            throw HotelException.loginFailUserNameNotFound();
        }
        Hotel hotel = opt.get();
        if (!hotel.isActivated()){
            throw HotelException.loginFailUserUnactivated();
        }
        if (!hotelService.matchPassword(loginRequest.getPassWord(), hotel.getPassword())) {
            throw HotelException.loginFailPasswordIncorrect();

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
            throw HotelException.createPassWordNull();
        }
        Optional<Hotel> opt = hotelService.findById(updateRequest.getId());
        if (opt.isEmpty()) {
            throw HotelException.loginFailUserNameNotFound();
        }
        Hotel hotel = opt.get();
        if (!hotelService.matchPassword(updateRequest.getOldPassword(), hotel.getPassword())) {
            throw HotelException.passwordIncorrect();

        }
        Hotel updatedHotel = hotelService.resetPassword(
                updateRequest.getId(),
                updateRequest.getNewPassword()
        );
        return "";
    }



//////////////////////////////////// ยังไม่เสร็จ ////////////////////
    public String uploadImage ()throws BaseException{
//        Hotel hotel;
//        photoHotelService.create("",hotel);
        return "";
    }

    public String getImage ()throws BaseException{
//        Hotel hotel;
//        photoHotelService.create("",hotel);
        return "";
    }
///////////////////////////////////////////////////////////////

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
