package GUI;

import Main.GamePanel;
import Main.GameState;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Represents the instructions screen of the Virtual Pet Game.
 * <p>
 * The {@code Instructions} class extends {@link JPanel} and provides a user
 * interface displaying the instructions for playing the game. It shows how to
 * interact with the pet using various controls such as moving, feeding,
 * playing, sleeping, and visiting the vet. The screen also provides a button
 * that allows the user to navigate back to the previous screen or main menu.
 * </p>
 *
 * <p>
 * This class also implements {@link MouseListener} to handle mouse events, such
 * as clicking the "Back" button to navigate through the game.
 * </p>
 */
public class MenuInstructions extends JPanel implements MouseListener {

    // Initialize variables
    private JFrame frame;
    private JLabel titleLabel;
    private JButton next;
    private GamePanel gamePanel;

    /**
     * Constructs a new {@code Instructions} screen panel for displaying the
     * game instructions.
     * <p>
     * This constructor sets up the user interface for the instructions screen,
     * including the title, instructions, and a "Back" button to navigate back
     * to the previous screen or main menu.
     * </p>
     *
     * @param gp
     */
    public MenuInstructions(GamePanel gp) {
        // Main screen
        this.gamePanel = gp;
        this.setLayout(null);
        this.setBackground(new Color(194, 242, 207));

        //Title
        titleLabel = new JLabel("Instructions", SwingConstants.CENTER);
        titleLabel.setBounds(400, 200, 700, 50);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
        this.add(titleLabel);

        // Instructions
        JLabel instructions = new JLabel(
                "<html><div style='text-align: center;'>"
                + "\uD83D\uDC3E <b>Welcome to the Virtual Pet Game!</b><br><br>"
                + "\u2022 Use the arrow keys to move your pet<br><br>"
                + "\u2022 Press 'F' to feed your pet when it's hungry<br><br>"
                + "\u2022 Press 'P' to play with your pet and increase happiness<br><br>"
                + "\u2022 Press 'S' to let your pet sleep to regain energy<br><br>"
                + "\u2022 Press 'V' to visit the vet when your pet's health is low<br><br>"
                + "\u2022 Press 'I' to check your inventory<br><br>"
                + "\u2022 Save your game from the menu<br>"
                + "</div></html>",
                SwingConstants.CENTER);
        instructions.setBounds(400, 300, 650, 200);
        instructions.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        this.add(instructions);

        // Back button
        next = new JButton("Back");
        next.setBounds(665, 510, 100, 40);
        next.setBackground(Color.green);
        this.add(next);
        next.addMouseListener(this);
    }

    /**
     * Handles a mouse click event.
     * <p>
     * This method is triggered when the user clicks the mouse and delegates the
     * handling of the event to the current GameStateMethods object.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Handles a mouse pressed event.
     * <p>
     * This method is triggered when the user presses the mouse button. If the
     * source of the event is the 'next' button, it switches the game state to
     * the menu and prints a message indicating the 'Back' button was clicked.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == next) {
            gamePanel.switchState(GameState.MENU);
            System.out.println("Back button clicked");
        }
    }

    /**
     * Handles a mouse release event.
     * <p>
     * This method is triggered when the user releases a mouse button.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Handles a mouse entered event.
     * <p>
     * This method is triggered when the mouse enters the component, and can be
     * used to perform actions such as highlighting a button or displaying a
     * tooltip.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Handles a mouse exited event.
     * <p>
     * This method is triggered when the user moves the mouse out of the
     * component.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
