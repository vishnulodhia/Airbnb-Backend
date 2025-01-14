package com.spring_boot.Airbnb.Strategy;

import com.spring_boot.Airbnb.Model.Inventory;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class BasePricingStrategy implements PricingStrategy{


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        log.info("BasePricingStrategy inventory price: {} room id : {}",inventory.getPrice(),inventory.getRoom().getId());
        return inventory.getRoom().getBasePrice();
    }
}
