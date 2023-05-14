package com.example.lovelypet.business;

import com.example.lovelypet.entity.Clinic;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.ClinicException;
import com.example.lovelypet.mapper.ClinicMapper;
import com.example.lovelypet.model.ClinicRegisterRequest;
import com.example.lovelypet.model.ClinicRegisterResponse;
import com.example.lovelypet.model.LoginRequest;
import com.example.lovelypet.service.ClinicService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ClinicBusiness {

    private final ClinicService clinicService;
    private final ClinicMapper clinicMapper;

    public ClinicBusiness(ClinicService clinicService, ClinicMapper clinicMapper) {
        this.clinicService = clinicService;
        this.clinicMapper = clinicMapper;
    }

    public Clinic register(ClinicRegisterRequest cq) throws BaseException {
        Clinic clinic = clinicService.create(cq.getUserNameC(), cq.getPassWordC(), cq.getNameC(), cq.getTelC(), cq.getEmailC(), cq.getAddressC(), cq.getPhotoC(), cq.getLicenseC(), cq.getDetailsC());
        // TODO: mapper
        return clinic;
    }

    public ClinicRegisterResponse login(LoginRequest reqc) throws BaseException {

        String op = reqc.getPassWord();
        String ou = reqc.getUserName();
        if (Objects.isNull(op)) {
            throw ClinicException.createPassWordNull();
        }
        if (Objects.isNull(ou)) {
            throw ClinicException.createUserNameNull();
        }

        Optional<Clinic> opt = clinicService.findLog(reqc.getUserName());
        if (opt.isEmpty()) {
            throw ClinicException.loginFailUserNameNotFound();
        }
        Clinic clinic = opt.get();
        if (!clinicService.matchPassword(reqc.getPassWord(), clinic.getPassWordC())) {
            throw ClinicException.loginFailPasswordIncorrect();

        } else {
            ClinicRegisterResponse resC = clinicMapper.toClinicRegisterResponse(clinic);
            return resC;
        }
    }
}
