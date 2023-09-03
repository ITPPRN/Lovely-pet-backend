package com.example.lovelypet.api;

import com.example.lovelypet.business.AdditionalServiceBusiness;
import com.example.lovelypet.entity.AdditionalServices;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.AdditionalServiceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/additional-service")
public class AdditionalServiceApi {

    private final AdditionalServiceBusiness additionalServiceBusiness;

    public AdditionalServiceApi(@RequestBody AdditionalServiceBusiness additionalServiceBusiness) {
        this.additionalServiceBusiness = additionalServiceBusiness;
    }

    //create
    @PostMapping("/add-service")
    public ResponseEntity<AdditionalServices> addService(@RequestBody AdditionalServiceRequest request) throws BaseException {
        AdditionalServices response = additionalServiceBusiness.addService(request);
        return ResponseEntity.ok(response);
    }

    //update
    @PostMapping("/update-service")
    public ResponseEntity<String> updateService(@RequestBody AdditionalServiceRequest request) throws BaseException {
        String response = additionalServiceBusiness.updateService(request);
        return ResponseEntity.ok(response);
    }

    // list all service in hotel
    @PostMapping("/list-service")
    public ResponseEntity<List<AdditionalServices>> listService() throws BaseException {
        List<AdditionalServices> response = additionalServiceBusiness.listAllHotel();
        return ResponseEntity.ok(response);
    }//git add

    // list all service in hotel for user
    @PostMapping("/list-service-for-user")
    public ResponseEntity<List<AdditionalServices>> listServiceForUser(@RequestParam int id) throws BaseException {
        List<AdditionalServices> response = additionalServiceBusiness.listAllHotelForUser(id);
        return ResponseEntity.ok(response);
    }

    //delete
    @PostMapping("/delete-service")
    //ใส่มาแค่ id service
    public ResponseEntity<String> deleteService(@RequestBody AdditionalServiceRequest request) throws BaseException {
        String response = additionalServiceBusiness.deleteService(request.getId());
        return ResponseEntity.ok(response);
    }
}
