package com.restaurantpos.iterator;

import com.restaurantpos.model.MenuCategory;
import com.restaurantpos.model.MenuComponent;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

//ITERATOR PATTERN
//Scorre in profondità (DFS) un albero Composite per filtrare specifici piatti.

public class DietaryIterator implements MenuIterator {
    private Stack<Iterator<MenuComponent>> iterators = new Stack<>();
    private MenuComponent nextItem = null;
    private boolean vegetarianOnly;

    public DietaryIterator(MenuComponent rootCategory, boolean vegetarianOnly) {
        this.vegetarianOnly = vegetarianOnly;
        if (rootCategory instanceof MenuCategory) {
            iterators.push(((MenuCategory) rootCategory).getComponents().iterator());
        }
    }

    @Override
    public boolean hasNext() {
        if (nextItem != null) {
            return true;
        }

        while (!iterators.isEmpty()) {
            Iterator<MenuComponent> currentIterator = iterators.peek();
            
            if (!currentIterator.hasNext()) {
                iterators.pop();
                continue;
            }

            MenuComponent component = currentIterator.next();
            
            if (component instanceof MenuCategory) {
                iterators.push(((MenuCategory) component).getComponents().iterator());
            } else {
                if (!vegetarianOnly || component.isVegetarian()) {
                    nextItem = component;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public MenuComponent next() {
        if (hasNext()) {
            MenuComponent toReturn = nextItem;
            nextItem = null;
            return toReturn;
        }
        throw new NoSuchElementException();
    }
}
