package com.spring_boot.Airbnb.Dto;

import com.spring_boot.Airbnb.Model.Hotel;
import lombok.Data;

@Data
public class HotelPriceDto {
    private Hotel hotel;
    private  Double price;
}
