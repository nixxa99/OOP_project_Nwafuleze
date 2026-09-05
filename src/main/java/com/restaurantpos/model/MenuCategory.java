package com.restaurantpos.model;

import java.util.ArrayList;
import java.util.List;

//COMPOSITE PATTERN che contiene una lista di altri componenti
//(piatti singoli o altre sottocategorie)
public class MenuCategory implements MenuComponent {
    private String name;
    private List<MenuComponent> components = new ArrayList<>();

    public MenuCategory(String name) {
        this.name = name;
    }

    public void add(MenuComponent component) {
        components.add(component);
    }

    public List<MenuComponent> getComponents() {
        return components;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        // Uso Stream API per calcolare la somma ricorsiva
        // tra i vari piatti, prende solo il valore "PREZZO"
        return components.stream()
                .mapToDouble(MenuComponent::getPrice)
                .sum();
    }

    @Override
    public boolean isVegetarian() {
        return components.stream().allMatch(MenuComponent::isVegetarian);
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "+ " + name);
        components.forEach(component -> component.print(indent + "  "));
    }
}
