package com.spring_boot.Airbnb.Strategy;

import com.spring_boot.Airbnb.Model.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStrategy{

   private  final  PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price= wrapped.calculatePrice(inventory);

        LocalDate today = LocalDate.now();

        if(today.plusDays(7).isAfter(inventory.getDate())){
            price  = price.multiply(BigDecimal.valueOf(1.15));
            log.info("UrgencyPricingStrategy inventory price: {} room id: {}",price,inventory.getRoom().getId());
        }

        return price;
    }
}
