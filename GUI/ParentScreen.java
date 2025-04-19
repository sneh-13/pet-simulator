package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import Main.GamePanel;
import Main.GameState;
import Main.InputHandler;
import Main.Item;
import Main.ItemType;
import Main.Player;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 * Represents the Parent Screen of the Virtual Pet Game.
 * <p>
 * This class extends {@link Playing} and implements
 * {@link java.awt.event.MouseListener} to handle user interactions such as
 * setting time limits, reviving the pet, and navigating back to the game.
 * </p>
 *
 * <p>
 * The parent screen displays various game controls, including the total minutes
 * played, options to set hour and day limits, and a button to revive a pet. The
 * parent can interact with the game through these options and view the player's
 * progress.
 * </p>
 *
 */
public class ParentScreen extends Playing implements MouseListener {

    private final Player player;
    private JButton setHourLimitButton;
    private JButton setDayLimitButton;
    private JButton revivePet;
    private JButton backToGame;

    /**
     * Constructor for the ParentScreen class.
     * <p>
     * Initializes the Parent Screen with the provided GamePanel, InputHandler,
     * and Player. Sets up the user interface elements specific to the Parent
     * Screen.
     * </p>
     *
     * @param gp
     * @param ip
     * @param player
     */
    public ParentScreen(GamePanel gp, InputHandler ip, Player player) {
        super(gp, ip);
        this.player = player;
        initParentComponents();
        // super.reAddProgressBars();   
    }

    /**
     * Initializes the components of the Parent Screen.
     * <p>
     * This method sets up the user interface elements specific to the Parent
     * Screen, including labels and buttons for displaying the total play time,
     * setting time limits, reviving a pet, and navigating back to the game.
     * Each component is configured with specific bounds, fonts, colors, and
     * event listeners.
     * </p>
     */
    private void initParentComponents() {
        // Display total minutes played.

        // "Back to Game" button.
        backToGame = new JButton("Back to Game");
        backToGame.setBounds(100, 240, 200, 40);
        backToGame.setFont(new Font("Arial", Font.PLAIN, 18));
        backToGame.setBackground(Color.BLUE);
        backToGame.addMouseListener(this);
        backToGame.setFocusable(false);
        add(backToGame);

        // Revive Pet button: always shown.
        revivePet = new JButton("Revive Pet");
        revivePet.setBounds(100, 300, 200, 40); // Adjust as needed.
        revivePet.setFont(new Font("Arial", Font.PLAIN, 18));
        revivePet.setBackground(Color.BLUE);
        revivePet.setBorderPainted(true);
        revivePet.addMouseListener(this);
        revivePet.setFocusable(false);
        add(revivePet);

        // Button to set hour limit.
        // Create button to set hour limit
        setHourLimitButton = new JButton("Set Hour Limit");
        setHourLimitButton.setBounds(350, 100, 200, 40);
        setHourLimitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        setHourLimitButton.addMouseListener(this);
        setHourLimitButton.setFocusable(false);
        add(setHourLimitButton);

        // Create button to set day limit
        setDayLimitButton = new JButton("Set Day Limit");

        setDayLimitButton.setBounds(850, 100, 200, 40);

        setDayLimitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        setDayLimitButton.addMouseListener(this);
        setDayLimitButton.setFocusable(false);
        add(setDayLimitButton);
    }

    /**
     * Retrieves the total minutes played by the player.
     * <p>
     * This method returns the total game time played by the player in minutes,
     * as tracked by the player instance.
     * </p>
     *
     * @return the total minutes played by the player.
     */
    private double getTotalMinutesPlayed() {
        return player.getGameTimePlayed();
    }

