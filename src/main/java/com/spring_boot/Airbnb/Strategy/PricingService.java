package com.spring_boot.Airbnb.Strategy;

import com.spring_boot.Airbnb.Model.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingService {

    public BigDecimal calculateDynamicPricing(Inventory inventory){
    PricingStrategy pricingStrategy = new BasePricingStrategy();

//    applying strategy
        pricingStrategy = new SurgePricingStrategy(pricingStrategy);
        pricingStrategy = new HolidayPricingStrategy(pricingStrategy);
        pricingStrategy = new UrgencyPricingStrategy(pricingStrategy);
        pricingStrategy = new OccupancyPricingStrategy(pricingStrategy);

    return pricingStrategy.calculatePrice(inventory);
    }

}
