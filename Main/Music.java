package Main;

import java.io.File;
import javax.sound.sampled.*;

/**
 * The Music class provides methods to play background music for the game.
 * <p>
 * Static class that contains methods to play the background music in a loop.
 * </p>
 */
public class Music {
    /**
     * Plays the background music for the game in a loop.
     * <p>
     * This method opens the music file located at "GUI/Assets/house_party.wav" and starts playing it in a continuous loop.
     * </p>
     */
    public static void playMusic() {
        try {
            File musicFile = new File("GUI/Assets/house_party.wav");
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            
            Clip clip = AudioSystem.getClip();
            
            clip.open(audioStream);
            
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            
            clip.start();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }        
    }
}