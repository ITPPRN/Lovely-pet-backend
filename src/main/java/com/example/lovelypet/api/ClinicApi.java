package com.example.lovelypet.api;

import com.example.lovelypet.business.ClinicBusiness;
import com.example.lovelypet.entity.Clinic;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.ClinicRegisterRequest;
import com.example.lovelypet.model.ClinicRegisterResponse;
import com.example.lovelypet.model.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clinic")
public class ClinicApi {

    private final ClinicBusiness clinicBusiness;

    public ClinicApi(ClinicBusiness clinicBusiness) {
        this.clinicBusiness = clinicBusiness;
    }

    @PostMapping("/registerC")
    public ResponseEntity<Clinic> registerC(@RequestBody ClinicRegisterRequest clrq) throws BaseException {

        Clinic response = clinicBusiness.register(clrq);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/loginC")
    public ResponseEntity<ClinicRegisterResponse> login(@RequestBody LoginRequest reqc) throws BaseException {
        ClinicRegisterResponse response = clinicBusiness.login(reqc);
        return ResponseEntity.ok(response);
    }

}
