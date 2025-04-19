package Tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

public class MusicTest {
    @Test
    void testPlayMusic() {

        // Create test new music file
        File musicFile = new File("GUI/Assets/house_party.wav");

        // Return if music file exists
        assertEquals(true, musicFile.exists());
    }
}
