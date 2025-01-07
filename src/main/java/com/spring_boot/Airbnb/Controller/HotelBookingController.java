package com.spring_boot.Airbnb.Controller;


import com.spring_boot.Airbnb.Dto.BookingDto;
import com.spring_boot.Airbnb.Dto.BookingRequest;
import com.spring_boot.Airbnb.Dto.GuestDto;
import com.spring_boot.Airbnb.Service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Slf4j
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    private ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest){
    return ResponseEntity.ok(bookingService.initialiseBooing(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    private ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId, @RequestBody List<GuestDto> guestDtoList){
        log.info("GuestDto {}",guestDtoList);
        return ResponseEntity.ok(bookingService.addGuest(bookingId,guestDtoList));
    }
}
