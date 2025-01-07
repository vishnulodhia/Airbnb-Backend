package com.spring_boot.Airbnb.Service;

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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{


    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper mapper;
    private final InventoryService inventoryService;


    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating a new room in hotel with ID: {}", hotelId);

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("hotel not found with ID: " + hotelId));

        Room room = mapper.map(roomDto, Room.class);
        log.info("Room: {}, RoomDto: {}", room, roomDto);

        room.setHotel(hotel);
        room.setActive(false);  // Make sure to set the active flag if necessary
        room = roomRepository.save(room);

        // Uncomment to create inventory if needed
         if(hotel.getActive()) {
             inventoryService.initializedRoomForOneYear(room);
         }

        return mapper.map(room
                , RoomDto.class);
    }


    @Override
    public List<RoomDto> getAllRoomInHotel(Long hotelId) {
        log.info("Get all rooms in hotel with hotel ID: {} :",hotelId);
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(()-> new ResourceNotFoundExceptions("Hotel not found with ID: "+hotelId));
        return hotel.getRooms().stream().map((element)-> mapper.map(element, RoomDto.class)).collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Get room with ID: {} :",roomId);
        Room room = roomRepository.findById(roomId).orElseThrow(()-> new ResourceNotFoundExceptions("room not found with ID: "+roomId));
        return mapper.map(room, RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()-> new ResourceNotFoundExceptions("room not found with ID: "+roomId));
        inventoryService.deleteFutureInventory(room);
        roomRepository.deleteById(roomId);
    }
}
