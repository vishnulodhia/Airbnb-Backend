package com.spring_boot.Airbnb.Service;

import com.spring_boot.Airbnb.Dto.RoomDto;

import java.util.List;

public interface RoomService {

    RoomDto createNewRoom(Long hotelId,RoomDto roomDto);

    List<RoomDto> getAllRoomInHotel(Long hotelId);

    RoomDto getRoomById(Long roomId);

    void deleteRoomById(Long roomId);

}
