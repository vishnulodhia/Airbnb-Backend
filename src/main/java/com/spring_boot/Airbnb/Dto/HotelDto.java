package com.spring_boot.Airbnb.Dto;


import com.spring_boot.Airbnb.Model.HotelContactInfo;
import com.spring_boot.Airbnb.Model.Room;
import com.spring_boot.Airbnb.Model.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HotelDto {
    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private HotelContactInfo hotelContactInfo;
    private Boolean active;
}
