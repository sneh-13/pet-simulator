package Tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Main.Inventory;
import Main.Item;
import Main.ItemType;

public class InventoryTest {

    static class TestItem extends Item {
        public TestItem(String name, ItemType type, int effect) {
            super(name, type, effect);
        }
    }

    private Inventory inventory;
    private Item apple;
    private Item toy;
    private Item medicine;

    @BeforeEach
    void setUp() {
        // Create an Inventory object with a certain max size
        inventory = new Inventory(3);
        
        // Create some test items
        apple = new TestItem("apple", ItemType.FOOD, 10);
        toy = new TestItem("toy", ItemType.TOY, 5);
        medicine = new TestItem("medicine", ItemType.MEDICINE, 15);
    }

    @Test
    void testAddItem() {
        // Add a single item to an empty inventory
        boolean added = inventory.addItem(apple);

        // Use assertEquals instead of assertTrue
        assertEquals(true, added);
        
        // Verify the item is present
        assertEquals(true, inventory.hasItem(apple.getType()));
    }

    @Test
    void testGetItem() {
        // Add item to inventory
        inventory.addItem(apple);
        Item item = inventory.getItem(apple.getType());
        
        assertEquals("apple", item.getName());
    }

    @Test
    void testHasItem() {
        // Initially, inventory should not have "apple"
        assertEquals(false, inventory.hasItem(apple.getType()));

        // Add "apple" and check again
        inventory.addItem(apple);
        assertEquals(true, inventory.hasItem(apple.getType()));

        // "toy" has not been added yet
        assertEquals(false, inventory.hasItem(toy.getType()));
    }

    @Test
    void testRemoveItem() {
        inventory.addItem(apple);

        // Remove "apple"
        boolean removed = inventory.removeItem(apple.getType());
        assertEquals(true, removed);

        // Check if "apple" is gone
        assertEquals(false, inventory.hasItem(apple.getType()));
    }

    @Test
    void testUseItem() {
        // Add items
        inventory.addItem(apple);

        // Use "apple"
        int effectApple = inventory.useItem(apple.getType());

        // The effect for `apple` was set to 10
        assertEquals(10, effectApple);

        // Verify it's removed
        assertEquals(false, inventory.hasItem(apple.getType()));
    }
}
