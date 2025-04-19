package Tests;

import Pets.Bunny;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BunnyTest {

    @Test
    void testGetName() {
        // Create a Bunny with a given name "Bun"
        Bunny bunny = new Bunny("Bun", 5);

        // Check if getName() returns the correct name ("Bun")
        assertEquals("Bun", bunny.getName());
    }

    @Test
    void testGetSpeedBoost() {
        Bunny bunny = new Bunny("Bun", 5);

        // Check if getSpeedBoost() returns the correct speed boost (5).
        assertEquals(5, bunny.getSpeedBoost());
    }

    @Test
    void testGetType() {
        Bunny bunny = new Bunny("Bun", 5);

        // Check if getType() returns "Bunny".
        assertEquals("Bunny", bunny.getType());
    }

    @Test
    void testWalkPet() {
        Bunny bunny = new Bunny("Bun", 5);

        // Health increase does not exceed MAX_HEALTH.
        bunny.setHealth(80); // initial health

        // walkPet() should increase health by (10 + speedBoost) = 15.
        bunny.walkPet();
        assertEquals(95, bunny.getHealth(), "Health should increase to 95 (80 + 10 + 5)");

        // Health increase exceeds MAX_HEALTH.
        bunny.setHealth(95);
        bunny.walkPet();

        // Expected health would be 95 + 15 = 110, but should be capped at 100.
        assertEquals(100, bunny.getHealth(), "Health should be capped at MAX_HEALTH (100)");
    }
}
