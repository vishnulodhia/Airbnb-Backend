package com.spring_boot.Airbnb.Service;

import com.spring_boot.Airbnb.Dto.HotelDto;
import com.spring_boot.Airbnb.Dto.HotelPriceDto;
import com.spring_boot.Airbnb.Dto.HotelSearchRequest;
import com.spring_boot.Airbnb.Model.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializedRoomForOneYear(Room room);

    void deleteFutureInventory(Room room);

    void deleteAllInventory(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
