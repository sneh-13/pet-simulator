
package GUI;

import Main.Player;
import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A panel that displays and updates the player's score.
 * <p>
 * This class extends {@link JPanel} and contains a {@link JLabel} to show the score.
 * The score can be updated via the {@link #updateScore(int)} method.
 * </p>
 */
public class PlayerScore extends JPanel {
    private JLabel scoreLabel;
    private Player player;
    private int score; // Score of the player

     /**
     * Constructs a new {@code PlayerScore} panel with an initial score of zero.
     * <p>
     * The constructor initializes the score label with a bold Arial font,
     * sets its position and size, and adds it to the panel. The panel is set to be transparent.
     * </p>
     */

    public PlayerScore(Player player) {
        this.player = player;

        scoreLabel = new JLabel("Score: " + player.getScore());
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setBounds(10, 10, 150, 30);
        this.add(scoreLabel);
        this.setOpaque(false);

    }
     /**
     * Constructs a new {@code PlayerScore} panel with an initial score of zero.
     * <p>
     * The constructor initializes the score label with a bold Arial font,
     * sets its position and size, and adds it to the panel. The panel is set to be transparent.
     * </p>
     */
    public PlayerScore() {
        score = 0;
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setBounds(10, 10, 150, 30);
        this.add(scoreLabel);
        this.setOpaque(false);
    }

    public void refresh() {
        scoreLabel.setText("Score: " + player.getScore());
    }
}
