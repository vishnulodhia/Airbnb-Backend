package com.spring_boot.Airbnb.Dto;

import com.spring_boot.Airbnb.Model.Hotel;
import lombok.Data;

@Data
public class HotelPriceDto {
    private Hotel hotel;
    private  Double price;

    public HotelPriceDto(Hotel hotel, Double averagePrice) {
        this.hotel = hotel;
        this.price = averagePrice;
    }
}
