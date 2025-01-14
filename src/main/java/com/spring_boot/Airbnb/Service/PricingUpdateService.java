package com.spring_boot.Airbnb.Service;

import com.spring_boot.Airbnb.Model.Hotel;
import com.spring_boot.Airbnb.Model.HotelMinPrice;
import com.spring_boot.Airbnb.Model.Inventory;
import com.spring_boot.Airbnb.Repository.HotelMinRepository;
import com.spring_boot.Airbnb.Repository.HotelRepository;
import com.spring_boot.Airbnb.Repository.InventoryRepository;
import com.spring_boot.Airbnb.Strategy.PricingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricingUpdateService {


//  scheduler to update inventory every hour

    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;
    private final HotelMinRepository hotelMinRepository;
    private final PricingService pricingService;


//    @Scheduled(cron = "")
    public void updatePrice(){
    int page =0;
    int batchSize = 100;
        try{
            while (true) {
                Page<Hotel> hotelpage = hotelRepository.findAll(PageRequest.of(page, batchSize));
                if (hotelpage.isEmpty())
                    break;
                hotelpage.getContent().forEach(this::updateHotelPrice);

                page++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateHotelPrice(Hotel hotel){
        log.info("Updating hotel price every hour of hotel ID: {}",hotel.getId());
        LocalDate startDate = LocalDate.now();
        LocalDate endaDate = LocalDate.now().plusDays(365);

        List<Inventory> inventoryList = inventoryRepository.findByHotelAndDateBetween(hotel,startDate,endaDate);
        log.info("List of inventory:{} ",inventoryList);
        updateInventoryPrice(inventoryList);
        updateHotelMinPrice(hotel,inventoryList,startDate,endaDate);
    }

    private void updateHotelMinPrice(Hotel hotel,List<Inventory> inventoryList,LocalDate startDate,LocalDate endDate){

        Map<LocalDate,BigDecimal> dailyMinPrice = inventoryList.stream()
                .collect(Collectors.groupingBy(Inventory::getDate,Collectors.mapping(Inventory::getPrice,Collectors.minBy(Comparator.naturalOrder()))
                )).entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,e->e.getValue().orElse(BigDecimal.ZERO)));

        List<HotelMinPrice> hotelMinPricesList = new ArrayList<>();

        dailyMinPrice.forEach((date,price)->{
            HotelMinPrice hotelMinPrice = hotelMinRepository.findByHotelAndDate(hotel,date).orElse(new HotelMinPrice(hotel,date));
            hotelMinPrice.setPrice(price);
            hotelMinPrice.setDate(date);
            hotelMinPricesList.add(hotelMinPrice);
        });

        hotelMinRepository.saveAll(hotelMinPricesList);
    }

    private void updateInventoryPrice(List<Inventory> inventoryList){

        inventoryList.forEach(inventory -> {
            BigDecimal dynamicPrice = pricingService.calculateDynamicPricing(inventory);
            inventory.setPrice(dynamicPrice);
        });

        inventoryRepository.saveAll(inventoryList);
    }




}
