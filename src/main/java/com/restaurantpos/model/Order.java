package com.restaurantpos.model;

import com.restaurantpos.strategy.DiscountStrategy;
import com.restaurantpos.strategy.NoDiscountStrategy;

import java.util.ArrayList;
import java.util.List;

// Esempio d'ordine di un cliente
public class Order {
    private int tableNumber;
    private List<MenuComponent> items;
    private DiscountStrategy discountStrategy;

    public Order(int tableNumber) {
        this.tableNumber = tableNumber;
        this.items = new ArrayList<>();
        this.discountStrategy = new NoDiscountStrategy();
    }

    public void addItem(MenuComponent item) {
        items.add(item);
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculateBaseTotal() {
        return items.stream()
                .mapToDouble(MenuComponent::getPrice)
                .sum();
    }

    public double calculateDiscountAmount() {
        double baseTotal = calculateBaseTotal();
        double discountedTotal = discountStrategy.applyDiscount(baseTotal);
        return baseTotal - discountedTotal;
    }

    public double calculateTotal() {
        double baseTotal = calculateBaseTotal();
        return discountStrategy.applyDiscount(baseTotal);
    }

    public void printReceipt() {
        double baseTotal = calculateBaseTotal();
        double discountAmount = calculateDiscountAmount();
        double finalTotal = calculateTotal();

        System.out.println("=== SCONTRINO TAVOLO " + tableNumber + " ===");
        items.forEach(item -> System.out.printf("- %s: %.2f€\n", item.getName(), item.getPrice()));
        System.out.println("-------------------------");
        System.out.printf("SUBTOTALE: %.2f€\n", baseTotal);

        if (discountAmount > 0.0) {
            System.out.printf("SCONTO APPLICATO: -%.2f€\n", discountAmount);
        } else {
            System.out.println("SCONTO APPLICATO: nessuno");
        }

        System.out.printf("TOTALE DA PAGARE: %.2f€\n", finalTotal);
        System.out.println("=========================");
    }
}