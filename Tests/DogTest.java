package Tests;

import Pets.Dog;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DogTest {

    @Test
    void testGetHappinessBoost() {
        // Create a Dog with happiness boost value.
        Dog dog = new Dog("Doggo", 5);

        // Verify that getHappinessBoost() returns the expected value (5).
        assertEquals(5, dog.getHappinessBoost());
    }

    @Test
    void testGetName() {
        Dog dog = new Dog("Doggo", 5);

        // Verify that getName() returns the correct name Doggo.
        assertEquals("Doggo", dog.getName());
    }

    @Test
    void testGetType() {
        Dog dog = new Dog("Doggo", 5);

        // Verify that getType() returns "Dog".
        assertEquals("Dog", dog.getType());
    }

    @Test
    void testPlayWithPet() {
        Dog dog = new Dog("Doggo", 5);
        // Set initial happiness
        dog.setHappiness(50);

        dog.playWithPet(10); // total expected increase: 10 + 5 = 15

        // Happiness should increase by 15 (10 + boost of 5)
        assertEquals(65, dog.getHappiness());

        // Test that the happiness does not exceed MAX_HAPPINESS.
        dog.setHappiness(95);
        dog.playWithPet(10); // 95 + 10 + 5 = 110, but should cap at 100
        assertEquals(100, dog.getHappiness());
    }

    @Test
    void testWalkPet() {
        Dog dog = new Dog("Doggo", 5);

        // Set initial health value
        dog.setHealth(80);
        // walkPet() increases health by (10 + happinessBoost) = 15.
        dog.walkPet();

        // Health should increase by 14 (80 + 15 = 95)
        assertEquals(95, dog.getHealth());

        // Test the capping when addition would exceed MAX_HEALTH (100).
        dog.setHealth(90);
        dog.walkPet(); // 90 + 15 = 105, but should cap at 100.
        assertEquals(100, dog.getHealth());
    }
}
