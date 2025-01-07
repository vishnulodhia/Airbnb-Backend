package com.spring_boot.Airbnb.Service;

import com.spring_boot.Airbnb.Dto.HotelDto;
import com.spring_boot.Airbnb.Dto.HotelInfoDto;
import com.spring_boot.Airbnb.Dto.RoomDto;
import com.spring_boot.Airbnb.Exceptions.ResourceNotFoundExceptions;
import com.spring_boot.Airbnb.Model.Hotel;
import com.spring_boot.Airbnb.Model.Room;
import com.spring_boot.Airbnb.Repository.HotelRepository;
import com.spring_boot.Airbnb.Repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService
{
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;


    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating new hotel with name:{}",hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);
        log.info("Created a new hotel with id:{}",hotel.getId());
        return modelMapper.map(hotelRepository.save(hotel),HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("Hotel not found with id"));
        log.info("find hotel with id:{}",hotel.getId());
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id,HotelDto hotelDto) {
    log.info("Updating the hotel with Id: {}",id);
    Hotel hotel = hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("hotel not found"));
    modelMapper.map(hotelDto,hotel);
    hotel.setId(id);
    return modelMapper.map(hotelRepository.save(hotel),HotelDto.class) ;
    }

    @Override
    @Transactional
    public void deleteHotelById(Long hotelId) {
        boolean exists = hotelRepository.existsById(hotelId);
        if(!exists) throw new ResourceNotFoundExceptions("hotel not found");
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(()-> new ResourceNotFoundExceptions("hotel not found"));

        for(Room room: hotel.getRooms()) {
            inventoryService.deleteAllInventory(room);
        }
        hotelRepository.deleteById(hotelId);
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating a hotel with id: {}",hotelId);
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(()-> new ResourceNotFoundExceptions("hotel not found"));
        hotel.setActive(true);
        for(Room room: hotel.getRooms()){
            inventoryService.initializedRoomForOneYear(room);
        }
        hotelRepository.save(hotel);
       }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(()-> new ResourceNotFoundExceptions("hotel not found"));
       List<RoomDto> roomDtoList = hotel.getRooms().stream().map((room)->modelMapper.map(room,RoomDto.class)).toList();
        return HotelInfoDto.builder().hotel(modelMapper.map(hotel,HotelDto.class)).rooms(roomDtoList).build();
    }
}

