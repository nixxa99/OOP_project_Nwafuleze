package com.restaurantpos;

import com.restaurantpos.factory.MenuItemFactory;
import com.restaurantpos.iterator.DietaryIterator;
import com.restaurantpos.model.MenuCategory;
import com.restaurantpos.model.MenuComponent;
import com.restaurantpos.model.MenuItem;
import com.restaurantpos.model.Order;
import com.restaurantpos.strategy.HappyHourStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class RestaurantTest {

    @Test
    //per verificare che la MenuItemFactory funzioni
    public void testFactoryCreatesCorrectItem() {
        MenuItem item = MenuItemFactory.createItem("Pizza margherita", 8.0, true);
        assertEquals("Pizza margherita", item.getName());
        assertEquals(8.0, item.getPrice());
        assertTrue(item.isVegetarian());
    }

    @Test
    //lancia un'eccezione se tento di inserire piatti con prezzo negativo
    public void testFactoryThrowsExceptionOnNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            MenuItemFactory.createItem("Piatto demo", -5.0, false);
        });
    }

    @Test
    //verifica funzionamento di categorie di menù ccomposite
    public void testCompositeCalculatesTotalPrice() {
        MenuCategory category = new MenuCategory("Menu Feste");
        category.add(MenuItemFactory.createItem("Pasta", 10.0, true));
        category.add(MenuItemFactory.createItem("Carne", 20.0, false));

        assertEquals(30.0, category.getPrice());
        assertFalse(category.isVegetarian());
    }

    @Test
    //verifico che eventuali sconti dell'happy hour vengano applicati(Strategy)
    public void testOrderWithStrategyAndStreams() {
        Order order = new Order(1);
        order.addItem(MenuItemFactory.createItem("Birra", 5.0, true));
        order.addItem(MenuItemFactory.createItem("Patatine", 5.0, true));

        //totale senza sconti dovrebbe risultare uguale a 10
        assertEquals(10.0, order.calculateTotal());

        //applico la "strategia" dello sconto al 20%
        order.setDiscountStrategy(new HappyHourStrategy());
        assertEquals(8.0, order.calculateTotal());
    }

    @Test
    //verifico che il filtraggio dei piatti vegetariani(Iterato) avvnega
    public void testIteratorFiltersVegetarian() {
        MenuCategory root = new MenuCategory("Root");
        root.add(MenuItemFactory.createItem("Salad", 5.0, true));
        root.add(MenuItemFactory.createItem("Burger", 10.0, false));

        DietaryIterator iterator = new DietaryIterator(root, true);
        
        assertTrue(iterator.hasNext());
        MenuComponent item = iterator.next();
        assertEquals("Salad", item.getName());
        
        assertFalse(iterator.hasNext());
    }
}