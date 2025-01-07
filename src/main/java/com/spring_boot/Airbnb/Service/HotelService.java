package com.spring_boot.Airbnb.Service;

import com.spring_boot.Airbnb.Dto.HotelDto;
import com.spring_boot.Airbnb.Dto.HotelInfoDto;
import com.spring_boot.Airbnb.Model.Hotel;

public interface HotelService {

    public HotelDto createNewHotel(HotelDto hotelDto);

    public HotelDto getHotelById(Long id);

    public HotelDto updateHotelById(Long id,HotelDto hotelDto);

    public void deleteHotelById(Long hotelId);
//
    public void activateHotel(Long hotelId);

    public HotelInfoDto getHotelInfoById(Long hotelId);
}
