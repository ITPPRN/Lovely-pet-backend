package com.example.lovelypet.business;

import com.example.lovelypet.entity.AdditionalServices;
import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.exception.AdditionalServiceException;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.HotelException;
import com.example.lovelypet.exception.UserException;
import com.example.lovelypet.mapper.AdditionalServiceMapper;
import com.example.lovelypet.model.AdditionalServiceRequest;
import com.example.lovelypet.model.AdditionalServiceResponse;
import com.example.lovelypet.service.AdditionalServiceService;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdditionalServiceBusiness {

    private final AdditionalServiceService additionalServiceService;

    private final HotelService hotelService;

    private final AdditionalServiceMapper additionalServiceMapper;

    public AdditionalServiceBusiness(AdditionalServiceService additionalServiceService, HotelService hotelService, AdditionalServiceMapper additionalServiceMapper) {
        this.additionalServiceService = additionalServiceService;
        this.hotelService = hotelService;
        this.additionalServiceMapper = additionalServiceMapper;
    }

    public AdditionalServiceResponse addService(AdditionalServiceRequest request) throws BaseException {
        //verify
        Hotel hotel = getCurrentId();

        if (Objects.isNull(request.getName())) {
            throw AdditionalServiceException.createNameNull();
        }

        if (request.getPrice() == 0) {
            throw AdditionalServiceException.createPriceNull();
        }

        AdditionalServices response = additionalServiceService.addService(hotel, request.getName(), request.getPrice());
        return additionalServiceMapper.toAdditionalServiceResponse(response);
    }

    public String updateService(AdditionalServiceRequest request) throws BaseException {
        //verify
        if (request.getId() == 0) {
            throw AdditionalServiceException.updateIdNull();
        }

        if ((Objects.isNull(request.getName())) && (request.getPrice() == 0.0)) {
            throw AdditionalServiceException.updateRequestNull();
        }

        additionalServiceService.update(request);
        return "Update Service No." + request.getId() + "Successfully";

    }

    //find
    public List<AdditionalServiceResponse> listAllHotel() throws BaseException {
        List<AdditionalServices> additions = additionalServiceService.findByHotelId(getCurrentId());


        List<AdditionalServiceResponse> responses = new ArrayList<>();
        for (AdditionalServices addition : additions) {
            AdditionalServiceResponse data = additionalServiceMapper.toAdditionalServiceResponse(addition);
            responses.add(data);
        }
        return responses;
    }

    public List<AdditionalServiceResponse> listAllHotelForUser(AdditionalServiceRequest id) throws BaseException {
        List<AdditionalServices> additions = additionalServiceService.findByHotelId(getCurrentIdForUser(id.getId()));
        List<AdditionalServiceResponse> responses = new ArrayList<>();
        for (AdditionalServices addition : additions) {
            AdditionalServiceResponse data = additionalServiceMapper.toAdditionalServiceResponse(addition);
            responses.add(data);
        }
        return responses;
    }

    @Transactional
    //delete
    public String deleteService(int id) throws BaseException {
        if (id == 0) {
            throw AdditionalServiceException.deleteIdNull();
        }
        return additionalServiceService.deleteService(id);
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

    private Hotel getCurrentIdForUser(int id) throws BaseException {
        Optional<Hotel> optHotel = hotelService.findById(id);
        if (optHotel.isEmpty()) {
            throw HotelException.notFound();
        }
        return optHotel.get();
    }
}




