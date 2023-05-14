package com.example.lovelypet.mapper;

import com.example.lovelypet.entity.Clinic;
import com.example.lovelypet.model.ClinicRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClinicMapper {

    ClinicRegisterResponse toClinicRegisterResponse(Clinic clinic);
}
