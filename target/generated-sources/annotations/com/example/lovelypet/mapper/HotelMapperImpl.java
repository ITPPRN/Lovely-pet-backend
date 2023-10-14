package com.example.lovelypet.mapper;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.model.HotelRegisterResponse;
import com.example.lovelypet.model.HotelResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2566-10-14T17:41:22+0700",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class HotelMapperImpl implements HotelMapper {

    @Override
    public HotelRegisterResponse toHotelRegisterResponse(Hotel hotel) {
        if ( hotel == null ) {
            return null;
        }

        HotelRegisterResponse hotelRegisterResponse = new HotelRegisterResponse();

        hotelRegisterResponse.setId( hotel.getId() );
        hotelRegisterResponse.setHotelName( hotel.getHotelName() );
        hotelRegisterResponse.setLocation( hotel.getLocation() );
        hotelRegisterResponse.setHotelTel( hotel.getHotelTel() );
        hotelRegisterResponse.setRating( hotel.getRating() );
        hotelRegisterResponse.setOpenState( hotel.getOpenState() );
        hotelRegisterResponse.setVerify( hotel.getVerify() );
        hotelRegisterResponse.setEmail( hotel.getEmail() );

        return hotelRegisterResponse;
    }

    @Override
    public HotelResponse toHotelResponse(Hotel hotel) {
        if ( hotel == null ) {
            return null;
        }

        HotelResponse hotelResponse = new HotelResponse();

        hotelResponse.setId( hotel.getId() );
        hotelResponse.setHotelName( hotel.getHotelName() );
        hotelResponse.setLocation( hotel.getLocation() );
        hotelResponse.setHotelTel( hotel.getHotelTel() );
        hotelResponse.setRating( hotel.getRating() );
        hotelResponse.setOpenState( hotel.getOpenState() );
        hotelResponse.setVerify( hotel.getVerify() );
        hotelResponse.setEmail( hotel.getEmail() );
        hotelResponse.setAdditionalNotes( hotel.getAdditionalNotes() );

        return hotelResponse;
    }
}
