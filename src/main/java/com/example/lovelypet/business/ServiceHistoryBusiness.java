package com.example.lovelypet.business;

import com.example.lovelypet.entity.Booking;
import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.ServiceHistory;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.*;
import com.example.lovelypet.model.BookingListResponse;
import com.example.lovelypet.model.HistoryRequest;
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
                BookingListResponse data = new BookingListResponse();
                data.setId(booking.getBookingId().getId());
                data.setRoomNumber(booking.getBookingId().getRoomId().getRoomNumber());
                data.setDate(booking.getBookingId().getDate());
                data.setBookingStartDate(formatDate.format(booking.getBookingId().getBookingStartDate()));
                data.setBookingEndDate(formatDate.format(booking.getBookingId().getBookingEndDate()));
                data.setPaymentMethod(booking.getBookingId().getPaymentMethod());
                data.setPayment(booking.getBookingId().getPayment());
                data.setState(booking.getBookingId().getState());
                data.setPetId(booking.getBookingId().getPetId().getId());
                data.setHotelId(booking.getBookingId().getHotelId().getId());
                data.setUserId(booking.getBookingId().getUserId().getId());
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
                BookingListResponse data = new BookingListResponse();
                data.setId(booking.getBookingId().getId());
                data.setRoomNumber(booking.getBookingId().getRoomId().getRoomNumber());
                data.setDate(booking.getBookingId().getDate());
                data.setBookingStartDate(formatDate.format(booking.getBookingId().getBookingStartDate()));
                data.setBookingEndDate(formatDate.format(booking.getBookingId().getBookingEndDate()));
                data.setPaymentMethod(booking.getBookingId().getPaymentMethod());
                data.setPayment(booking.getBookingId().getPayment());
                data.setState(booking.getBookingId().getState());
                data.setPetId(booking.getBookingId().getPetId().getId());
                data.setHotelId(booking.getBookingId().getHotelId().getId());
                data.setUserId(booking.getBookingId().getUserId().getId());
                response2.add(data);
            }
            return response2;
        } else {
            throw ServiceHistoryException.accessDenied();
        }
    }

    public BookingListResponse getServiceHistory(HistoryRequest id) throws BaseException {
        if (Objects.isNull(id)) {
            throw BookingException.idBookingIsNull();
        }
        Optional<ServiceHistory> opt = serviceHistoryService.findById(id.getId());
        if (opt.isEmpty()) {
            throw BookingException.notFound();
        }
        Booking serviceHistory = opt.get().getBookingId();
        BookingListResponse res = new BookingListResponse();
        return res;
    }

    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
}





