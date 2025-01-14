package com.spring_boot.Airbnb.Controller;


import com.spring_boot.Airbnb.Dto.HotelDto;
import com.spring_boot.Airbnb.Dto.HotelInfoDto;
import com.spring_boot.Airbnb.Dto.HotelPriceDto;
import com.spring_boot.Airbnb.Dto.HotelSearchRequest;
import com.spring_boot.Airbnb.Model.HotelMinPrice;
import com.spring_boot.Airbnb.Service.HotelService;
import com.spring_boot.Airbnb.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelBrowseController {

    private final InventoryService inventoryService;

    private final HotelService hotelService;

    @GetMapping("/search")
 private ResponseEntity<Page<HotelPriceDto>> searchHotel(@RequestBody HotelSearchRequest hotelSearchRequest){
      var page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }

}
