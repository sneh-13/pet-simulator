package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GUI.PlayingInstructions;
import Main.GamePanel;
import Main.GameState;

public class PlayingInstructionsTest {

    private DummyGamePanel dummyGamePanel;
    private PlayingInstructions instructionsPanel;
    private JButton backButton;

    /**
     * A dummy implementation of GamePanel that records calls to switchState.
     */
    private class DummyGamePanel extends GamePanel {
        public GameState lastSwitchedState = null;

        @Override
        public void switchState(GameState state) {
            lastSwitchedState = state;
        }
    }

    @BeforeEach
    void setUp() {
        dummyGamePanel = new DummyGamePanel();
        instructionsPanel = new PlayingInstructions(dummyGamePanel);
    }

    @Test
    void testConstructorComponents() {
        // Check the the panel's background color.
        Color expectedColor = new Color(194, 242, 207);
        assertEquals(expectedColor, instructionsPanel.getBackground());

        // Assume that the title label is the first component added.
        JLabel titleLabel = (JLabel) instructionsPanel.getComponent(0);
        assertEquals("Instructions", titleLabel.getText());
    }
}

