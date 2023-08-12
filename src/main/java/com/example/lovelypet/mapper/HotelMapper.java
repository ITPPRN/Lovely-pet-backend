package com.example.lovelypet.mapper;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.model.HotelRegisterResponse;
import com.example.lovelypet.model.HotelResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface
HotelMapper {
    HotelRegisterResponse toHotelRegisterResponse(Hotel hotel);

    HotelResponse toHotelResponse(Hotel hotel);
}
