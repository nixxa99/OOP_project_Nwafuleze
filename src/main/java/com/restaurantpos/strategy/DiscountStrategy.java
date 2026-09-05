package com.restaurantpos.strategy;

// STRATEGY PATTERN
// Definisce una famiglia di algoritmi per il calcolo dello sconto

public interface DiscountStrategy {
    double applyDiscount(double baseTotal);
}
