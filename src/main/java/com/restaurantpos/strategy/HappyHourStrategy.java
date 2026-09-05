package com.restaurantpos.strategy;

public class HappyHourStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double baseTotal) {
        // Applica il 20% di sconto fisso durante l'Happy Hour
        return baseTotal * 0.80; 
    }
}
