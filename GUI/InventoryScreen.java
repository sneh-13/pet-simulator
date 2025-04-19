package GUI;

import java.awt.Color;
import java.awt.*;
import Main.GamePanel;

import javax.swing.*;

/**
 * Represents the Inventory Screen of the game.
 * <p>
 * Responsible for displaying the inventory interface in the game. 
 * It initializes the layout, sets the background color, and provides interactive buttons 
 * and labels for the player to manage their in-game items (such as food and toys).
 * Takes a {@link GamePanel} as a parameter, allowing it to access methods and 
 * interact with the game state
 * </p>
 */

public class InventoryScreen extends JPanel {

    private GamePanel gamePanel;
    private JButton Meat1, Toy1;
    private JLabel MeatLabel, ToyLabel;
    
    /**
     * Constructor for the Inventory Screen
     * <p>
     * Initializes the Inventory Screen (layout and background color)
     * </p>
     * @param gp
     */
    public InventoryScreen(GamePanel gp){
        this.gamePanel = gp;

        this.setLayout(null);
        this.setBackground(new Color(194, 242, 207));

    }
}