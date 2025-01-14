package com.spring_boot.Airbnb.Service;

import com.spring_boot.Airbnb.Dto.HotelDto;
import com.spring_boot.Airbnb.Dto.HotelPriceDto;
import com.spring_boot.Airbnb.Dto.HotelSearchRequest;
import com.spring_boot.Airbnb.Model.Hotel;
import com.spring_boot.Airbnb.Model.Inventory;
import com.spring_boot.Airbnb.Model.Room;
import com.spring_boot.Airbnb.Repository.HotelMinRepository;
import com.spring_boot.Airbnb.Repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;
    private final ModelMapper mapper;
    private final HotelMinRepository hotelMinRepository;



    @Override
    public void initializedRoomForOneYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);
        List<Inventory> inventories = new ArrayList<>();

        for (; !today.isAfter(endDate); today = today.plusDays(1)) {
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .city(room.getHotel().getCity())
                    .bookedCount(0)
                    .date(today)
                    .price(room.getBasePrice())
                    .totalCount(room.getTotalCount())
                    .surgeFactor(BigDecimal.ONE)
                    .closed(false)
                    .build();
            inventories.add(inventory); // Collect each inventory in the list
        }
        inventoryRepository.saveAll(inventories);
    }

    @Override
    public void deleteFutureInventory(Room room) {
        LocalDate today = LocalDate.now();
        inventoryRepository.deleteByDateAfterAndRoom(today,room);
    }

    @Override
    public void deleteAllInventory(Room room) {
        log.info("Deleting this inventories of room with id: {}",room.getId());
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
        log.info("searching hotels for {} city from {} to {}",hotelSearchRequest.getCity(),hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate());
        Pageable pageable = PageRequest.of(hotelSearchRequest.getPage(),hotelSearchRequest.getSize());
        log.info("hotelSearchRequest: {}",hotelSearchRequest);
        Long dateCount  = ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate())+1;
        log.info("betweenDays: {}",dateCount);
        Page<HotelPriceDto> hotelpage = hotelMinRepository.findHotelWithAvailableInventory(hotelSearchRequest.getCity(),hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate(),hotelSearchRequest.getRoomCount(),dateCount,pageable);
        log.info("hotelPage: {}",hotelpage.getContent());
        return hotelpage;
    }
}
