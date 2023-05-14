package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Clinic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClinicRepository extends CrudRepository<Clinic, String> {

    boolean existsByUserNameC(String userName);

    boolean existsByEmailC(String email);

    boolean existsByLicenseC(String license);

    Optional<Clinic> findByUserNameC(String userName);

}
