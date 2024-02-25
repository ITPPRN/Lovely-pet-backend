package com.example.lovelypet.api;

import com.example.lovelypet.business.AdditionalServiceBusiness;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.AdditionalServiceRequest;
import com.example.lovelypet.model.AdditionalServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<AdditionalServiceResponse> addService(@RequestBody AdditionalServiceRequest request) throws BaseException {
        AdditionalServiceResponse response = additionalServiceBusiness.addService(request);
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
    public ResponseEntity<List<AdditionalServiceResponse>> listService() throws BaseException {
        List<AdditionalServiceResponse> response = additionalServiceBusiness.listAllHotel();
        return ResponseEntity.ok(response);
    }//git add

    // list all service in hotel for user
    @PostMapping("/list-service-for-user")
    public ResponseEntity<List<AdditionalServiceResponse>> listServiceForUser(@RequestBody AdditionalServiceRequest id) throws BaseException {
        List<AdditionalServiceResponse> response = additionalServiceBusiness.listAllHotelForUser(id);
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
