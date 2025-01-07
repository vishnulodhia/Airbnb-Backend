package com.spring_boot.Airbnb.Dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Data
public class BookingRequest {
private Long hotelId;
private Long roomId;
private LocalDate checkInDate;
private LocalDate checkOutDate;
private Integer roomCount;
}
