package com.spring_boot.Airbnb.Strategy;

import com.spring_boot.Airbnb.Model.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);
}
