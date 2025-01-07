package com.spring_boot.Airbnb.Service;

import com.spring_boot.Airbnb.Dto.BookingDto;
import com.spring_boot.Airbnb.Dto.BookingRequest;
import com.spring_boot.Airbnb.Dto.GuestDto;
import com.spring_boot.Airbnb.Exceptions.ResourceNotFoundExceptions;
import com.spring_boot.Airbnb.Model.*;
import com.spring_boot.Airbnb.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final HotelRepository hotelRepository;

    private final RoomRepository roomRepository;

    private final InventoryRepository inventoryRepository;

    private final ModelMapper mapper;

    private final UserRepository userRepository;

    private final GuestRepository guestRepository;


    @Override
    @Transactional
    public BookingDto initialiseBooing(BookingRequest bookingRequest) {
       log.info("Initialising booking for hotel :{}, room :{},date {}--{}",bookingRequest.getHotelId(),bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate());

        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(()-> new ResourceNotFoundExceptions("Hotel not found with id: "+bookingRequest.getHotelId()));
        Room room = roomRepository.findById(bookingRequest.getHotelId()).orElseThrow(()-> new ResourceNotFoundExceptions("Room not found with id: "+bookingRequest.getRoomId()));
        User user = getCurrentUser();
        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate(),bookingRequest.getRoomCount());
       Long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate())+1;
         System.out.println("Inventory size"+ inventoryList);

       if(inventoryList.size() != daysCount) {
           throw new IllegalStateException("Room is not available anymore");
       }

//     Reserve  the room update the booked count

        for(Inventory inventory: inventoryList)
            inventory.setReserveCount(inventory.getBookedCount()+ bookingRequest.getRoomCount());

        inventoryRepository.saveAll(inventoryList);

//        create the booking
// TODO calculate dynamic amount
          Booking booking = Booking.builder().bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel).room(room).checkInDate(bookingRequest.getCheckInDate())
                .user(user)
                .checkOutDate(bookingRequest.getCheckOutDate())
                .roomCount(bookingRequest.getRoomCount())
                .amount(BigDecimal.TEN).build();

        return  mapper.map(bookingRepository.save(booking),BookingDto.class);
    }

    @Override
    public BookingDto addGuest(Long bookingId, List<GuestDto> guestDtoList) {
        log.info("Adding guests for booking with id: {}",bookingId);
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()-> new ResourceNotFoundExceptions("Booking not found with id: "+bookingId));

        if(hasBookingExpire(booking)){
        throw new IllegalStateException("Booking has already expire");
        }

        log.info("booking {}",booking);

        if(booking.getBookingStatus()!= BookingStatus.RESERVED){
            throw new IllegalStateException("Booking is not under reserve state,cannot add guest");
        }
        List<Guest> guestList = guestDtoList.stream()
                .map(guestDto -> {
                    Guest guest = mapper.map(guestDto, Guest.class);
                    guest.setUser(getCurrentUser());
                    return guest;
                })
                .collect(Collectors.toList());
        log.info("Guest: {}",guestList);

        guestRepository.saveAll(guestList);


        booking.setBookingStatus(BookingStatus.GUEST_ADDED);

        return mapper.map(bookingRepository.save(booking),BookingDto.class);
    }

    public boolean hasBookingExpire(Booking booking){
        return booking.getCreateAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser(){
        Optional<User> user = userRepository.findById(Long.valueOf(1));
        return user.get();
    }

}
