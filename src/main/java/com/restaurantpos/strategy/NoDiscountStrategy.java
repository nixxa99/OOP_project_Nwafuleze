package com.restaurantpos.strategy;

public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    //caso in cui non vengono applicati sconti
    public double applyDiscount(double baseTotal) {
        return baseTotal;
    }
}
