package com.example.lovelypet.mapper;

import com.example.lovelypet.entity.AdditionalServices;
import com.example.lovelypet.model.AdditionalServiceResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2566-10-14T15:12:48+0700",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class AdditionalServiceMapperImpl implements AdditionalServiceMapper {

    @Override
    public AdditionalServiceResponse toAdditionalServiceResponse(AdditionalServices additionalServices) {
        if ( additionalServices == null ) {
            return null;
        }

        AdditionalServiceResponse additionalServiceResponse = new AdditionalServiceResponse();

        additionalServiceResponse.setId( additionalServices.getId() );
        additionalServiceResponse.setName( additionalServices.getName() );
        additionalServiceResponse.setPrice( additionalServices.getPrice() );

        return additionalServiceResponse;
    }
}
