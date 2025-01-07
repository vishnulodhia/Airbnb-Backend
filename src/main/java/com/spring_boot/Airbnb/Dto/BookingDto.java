package com.spring_boot.Airbnb.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring_boot.Airbnb.Model.*;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
@Data
public class BookingDto {

    private Long id;
    private Integer roomCount;
    private Date checkInDate;
    private Date checkOutDate;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Payment payment;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guest;
}
