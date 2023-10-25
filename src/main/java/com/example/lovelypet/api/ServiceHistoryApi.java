package com.example.lovelypet.api;

import com.example.lovelypet.business.ServiceHistoryBusiness;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.BookingListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service-history")
public class ServiceHistoryApi {

    private final ServiceHistoryBusiness serviceHistoryBusiness;

    public ServiceHistoryApi(ServiceHistoryBusiness serviceHistoryBusiness) {
        this.serviceHistoryBusiness = serviceHistoryBusiness;
    }

    @PostMapping("/list-service-history")
    public ResponseEntity<List<BookingListResponse>> listService() throws BaseException {
        List<BookingListResponse> response = serviceHistoryBusiness.listServiceHistory();
        return ResponseEntity.ok(response);
    }


    ////////////////////////////////
    /////////////////////////////////////
}
