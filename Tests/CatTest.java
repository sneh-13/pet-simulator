package Tests;

import Pets.Cat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CatTest {
    
    @Test
    void testGetName() {
        // Create Cat name "Cat" with speedBoost 10
        Cat cat = new Cat("Cat", 10);

        // The cat's name should be "Cat"
        assertEquals("Cat", cat.getName());
    }

    @Test
    void testGetSleepBoost() {
        Cat cat = new Cat("Cat", 10);

        // The sleep boost should be 10
        assertEquals(10, cat.getSleepBoost());
    }

    @Test
    void testGetType() {
        Cat cat = new Cat("Cat", 10);

        // The cat type should be Cat
        assertEquals("Cat", cat.getType());
    }
}
