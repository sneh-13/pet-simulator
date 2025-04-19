package Tests;

import Pets.Pet;
import Main.Inventory;
import Main.Item;
import Main.ItemType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

public class PetTest {

    /**
     * A concrete subclass of Pet for testing purposes.
     */
    class TestPet extends Pet {
        public TestPet(String name) {
            super(name);
        }
        @Override
        public String getType() {
            return "TestPet";
        }
        @Override
        public String getName() {
            return name;
        }
    }
    
    /**
     * Utility method to get the value of a private boolean field using reflection.
     */
    private boolean getBooleanField(Pet pet, String fieldName) {
        try {
            Field field = Pet.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getBoolean(pet);
        } catch (Exception e) {
            fail("Reflection error: " + e.getMessage());
            return false;
        }
    }

    @Test
    void testFeedPet() {
        TestPet pet = new TestPet("Buddy");
        pet.setFullness(50);
        pet.feedPet(30);
        // Expected: fullness becomes 80 (50 + 30), capped by MAX_FULLNESS if necessary.
        assertEquals(80, pet.getFullness());
    }

    @Test
    void testGetFullness() {
        TestPet pet = new TestPet("Buddy");
        pet.setFullness(75);
        assertEquals(75, pet.getFullness());
    }

    @Test
    void testGetHappiness() {
        TestPet pet = new TestPet("Buddy");
        pet.setHappiness(65);
        assertEquals(65, pet.getHappiness());
    }

    @Test
    void testGetHealth() {
        TestPet pet = new TestPet("Buddy");
        pet.setHealth(85);
        assertEquals(85, pet.getHealth());
    }

    @Test
    void testGetInventory() {
        TestPet pet = new TestPet("Buddy");
        Inventory inv = pet.getInventory();
        assertNotNull(inv);
    }

    @Test
    void testGetItemCount() {
        TestPet pet = new TestPet("Buddy");
        int initialCount = pet.getItemCount();
        assertEquals(0, pet.getItemCount());
    }

    @Test
    void testGetName() {
        TestPet pet = new TestPet("Buddy");
        assertEquals("Buddy", pet.getName());
    }

    @Test
    void testGetSleep() {
        TestPet pet = new TestPet("Buddy");
        pet.setSleep(40);
        assertEquals(40, pet.getSleep());
    }

    @Test
    void testGetType() {
        TestPet pet = new TestPet("Buddy");
        assertEquals("TestPet", pet.getType());
    }

    @Test
    void testGiveGift() {
        TestPet pet = new TestPet("Buddy");
        pet.setHappiness(50);
        pet.giveGift(20);
        // Expected: happiness becomes 70 (50 + 20), capped at MAX_HAPPINESS if necessary.
        assertEquals(70, pet.getHappiness());
    }

    @Test
    void testGoToBed() {
        TestPet pet = new TestPet("Buddy");
        pet.setSleep(50); // Set pet sleep to 50
        int sleepBefore = pet.getSleep();
        pet.goToBed();

        // Calling update() while sleeping should restore sleep by 2.
        pet.update();

        // Pet should have 52 sleep stats
        assertEquals(sleepBefore + 2, pet.getSleep()); 
    }

    @Test
    void testIsDead() {
        TestPet pet = new TestPet("Buddy");
        pet.setDead(true);
        assertTrue(pet.isDead());
    }

    @Test
    void testPlayWithPet() {
        TestPet pet = new TestPet("Buddy");
        pet.setHappiness(50);
        pet.playWithPet(20);
        // After first play, happiness should be 70.
        assertEquals(70, pet.getHappiness());

        // A second call immediately should not increase happiness due to cooldown.
        pet.playWithPet(20);
        assertEquals(70, pet.getHappiness());
    }

    @Test
    void testSetDead() {
        TestPet pet = new TestPet("Buddy");

        pet.setDead(true);
        assertTrue(pet.isDead());
    }

    @Test
    void testSetFullness() {
        TestPet pet = new TestPet("Buddy");
        pet.setFullness(50);
        assertEquals(50, pet.getFullness());
    }

    @Test
    void testSetHappiness() {
        TestPet pet = new TestPet("Buddy");
        pet.setHappiness(50);
        assertEquals(50, pet.getHappiness());
    }

    @Test
    void testSetHealth() {
        TestPet pet = new TestPet("Buddy");
        pet.setHealth(50);
        assertEquals(50, pet.getHealth());
    }

    @Test
    void testSetName() {
        TestPet pet = new TestPet("Buddy");
        pet.setName("Max");
        assertEquals("Max", pet.getName());
    }

    @Test
    void testSetSleep() {
        TestPet pet = new TestPet("Buddy");
        pet.setSleep(50);
        assertEquals(50, pet.getSleep());
    }

    @Test
    void testTakeToVet() {
        TestPet pet = new TestPet("Buddy");
        pet.setHealth(60);
        pet.takeToVet();

        // Health should increase by 35, but not exceed MAX_HEALTH (100).
        assertEquals(95, pet.getHealth());
    }


    @Test
    void testUseItem() {
        TestPet pet = new TestPet("Buddy");
        int countBefore = pet.getItemCount();
        pet.setFullness(90);
        Item item = new Item("apple", ItemType.FOOD, 10);
        pet.addItem(item);
        pet.useItem(ItemType.FOOD);

        // After using the item, the item count should decrease.
        assertTrue(pet.getItemCount() < countBefore + 1);
    }

    @Test
    void testWalkPet() {
        TestPet pet = new TestPet("Buddy");
        
        pet.setHealth(90);
        pet.setSleep(50);
        pet.setFullness(50);
        pet.walkPet();

        // After a walk: 
        // Health increases by 10
        // Sleep decreases by 5,
        // Fullness decreases by 5.
        assertEquals(100, pet.getHealth());
        assertEquals(45, pet.getSleep());
        assertEquals(45, pet.getFullness());
    }
}
