package Tests;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Font;
import javax.swing.JLabel;
import org.junit.jupiter.api.Test;
import GUI.PlayerScore;
import Main.Player;
import GUI.PlayerScore;

public class PlayerScoreTest {

    /**
     * A simple dummy implementation of Player that allows us to set and get a score.
     * This class is used for testing purposes.
     */
    private static class DummyPlayer extends Player {
        private int score;

        public DummyPlayer(int score) {
            // We assume that Player has a constructor that we can call.
            this.score = score;
        }

        @Override
        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

    @Test
    void testRefresh() {
        DummyPlayer player = new DummyPlayer(25);
        PlayerScore panel = new PlayerScore(player);
        // Change the player's score
        player.setScore(100);
        // Call refresh to update the label
        panel.refresh();
        JLabel label = (JLabel) panel.getComponent(0);

        // After refresh, the label should reflect the updated score of 100
        assertEquals("Score: 100", label.getText());
    }
}
