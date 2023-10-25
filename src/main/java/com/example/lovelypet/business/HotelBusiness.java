package com.example.lovelypet.business;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.PhotoHotel;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.HotelException;
import com.example.lovelypet.exception.UserException;
import com.example.lovelypet.mapper.HotelMapper;
import com.example.lovelypet.model.*;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.service.TokenService;
import com.example.lovelypet.util.SecurityUtil;
import io.netty.util.internal.StringUtil;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelBusiness {

    private final HotelService hotelService;

    private final TokenService tokenService;

    private final HotelMapper hotelMapper;

    private final EmailBusiness emailBusiness;

    private final PhotoHotelBusiness photoHotelBusiness;

    public HotelBusiness(HotelService hotelService, TokenService tokenService, HotelMapper hotelMapper, EmailBusiness emailBusiness, PhotoHotelBusiness photoHotelBusiness) {
        this.hotelService = hotelService;
        this.tokenService = tokenService;
        this.hotelMapper = hotelMapper;
        this.emailBusiness = emailBusiness;
        this.photoHotelBusiness = photoHotelBusiness;
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
                nextXMinute(30),
                request.getAdditionalNotes(),
                request.getLatitude(),
                request.getLongitude()

        );
        HotelRegisterResponse hRes = hotelMapper.toHotelRegisterResponse(hotel);
        sendEmail(hotel);
        return hRes;
    }

    private Date nextXMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
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
        Hotel hotel = getCurrentId();
        return tokenService.tokenizeHotel(hotel);
    }

    public ActivateResponse activate(String request) throws BaseException {
        if (StringUtil.isNullOrEmpty(request)) {
            throw HotelException.activateNoToken();
        }
        Optional<Hotel> opt = hotelService.findByToken(request);
        if (opt.isEmpty()) {
            throw HotelException.activateFail();
        }

        Hotel hotel = opt.get();
        if (hotel.isActivated()) {
            throw HotelException.activateAlready();
        }

        Date now = new Date();
        Date expireDate = hotel.getTokenExpire();
        if (now.after(expireDate)) {
            //TODO: re-mail
            throw HotelException.activateTokenExpire();
        }
        hotel.setActivated(true);
        hotelService.update(hotel);

        ActivateResponse response = new ActivateResponse();
        response.setSuccess(true);
        return response;
    }

    public void resendActivationEmail(ResendActivateEmailRequest request) throws BaseException {
        String email = request.getToken();
        if (StringUtil.isNullOrEmpty(email)) {
            throw HotelException.resendActivationEmailNoEmail();
        }

        Optional<Hotel> opt = hotelService.findByEmail(email);
        if (opt.isEmpty()) {
            throw HotelException.resendActivationEmailNoEmailEmailNotFound();
        }

        Hotel hotel = opt.get();

        if (hotel.isActivated()) {
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
        if (!hotel.isActivated()) {
            throw HotelException.loginFailUserUnactivated();
        }
        if (!hotelService.matchPassword(loginRequest.getPassWord(), hotel.getPassword())) {
            throw HotelException.loginFailPasswordIncorrect();

        }
        if (Objects.nonNull(hotel.getDateDeleteAccount())) {
            hotel.setDateDeleteAccount(null);
            Hotel response = hotelService.update(hotel);
            return tokenService.tokenizeHotel(response);
        } else {
            return tokenService.tokenizeHotel(hotel);
        }
    }

    public HotelResponse updateNormalData(HotelUpdateRequest updateRequest) throws BaseException {

        Hotel hotel = getCurrentId();
        if (Objects.nonNull(updateRequest.getHotelName())) {
            hotel.setHotelName(updateRequest.getHotelName());
        }
        if (Objects.nonNull(updateRequest.getHotelTel())) {
            hotel.setHotelTel(updateRequest.getHotelTel());
        }
        if (Objects.nonNull(updateRequest.getLocation())) {
            hotel.setLocation(updateRequest.getLocation());
        }
        if (Objects.nonNull(updateRequest.getAdditionalNotes())) {
            hotel.setAdditionalNotes(updateRequest.getAdditionalNotes());
        }
        if (Objects.nonNull(updateRequest.getOpenState())) {
            hotel.setOpenState(updateRequest.getOpenState());
        }
        Hotel update = hotelService.update(hotel);
        return hotelMapper.toHotelResponse(update);
    }

    public HotelResponse updateVerifyState(HotelVerifyRequest updateRequest) throws BaseException {

        Optional<Hotel> opt = hotelService.findById(updateRequest.getId());
        if (opt.isEmpty()) {
            throw HotelException.notFound();
        }
        Hotel hotel = opt.get();
        hotel.setVerify(updateRequest.getStatus());
        Hotel update = hotelService.update(hotel);
        return hotelMapper.toHotelResponse(update);
    }

    public String resetPassword(HotelUpdateRequest updateRequest) throws BaseException {

        if (Objects.isNull(updateRequest.getNewPassword())) {
            throw HotelException.createPassWordNull();
        }
        Hotel hotel = getCurrentId();
        if (!hotelService.matchPassword(updateRequest.getOldPassword(), hotel.getPassword())) {
            throw HotelException.passwordIncorrect();

        }
        hotel.setPassword(updateRequest.getNewPassword());
        hotelService.update(hotel);
        return "Successful password reset";
    }

    //get id on token
    private Hotel getCurrentId() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }
        String hotelId = opt.get();
        Optional<Hotel> optHotel = hotelService.findById(Integer.parseInt(hotelId));
        if (optHotel.isEmpty()) {
            throw HotelException.notFound();
        }
        return optHotel.get();
    }

    //get hotel
    public List<HotelResponse> listHotelByState2() throws BaseException {
        List<Hotel> hotels = hotelService.findByStateVerifyAndStateOpen("approve", "OPEN");
        if (hotels.isEmpty()) {
            throw HotelException.notFound();
        }
        List<HotelResponse> response = new ArrayList<>();
        for (Hotel hotel : hotels) {

            HotelResponse data = hotelMapper.toHotelResponse(hotel);
            response.add(data);
        }
        return response;
    }

    public List<HotelResponse> getHotelByVerify() throws BaseException {
        List<Hotel> hotels = hotelService.findByVerify("waite");
        if (hotels.isEmpty()) {
            throw HotelException.notFound();
        }
        List<HotelResponse> response = new ArrayList<>();
        for (Hotel hotel : hotels) {

            HotelResponse data = hotelMapper.toHotelResponse(hotel);
            response.add(data);
        }
        return response;
    }

    //delete account request
    public String deleteAccountRequest() throws BaseException {
        Hotel hotel = getCurrentId();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        calendar.add(Calendar.DAY_OF_MONTH, 90);
        Date dateDelete = calendar.getTime();
        hotel.setDateDeleteAccount(dateDelete);
        Hotel response = hotelService.update(hotel);
        return response.getHotelName() + "account will be deleted when" + response.getDateDeleteAccount() + "if not logged in.";
    }

    //delete account
    public void deleteAccount(int id) throws BaseException {
        Optional<Hotel> opt = hotelService.findById(id);
        if (opt.isEmpty()) {
            throw HotelException.notFound();
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        Date date = calendar.getTime();
        Hotel hotel = opt.get();
        if (!hotel.getDateDeleteAccount().equals(date)) {
            return;
        }
        //delete image
        List<PhotoHotel> images = photoHotelBusiness.findByHotelId(id); // ดึงข้อมูลรูปภาพทั้งหมดจากฐานข้อมูล
        for (PhotoHotel image : images) {
            PhotoHotelRequest data = new PhotoHotelRequest();
            data.setName(image.getPhotoHotelFile());
            photoHotelBusiness.deleteImage(data);
        }

        //TODO: delete image room

        hotelService.deleteByIdU(hotel.getId());
    }

    //////////////////////////////////// ยังไม่เสร็จ ////////////////////

    /////////////////////////////////////////////////////////////

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
