package GUI;

import Main.GamePanel;
import Main.GameState;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The MenuState class represents the menu screen of the game.
 * <p>
 * Extends {@link JPanel} and provides the user interface 
 * for the main menu of the game. This menu includes options like "Start", "Resume", and "Instructions" 
 * that the player can click to navigate to different game states.
 * </p>
 * 
 * <p>
 * implements {@link MouseListener} to handle mouse events for the buttons.
 * The background image and button labels are set up in the constructor, and the button actions 
 * are linked to the appropriate game functions.
 * </p>
 */
public class MenuState extends JPanel implements MouseListener {

    private JButton startButton, resumeButton, instructionsButton;
    private GamePanel gamePanel;
    private Image backgroundImage;
    
    /**
     * Constructor for the MenuState.
     * <p>
     * Initializes the menu screen by setting up the layout, background image, and buttons.
     * Sets their positions, font styles, and adds mouse listeners to handle user clicks. The background 
     * image for the menu screen is loaded and applied to the panel.
     * </p>
     * @param gp
     */
    public MenuState(GamePanel gp) {
        this.gamePanel = gp;
        
        this.setLayout(null);
        
        backgroundImage = new ImageIcon("GUI/Assets/background.png").getImage();
        // this.setBackground(new Color(194, 242, 207));

        startButton = new JButton("Start");
        resumeButton = new JButton("Resume");
        instructionsButton = new JButton("Instructions");

        Font buttonFont = new Font("Arial", Font.PLAIN, 30);
        startButton.setFont(buttonFont);
        resumeButton.setFont(buttonFont);
        instructionsButton.setFont(buttonFont);

        startButton.setBounds(650, 150, 200, 100);
        resumeButton.setBounds(650, 300, 200, 100);
        instructionsButton.setBounds(650, 450, 200, 100);

        startButton.addMouseListener(this);
        resumeButton.addMouseListener(this);
        instructionsButton.addMouseListener(this);


        this.add(startButton);
        this.add(resumeButton);
        this.add(instructionsButton);
    }

    /**
     * Handles a mouse click event.
     * <p>
     * This method is triggered when the user clicks the mouse and
     * delegates the handling of the event to the current GameStateMethods
     * object. The method determines which button was clicked and switches
     * the game state accordingly.
     * </p>
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Object code = e.getSource();

        if (code == startButton) {
            gamePanel.switchState(GameState.NEWGAME);
        } else if (code == resumeButton) {
            gamePanel.switchState(GameState.RESUME);
        } else if (code == instructionsButton) {
            System.out.println("Instructions button clicked");
            gamePanel.switchState(GameState.INSTRUCTIONS);
        } 
    }

    /**
     * Paints the game menu screen.
     * <p>
     * This method is responsible for rendering the game menu screen. It
     * draws the background image to fill the entire panel.
     * </p>
     * @param g The Graphics object to be used for rendering.
     */
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
    
    /**
     * Handles a mouse click event.
     * <p>
     * This method is a dummy implementation of the MouseListener
     * interface and does nothing.
     * </p>
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