    /**
     * Revives the pet by restoring its health and other attributes.
     * <p>
     * This method restores the pet's health, happiness, fullness, and sleep
     * levels to their maximum values. It also updates the progress bars to
     * reflect these changes and sets the pet's status to alive.
     * </p>
     */
    public void revivePet() {

        progressBars.setHealth(progressBars.max_health);  // Ensure max_health is accessible.

        progressBars.updateProgressBar(progressBars.health_bar, 100, 100, "Health");
        progressBars.updateProgressBar(progressBars.happiness_bar, 100, 100, "Happiness");
        progressBars.updateProgressBar(progressBars.fullness_bar, 100, 100, "Fullness");
        progressBars.updateProgressBar(progressBars.sleep_bar, 100, 100, "Sleep");

        activePet.setFullness(100);
        activePet.setHappiness(100);
        activePet.setHealth(100);
        activePet.setSleep(100);
        activePet.setDead(false);

        // Revive the pet in the persistent Pet object.
        // gamePanel.getActivePet().setHealth(100);
        noUpdate = false;
        progressBars.restartTimer();
        showMessage("Pet Revived!", 2000);
    }

    //no
    /**
     * Updates the parent screen by calling the superclass's update method and
     * updating the displayed total minutes played by the player.
     * <p>
     * It also ensures the progress bars are displayed on this screen.
     * </p>
     */
    @Override
    public void update() {
        System.out.println("Updating ParentScreen - State: " + gamePanel.getState());
        System.out.println("Label visible? " + playedTimeLabel.isVisible());
        System.out.println("Label text: " + playedTimeLabel.getText());
        System.out.println("Parent contains label? " + (playedTimeLabel.getParent() == this));

        super.update();  //playin

        playedTimeLabel.setVisible(true); // Show it only on Parent screen

        if (progressBars.getParent() != this) {
            Container oldParent = progressBars.getParent();
            if (oldParent != null) {
                oldParent.remove(progressBars);
            }
            add(progressBars);
            revalidate();
            repaint();
        }
    }

    /**
     * Returns whether the pet is dead or not.
     * <p>
     * This method simply calls the superclass's isDead method to determine
     * whether the pet is dead. It exists to provide a convenient interface in
     * the parent screen for checking the pet's status.
     * </p>
     *
     * @return true if the pet is dead, false if it is alive.
     */
    public boolean isDead() {
        return super.isDead();
    }

    /**
     * Handles mouse clicks on the parent screen.
     * <p>
     * This method is triggered when the user clicks on any of the buttons on
     * the parent screen. Depending on the source of the event, it will either
     * set a time limit, go back to the game, or revive the pet.
     * </p>
     *
     * @param e the MouseEvent triggered by the user's input.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Object src = e.getSource();
        if (src == setHourLimitButton) {
            String input = javax.swing.JOptionPane.showInputDialog(this, "Enter hour limit:");
            if (input != null && !input.isEmpty()) {
                int limit = Integer.parseInt(input);
                super.setMaxPlayTimeMinutes(limit);
            }
        } else if (src == setDayLimitButton) {
            String input = javax.swing.JOptionPane.showInputDialog(this, "Enter day limit:");
            if (input != null && !input.isEmpty()) {
                System.out.println("Day limit set to: " + input);
            }
        } else if (src == backToGame) {
            gamePanel.switchState(GameState.PLAYING);
        } else if (e.getSource() == revivePet) {
            if (!isDead()) {
                showMessage("Pet is not dead", 2000);
            } else {
                // Delegate the revival to the Playing class's revivePet() method.
                revivePet();
            }
        } else if (e.getSource() == walkPet) {
            gamePanel.switchState(GameState.WALKING);
            activePet.walkPet();
        } else if (e.getSource() == foodButton) {
            Item food = activePet.getInventory().getItem(ItemType.FOOD);
            activePet.feedPet(food.getEffect());
        } else if (e.getSource() == toyButton) {
            Item toy = activePet.getInventory().getItem(ItemType.TOY);
            activePet.playWithPet(toy.getEffect());
        } else if (e.getSource() == sleepButton) {
            activePet.goToBed();
            System.out.println("Putting pet to sleep");
        } else if (e.getSource() == takeVet) {
            System.out.println("Taking pet to vet");
            activePet.takeToVet();
            gamePanel.switchState(GameState.VETSCREEN);
        }

    }

    /**
     * Called when the user clicks on the parent screen.
     * <p>
     * This method is not currently used and is simply a required override from
     * the MouseListener interface.
     * </p>
     *
     * @param e the MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
