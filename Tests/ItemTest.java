package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import Main.Item;
import Main.ItemType;

public class ItemTest {
    @Test
    void testGetEffect() {
        // Create new Item "apple"
        Item item = new Item("apple", ItemType.FOOD, 10);

        // Verify the effect is correctly returned
        assertEquals(10, item.getEffect());
    }

    @Test
    void testGetName() {
        Item item = new Item("apple", ItemType.FOOD, 10);

        // Verify the name is correctly returned
        assertEquals("apple", item.getName());
    }

    @Test
    void testGetType() {
        Item item = new Item("apple", ItemType.FOOD, 10);
        
        // Verify the type is correctly returned
        assertEquals(ItemType.FOOD, item.getType());
    }
}
