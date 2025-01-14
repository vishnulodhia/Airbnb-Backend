package com.spring_boot.Airbnb.Strategy;

import com.spring_boot.Airbnb.Model.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;


@RequiredArgsConstructor
@Slf4j
public class HolidayPricingStrategy implements PricingStrategy {


    private  final  PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price= wrapped.calculatePrice(inventory);
        boolean isTodayHoliday = true;// call an api or check with local data

        if(isTodayHoliday){
            price  = price.multiply(BigDecimal.valueOf(1.25));
            log.info("HolidayPricingStrategy inventory price: {} room id: {}",price,inventory.getRoom().getId());
        }

        return price;
    }

}

