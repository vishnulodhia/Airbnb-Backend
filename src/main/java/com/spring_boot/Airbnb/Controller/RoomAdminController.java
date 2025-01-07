package com.spring_boot.Airbnb.Controller;


import com.spring_boot.Airbnb.Dto.RoomDto;
import com.spring_boot.Airbnb.Service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomAdminController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId, @RequestBody RoomDto roomDto){
        log.info("Inside createNewRoom controller");
        RoomDto room = roomService.createNewRoom(hotelId,roomDto);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping
        public ResponseEntity<List<RoomDto>> getAllRoomInHotel(@PathVariable Long hotelId){
        return ResponseEntity.ok(roomService.getAllRoomInHotel(hotelId));
    }

    @GetMapping("/{roomId}")
        public ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId){
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long roomId){
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }
}
