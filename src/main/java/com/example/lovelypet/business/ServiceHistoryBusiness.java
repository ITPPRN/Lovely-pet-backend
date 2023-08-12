package com.example.lovelypet.business;

import com.example.lovelypet.entity.Booking;
import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.ServiceHistory;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.*;
import com.example.lovelypet.mapper.BookingMapper;
import com.example.lovelypet.model.BookingListResponse;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.service.ServiceHistoryService;
import com.example.lovelypet.service.UserService;
import com.example.lovelypet.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ServiceHistoryBusiness {
    private final HotelService hotelService;

    private final UserService userService;

    private final BookingMapper bookingMapper;

    private final ServiceHistoryService serviceHistoryService;

    public ServiceHistoryBusiness(HotelService hotelService, UserService userService, BookingMapper bookingMapper, ServiceHistoryService serviceHistoryService) {
        this.hotelService = hotelService;
        this.userService = userService;
        this.bookingMapper = bookingMapper;
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
                BookingListResponse data = bookingMapper.toBookingListResponse(booking.getBookingId());
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
            List<ServiceHistory> bookingList = serviceHistoryService.findByUserId(user);
            if (bookingList.isEmpty()) {
                throw ServiceHistoryException.notFound();
            }

            List<BookingListResponse> response = new ArrayList<>();
            for (ServiceHistory booking : bookingList) {
                BookingListResponse data = bookingMapper.toBookingListResponse(booking.getBookingId());
                response.add(data);
            }
            return response;
        } else {
            throw ServiceHistoryException.accessDenied();
        }
    }

    public BookingListResponse getServiceHistory(int id) throws BaseException {
        if (id == 0) {
            throw BookingException.idBookingIsNull();
        }
        Optional<ServiceHistory> opt = serviceHistoryService.findById(id);
        if (opt.isEmpty()) {
            throw BookingException.notFound();
        }
        Booking serviceHistory = opt.get().getBookingId();
        return bookingMapper.toBookingListResponse(serviceHistory);
    }

    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////

}




