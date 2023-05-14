package com.example.lovelypet.service;

import com.example.lovelypet.entity.Clinic;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.ClinicException;
import com.example.lovelypet.repository.ClinicRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ClinicService {

    private final ClinicRepository repository;

    public ClinicService(ClinicRepository repository) {
        this.repository = repository;
    }

    public Clinic create(String userName, String passWord, String name, String tel, String email, String address, String photo, String license, String details) throws BaseException {

        //validate
        if (Objects.isNull(userName)) {
            throw ClinicException.createUserNameNull();
        }
        if (Objects.isNull(passWord)) {
            throw ClinicException.createPassWordNull();
        }
        if (Objects.isNull(name)) {
            throw ClinicException.createNameNull();
        }
        if (Objects.isNull(tel)) {
            throw ClinicException.createTelNull();
        }
        if (Objects.isNull(email)) {
            throw ClinicException.createEmailNull();
        }
        if (Objects.isNull(address)) {
            throw ClinicException.createAddressNull();
        }
        if (Objects.isNull(photo)) {
            throw ClinicException.createPhotoNull();
        }
        if (Objects.isNull(license)) {
            throw ClinicException.createLicenseNull();
        }

        //verify
        if (repository.existsByEmailC(email)) {
            throw ClinicException.createEmailDuplicated();
        }
        if (repository.existsByUserNameC(userName)) {
            throw ClinicException.createUserNameDuplicated();
        }
        if (repository.existsByLicenseC(license)) {
            throw ClinicException.createLicenseDuplicated();
        }

        //save
        else {
            Clinic entity = new Clinic();
            entity.setUserNameC(userName);
            entity.setPassWordC(passWord);
            entity.setNameC(name);
            entity.setTelC(tel);
            entity.setEmailC(email);
            entity.setAddressC(address);
            entity.setStatusC("CLOSE");
            entity.setPhotoC(photo);
            entity.setLicenseC(license);
            entity.setDetailsC(details);
            entity.setRatingC(0);
            entity.setStatusVerify("wait");
            return repository.save(entity);
        }
    }

    public Optional<Clinic> findLog(String userName) throws BaseException {

        Optional<Clinic> clinic = repository.findByUserNameC(userName);
        return clinic;
    }

    public boolean matchPassword(String reqPass, String dataPass) {
        return reqPass.matches(dataPass);
    }
}
