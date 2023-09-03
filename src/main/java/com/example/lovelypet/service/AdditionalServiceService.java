package com.example.lovelypet.service;

import com.example.lovelypet.entity.AdditionalServices;
import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.exception.AdditionalServiceException;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.AdditionalServiceRequest;
import com.example.lovelypet.repository.AdditionalServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdditionalServiceService {

    private final AdditionalServiceRepository repository;

    public AdditionalServiceService(AdditionalServiceRepository repository) {
        this.repository = repository;
    }

    public AdditionalServices addService(
            Hotel hotelId,
            String name,
            float price
    ) {

        AdditionalServices entity = new AdditionalServices();
        entity.setHotelId(hotelId);
        entity.setName(name);
        entity.setPrice(price);
        return repository.save(entity);
    }

    public AdditionalServices update(AdditionalServiceRequest request) throws BaseException {

        //validate
        AdditionalServices entity = findById(request.getId());

        if (Objects.nonNull(request.getName())) {
            entity.setName(request.getName());
        }

        if (request.getPrice() != 0.0) {
            entity.setPrice(request.getPrice());
        }

        return repository.save(entity);

    }

    //delete
    public String deleteService(int id) throws BaseException {
        findById(id);
        repository.deleteById(id);
        Optional<AdditionalServices> opt = repository.findById(id);
        if (opt.isPresent()) {
            throw AdditionalServiceException.deleteFail();
        }
        return "Delete service successfully";
    }


    //find
    public AdditionalServices findById(int id) throws BaseException {
        Optional<AdditionalServices> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw AdditionalServiceException.notFound();
        }
        return opt.get();
    }

    public List<AdditionalServices> findByHotelId(Hotel hotel) throws BaseException {
        List<AdditionalServices> response = repository.findByHotelId(hotel);
        if (response.isEmpty()) {
            throw AdditionalServiceException.notFound();
        }
        return response;
    }
}
