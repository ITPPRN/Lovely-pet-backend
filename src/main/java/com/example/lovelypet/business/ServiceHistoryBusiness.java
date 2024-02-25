package com.example.lovelypet.business;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.ServiceHistory;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.HotelException;
import com.example.lovelypet.exception.ServiceHistoryException;
import com.example.lovelypet.exception.UserException;
import com.example.lovelypet.model.AdditionalServiceResponse;
import com.example.lovelypet.model.BookingListResponse;
import com.example.lovelypet.model.PetProfileResponse;
import com.example.lovelypet.model.UseProfile;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.service.ServiceHistoryService;
import com.example.lovelypet.service.UserService;
import com.example.lovelypet.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ServiceHistoryBusiness {
    private final HotelService hotelService;

    private final UserService userService;

    private final ServiceHistoryService serviceHistoryService;

    public SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

    public ServiceHistoryBusiness(HotelService hotelService, UserService userService, ServiceHistoryService serviceHistoryService) {
        this.hotelService = hotelService;
        this.userService = userService;
        this.serviceHistoryService = serviceHistoryService;
    }

    public List<BookingListResponse> listServiceHistory() throws BaseException {

        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        Optional<String> opt2 = SecurityUtil.getCurrentUserRole();
        if (opt2.isEmpty()) {
            throw UserException.unauthorized();
        }
        String id = opt.get();
        String role = opt2.get();
        if (role.equals("HOTEL")) {
            Optional<Hotel> optHotel = hotelService.findById(Integer.parseInt(id));
            if (optHotel.isEmpty()) {
                throw HotelException.notFound();
            }
            Hotel hotel = optHotel.get();
            List<ServiceHistory> bookingList = serviceHistoryService.findByHotelId(hotel);
            if (bookingList.isEmpty()) {
                throw ServiceHistoryException.notFound();
            }
            List<BookingListResponse> response = new ArrayList<>();
            for (ServiceHistory booking : bookingList) {

                PetProfileResponse petProfile = new PetProfileResponse();
                petProfile.setPetTypeId(booking.getBookingId().getPetId().getPetTypeId().getId());
                petProfile.setPetTyName(booking.getBookingId().getPetId().getPetTypeId().getName());
                petProfile.setId(booking.getBookingId().getPetId().getId());
                petProfile.setPetName(booking.getBookingId().getPetId().getPetName());
                petProfile.setBirthday(formatDate.format(booking.getBookingId().getPetId().getBirthday()));
                petProfile.setUserOwner(booking.getBookingId().getPetId().getUserId().getId());
                petProfile.setPhotoPath(booking.getBookingId().getPetId().getPetPhoto());

                UseProfile use = new UseProfile();
                use.setId(booking.getUserId().getId());
                use.setName(booking.getUserId().getName());
                use.setEmail(booking.getUserId().getEmail());
                use.setPhoneNumber(booking.getUserId().getPhoneNumber());

                AdditionalServiceResponse addSer = null;
                if (Objects.nonNull(booking.getBookingId().getAdditionalServiceId())) {
                    addSer = new AdditionalServiceResponse();
                    addSer.setId(booking.getBookingId().getAdditionalServiceId().getId());
                    addSer.setName(booking.getBookingId().getAdditionalServiceId().getName());
                    addSer.setPrice(booking.getBookingId().getAdditionalServiceId().getPrice());
                }


                BookingListResponse data = new BookingListResponse();
                data.setId(booking.getBookingId().getId());
                data.setRoomNumber(booking.getBookingId().getRoomId().getRoomNumber());
                data.setDate(booking.getBookingId().getDate());
                data.setBookingStartDate(formatDate.format(booking.getBookingId().getBookingStartDate()));
                data.setBookingEndDate(formatDate.format(booking.getBookingId().getBookingEndDate()));
                data.setPaymentMethod(booking.getBookingId().getPaymentMethod());
                data.setPayment(booking.getBookingId().getPayment());
                data.setState(booking.getBookingId().getState());
                data.setPet(petProfile);
                data.setHotelId(booking.getBookingId().getHotelId().getId());
                data.setNameHotel(booking.getBookingId().getHotelId().getHotelName());
                data.setLatitude(booking.getBookingId().getHotelId().getLatitude());
                data.setLongitude(booking.getBookingId().getHotelId().getLongitude());
                data.setTelHotel(booking.getBookingId().getHotelId().getHotelTel());
                data.setEmail(booking.getBookingId().getHotelId().getEmail());
                data.setFeedback(booking.getBookingId().isFeedback());
                data.setUser(use);
                data.setAddSer(addSer);
                response.add(data);
            }
            return response;
        }

        if (role.equals("USER")) {
            Optional<User> optUser = userService.findById(Integer.parseInt(id));
            if (optUser.isEmpty()) {
                throw UserException.notFound();
            }
            User user = optUser.get();
            List<ServiceHistory> bookingList2 = serviceHistoryService.findByUserId(user);
            if (bookingList2.isEmpty()) {
                throw ServiceHistoryException.notFound();
            }

            List<BookingListResponse> response2 = new ArrayList<>();
            for (ServiceHistory booking : bookingList2) {

                PetProfileResponse petProfile = new PetProfileResponse();
                petProfile.setPetTypeId(booking.getBookingId().getPetId().getPetTypeId().getId());
                petProfile.setPetTyName(booking.getBookingId().getPetId().getPetTypeId().getName());
                petProfile.setId(booking.getBookingId().getPetId().getId());
                petProfile.setPetName(booking.getBookingId().getPetId().getPetName());
                petProfile.setBirthday(formatDate.format(booking.getBookingId().getPetId().getBirthday()));
                petProfile.setUserOwner(booking.getBookingId().getPetId().getUserId().getId());
                petProfile.setPhotoPath(booking.getBookingId().getPetId().getPetPhoto());

                UseProfile use = new UseProfile();
                use.setId(booking.getUserId().getId());
                use.setName(booking.getUserId().getName());
                use.setEmail(booking.getUserId().getEmail());
                use.setPhoneNumber(booking.getUserId().getPhoneNumber());

                AdditionalServiceResponse addSer = null;
                if (Objects.nonNull(booking.getBookingId().getAdditionalServiceId())) {
                    addSer = new AdditionalServiceResponse();
                    addSer.setId(booking.getBookingId().getAdditionalServiceId().getId());
                    addSer.setName(booking.getBookingId().getAdditionalServiceId().getName());
                    addSer.setPrice(booking.getBookingId().getAdditionalServiceId().getPrice());
                }


                BookingListResponse data = new BookingListResponse();
                data.setId(booking.getBookingId().getId());
                data.setRoomNumber(booking.getBookingId().getRoomId().getRoomNumber());
                data.setDate(booking.getBookingId().getDate());
                data.setBookingStartDate(formatDate.format(booking.getBookingId().getBookingStartDate()));
                data.setBookingEndDate(formatDate.format(booking.getBookingId().getBookingEndDate()));
                data.setPaymentMethod(booking.getBookingId().getPaymentMethod());
                data.setPayment(booking.getBookingId().getPayment());
                data.setState(booking.getBookingId().getState());
                data.setPet(petProfile);
                data.setHotelId(booking.getBookingId().getHotelId().getId());
                data.setNameHotel(booking.getBookingId().getHotelId().getHotelName());
                data.setLatitude(booking.getBookingId().getHotelId().getLatitude());
                data.setLongitude(booking.getBookingId().getHotelId().getLongitude());
                data.setTelHotel(booking.getBookingId().getHotelId().getHotelTel());
                data.setEmail(booking.getBookingId().getHotelId().getEmail());
                data.setFeedback(booking.getBookingId().isFeedback());
                data.setUser(use);
                data.setAddSer(addSer);
                response2.add(data);
            }
            return response2;
        } else {
            throw ServiceHistoryException.accessDenied();
        }
    }


    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
}





