package Tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Main.Parent;
import Main.Player;
import Pets.Pet;

public class ParentTest {

    // A nested mock pet so we can instantiate the abstract Pet class
    static class MockPet extends Pet {
        public MockPet(String name) {
            super(name);
        }

        @Override
        public String getType() {
            return "MockPet";
        }
    }

    private Player mockPlayer;
    private Parent parentValid;   // Will be authenticated
    private Parent parentInvalid; // Will remain locked

    /**
     * Helper method to access the private 'totalMinutes' field of Parent,
     */
    private long getTotalMinutes(Parent p) {
        try {
            Field totalMinutesField = Parent.class.getDeclaredField("totalMinutes");
            totalMinutesField.setAccessible(true);
            return (long) totalMinutesField.get(p);
        } catch (Exception e) {
            return -1;
        }
    }

    @BeforeEach
    void setUp() {
        // Create a mock Pet and Player
        Pet mockPet = new MockPet("MockPet");
        mockPlayer = new Player("TestPlayer", mockPet, null, null);

        // Provide correct credentials (should be authenticated)
        parentValid = new Parent("parent", "enter", mockPlayer);

        // Provide incorrect credentials (should remain locked)
        parentInvalid = new Parent("wrong", "pass", mockPlayer);
    }

    @Test
    void testIsAuthenticated() {
        // parentValid should be authenticated
        assertEquals(true, parentValid.isAuthenticated());

        // parentInvalid should remain locked
        assertEquals(false, parentInvalid.isAuthenticated());
    }

    @Test
    void testSetPlayTime() {
        // Start with totalMinutes at 0.
        long initialMinutes = getTotalMinutes(parentValid);
        assertEquals(0, initialMinutes);

        // Add 3 hours: 3 * 60 * 60 * 1000 = 10800000 ms, equals 180 minutes.
        parentValid.setPlayTime(0L, 10800000L);
        long newMinutes = getTotalMinutes(parentValid);
        assertEquals(180, newMinutes);

        // Attempt invalid time, totalMinutes should not change.
        parentValid.setPlayTime(2000L, 1000L);
        long noChangeMinutes = getTotalMinutes(parentValid);
        assertEquals(180, noChangeMinutes);
    }

    @Test
    void testGetTotalMinutes() {
        // Check if initial minutes is 0 
        long initialMinutes = getTotalMinutes(parentValid);
        assertEquals(0, initialMinutes);

        // Increase total minutes by calling setPlayTime.
        // 2 hours in milliseconds: 2 * 60 * 60 * 1000 = 7200000 ms, equals 120 minutes.
        parentValid.setPlayTime(0L, 7200000L);

        // Confirm totalMinutes is updated to 120.
        long updatedMinutes = getTotalMinutes(parentValid);
        assertEquals(120, updatedMinutes);
    }

    @Test
    void testSetTotalMinutes() {
        // Set totalMinutes to 5.
        parentValid.setTotalMinutes(5L);
        long newMinutes = getTotalMinutes(parentValid);
        assertEquals(5, newMinutes);

        // Attempt setting negative -1 minute, should remain 5.
        parentValid.setTotalMinutes(-1L);
        long stillFive = getTotalMinutes(parentValid);
        assertEquals(5, stillFive);
    }
    
    @Test
    void testAuthenticate() {
        // Check correct credentials
        boolean validCheck = parentValid.authenticate("parent", "enter");
        assertEquals(true, validCheck);

        // Wrong name
        boolean wrongName = parentValid.authenticate("wrong", "enter");
        assertEquals(false, wrongName);

        // Wrong password
        boolean wrongPassword = parentValid.authenticate("parent", "bad");
        assertEquals(false, wrongPassword);
    }



}
