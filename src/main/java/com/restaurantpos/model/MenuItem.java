package com.restaurantpos.model;

// COMPOSITE PATTERN per rappresentare il singolo piatto (elemento indivisibile del menù)
public class MenuItem implements MenuComponent {
    private String name;
    private double price;
    private boolean isVegetarian;

    public MenuItem(String name, double price, boolean isVegetarian) {
        this.name = name;
        this.price = price;
        this.isVegetarian = isVegetarian;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public boolean isVegetarian() {
        return isVegetarian;
    }

    @Override
    public void print(String indent) {
        String vegFlag = isVegetarian ? " [V]" : "";
        System.out.printf("%s- %s%s : %.2f€\n", indent, name, vegFlag, price);
    }
}
