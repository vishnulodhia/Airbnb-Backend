package com.spring_boot.Airbnb.Service;

import com.spring_boot.Airbnb.Dto.BookingDto;
import com.spring_boot.Airbnb.Dto.BookingRequest;
import com.spring_boot.Airbnb.Dto.GuestDto;

import java.util.List;

public interface BookingService {
public BookingDto initialiseBooing(BookingRequest bookingRequest);

public BookingDto addGuest(Long bookingId, List<GuestDto> guestDtoList);

}
