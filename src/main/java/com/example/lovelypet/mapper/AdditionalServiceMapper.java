package com.example.lovelypet.mapper;

import com.example.lovelypet.entity.AdditionalServices;
import com.example.lovelypet.model.AdditionalServiceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdditionalServiceMapper {
    AdditionalServiceResponse toAdditionalServiceResponse(AdditionalServices additionalServices);
}
