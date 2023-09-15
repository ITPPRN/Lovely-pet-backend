package com.example.lovelypet.api;

import com.example.lovelypet.business.ServiceHistoryBusiness;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.BookingListResponse;
import com.example.lovelypet.model.HistoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/get-service-history")
    public ResponseEntity<BookingListResponse> getService(@RequestBody HistoryRequest id) throws BaseException {
        BookingListResponse response = serviceHistoryBusiness.getServiceHistory(id);
        return ResponseEntity.ok(response);
    }

    ////////////////////////////////
    /////////////////////////////////////
}
