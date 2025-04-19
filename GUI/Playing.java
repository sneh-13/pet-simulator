package GUI;

import Main.GamePanel;
import Main.GameState;
import Main.InputHandler;
import Main.ItemType;
import Main.Player;
import Pets.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Represents the Playing screen of the Virtual Pet Game.
 * <p>
 * The {@code Playing} class extends {@link JPanel} and implements
 * {@link java.awt.event.MouseListener} to handle user interactions such as
 * feeding, playing with, and putting the pet to sleep. It also manages the
 * display of the pet's animations and progress bars.
 * </p>
 *
 * <p>
 * The playing screen displays various game controls, including buttons for
 * interacting with the pet, a menu bar for navigation, and progress bars for
 * tracking the pet's health, sleep, fullness, and happiness.Represents the main
 * playing screen of the Virtual Pet Game.
 * <p>
 * The {@code Playing} class extends {@link javax.swing.JPanel} and provides a
 * user interface for the player to interact with their virtual pet. It handles
 * the rendering of the pet's animations, and the player's input to control the
 * pet's movement and interaction with the environment.
 * </p>
 *
 * <p>
 * The class includes animation logic for the pet, as well as logic for the
 * pet's actions and interactions. It also displays the pet's progress through
 * various status indicators, such as hunger and health bars.
 *
 * </p>
 */
public class Playing extends JPanel implements MouseListener {

    private JMenuBar menuBar;
    private JMenuItem insItem, backItem;
    protected InputHandler inputHandler;
    private JLabel invTitle;
    protected JLabel playedTimeLabel; //this will be used for ParentScreen
    protected JButton toyButton, foodButton, walkPet, takeVet, sleepButton, newGame, giftButton;
    protected GamePanel gamePanel;
    private JMenu gameMenu;
    private BufferedImage[] sleep, run, idle, dead;
    private BufferedImage food, toy, gift;
    private int frameIndex = 0;
    private long lastFrameTime = System.currentTimeMillis();
    private final int frameDelay = 1000;
    private BufferedImage dogBone, dogMeat, background;
    private BufferedImage[] anim; //we are goign to use this to store the current animation, depending on the isSleeping boolean
    private Player player;
    private String tempMessage = "";
    private long messageExpireTime = 0;
    private boolean isDog;
    protected Pet activePet;
    private long sleepStartTime;
    private int petWidth, petHeight;
    protected boolean noUpdate = false;
    private int maxPlayTimeMinutes = 10000;

    protected ProgressBars progressBars;
    private PlayerScore playerScore;

    /**
     * Initializes the {@code Playing} object with the specified game panel and
     * input handler. Includes UI components such as buttons for interacting
     * with the pet. It also loads the animations for the pet based on its
     * current state, sets up the progress bars for displaying the pet's status
     * and the scoreboard for tracking the player's progress
     * </p>
     *
     * @param gp
     * @param IP
     */
    public Playing(GamePanel gp, InputHandler IP) {
        this.gamePanel = gp;
        this.player = gamePanel.getPlayer();
        this.activePet = gamePanel.getActivePet();

        this.inputHandler = IP;

        this.setLayout(null);
        this.setBackground(new Color(100, 240, 240));

        menuBar = new JMenuBar();
        gameMenu = new JMenu("Menu");
        insItem = new JMenuItem("Instructions");
        backItem = new JMenuItem("Parental Login");
        gameMenu.add(insItem);
        gameMenu.add(backItem);
        menuBar.add(gameMenu);

        isDog = gamePanel.getActivePet().getType().equals("Dog");
        String animal = gamePanel.getActivePet().getType();

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBounds(0, 0, 1400, 25);
        menuPanel.add(menuBar, BorderLayout.NORTH);
        this.add(menuPanel);

        try {
            sleep = gamePanel.getActivePet().getAnimation("sleep");
            run = gamePanel.getActivePet().getAnimation("run");
            idle = gamePanel.getActivePet().getAnimation("idle");
            dead = gamePanel.getActivePet().getAnimation("dead");
            food = gamePanel.getActivePet().getImage(animal + "food");
            toy = gamePanel.getActivePet().getImage(animal + "toy");
            gift = gamePanel.getActivePet().getImage(animal + "gift");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            background = ImageIO.read(new File("GUI/Assets/backgroundPet.png"));
            // dogBone = ImageIO.read(new File("GUI/Assets/Dog/Bones.png")).getSubimage(0, 0, 16, 16);
            // dogMeat = ImageIO.read(new File("GUI/Assets/Dog/DogMeat.png")).getSubimage(0, 0, 16, 16);

            foodButton = new JButton(new ImageIcon(food.getScaledInstance(48, 48, Image.SCALE_SMOOTH)));
            toyButton = new JButton(new ImageIcon(toy.getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
            giftButton = new JButton(new ImageIcon(gift.getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        invTitle = new JLabel("Inventory");
        invTitle.setFont(new Font("Arial", Font.BOLD, 20));
        invTitle.setBounds(100, 550, 200, 30);
        this.add(invTitle);

        playedTimeLabel = new JLabel("Total Minutes Played: 0");
        playedTimeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playedTimeLabel.setBounds(1200, 60, 300, 30);
        playedTimeLabel.setVisible(false);
        this.add(playedTimeLabel);

        giftButton.setBounds(150, 720, 100, 100);
        this.add(giftButton);
        foodButton.setBounds(100, 600, 100, 100);
        toyButton.setBounds(220, 600, 100, 100);
        this.add(foodButton);
        this.add(toyButton);

        sleepButton = new JButton("Go to Sleep");
        sleepButton.setBounds(100, 360, 200, 40);
        sleepButton.setFont(new Font("Arial", Font.PLAIN, 18));
        sleepButton.setBackground(Color.BLUE);
        sleepButton.setBorderPainted(true);
        this.add(sleepButton);

        takeVet = new JButton("Take to Vet");
        takeVet.setBounds(100, 430, 200, 40); // Positioned just above the inventory title
        takeVet.setFont(new Font("Arial", Font.PLAIN, 18));
        takeVet.setBackground(Color.BLUE);
        takeVet.setBorderPainted(true);
        this.add(takeVet);

        walkPet = new JButton("Walk Pet");
        walkPet.setBounds(100, 500, 200, 40); // Right below takeVet and just above the label
        walkPet.setFont(new Font("Arial", Font.PLAIN, 18));
        walkPet.setBackground(Color.BLUE);
        walkPet.setBorderPainted(true);
        this.add(walkPet);

        newGame = new JButton("New Game");
        newGame.setBounds(600, 100, 200, 40); // Positioned just above the inventory title
        newGame.setFont(new Font("Arial", Font.PLAIN, 18));
        newGame.setBackground(Color.BLUE);
        newGame.setBorderPainted(true);
        this.add(newGame);
        newGame.addMouseListener(this);

        takeVet.addMouseListener(this);
        sleepButton.addMouseListener(this);
        backItem.addMouseListener(this);
        insItem.addMouseListener(this);
        toyButton.addMouseListener(this);
        foodButton.addMouseListener(this);
        walkPet.addMouseListener(this);
        giftButton.addMouseListener(this);

        // Progress Bar 
        this.progressBars = gamePanel.getProgressBars();
        this.progressBars.setBounds(3, 25, 300, 150);

        Container oldParent = progressBars.getParent();
        if (oldParent != null) {
            oldParent.remove(progressBars);
        }

        this.add(progressBars);
        this.progressBars.setBackground(new Color(194, 242, 207));
        this.progressBars.startstat_timer();

        this.revalidate();
        this.repaint();

        //maintains our score in top right corner
        playerScore = new PlayerScore(player);
        playerScore.setBounds(900, 25, 170, 50);
        this.add(playerScore);

    }

    /**
     * This method is called to show a message on the screen for a specified
     * duration.
     * <p>
     * It sets the message to be displayed and calculates the expiration time
     * based on the current system time and the specified duration in
     * milliseconds.
     * </p>
     *
     * @param message The message to be displayed.
     * @param durationMillis The duration in milliseconds for which the message
     * should be displayed. Loads the buttons for the playing screen.
     * <p>
     * This method populates the playing screen with the various buttons and
     * their respective listeners. The buttons include the "Take to Vet", "Go to
     * Sleep", "Walk Pet", "Play", "Feed", "Give Gift", and "New Game" buttons.
     * </p>
     */
    private void loadButtons() {

    }

    /**
     * Displays a temporary on-screen message for a specified duration.
     * <p>
     * This method sets a message to be displayed on the playing screen and
     * specifies how long the message should be visible. The message will
     * automatically disappear after the given duration in milliseconds. The
     * screen is repainted to ensure the message is rendered.
     * </p>
     *
     * @param message the message to be displayed
     * @param durationMillis the duration in milliseconds for which the message
     * should be visible
     */
    protected void showMessage(String message, int durationMillis) {
        tempMessage = message;
        messageExpireTime = System.currentTimeMillis() + durationMillis;
        repaint();
    }

    /**
     * This method is called to update the game state.
     * <p>
     * It handles user input, updates the pet's state, and refreshes the
     * progress bars. It also checks if the player has exceeded the maximum play
     * time limit. Updates the current state of the playing screen.
     * <p>
     * This method handles the transition of the pet's sleep state based on user
     * input. If the 'S' key is pressed, the pet enters the sleep state and the
     * sleep animation begins. The sleep state is maintained for a fixed
     * duration, after which it automatically ends. The method also updates the
     * animation frames and refreshes the player's score display.
     *
     * </p>
     */
    public void update() {
        playedTimeLabel.setVisible(false);

        if (overTimeLimit()) {
            showMessage("Play time limit reached!", 3000);
            noUpdate = true;  //so we can no longeer update due to parent restriction
            return;
        }

        if (noUpdate) {
            return;
        } else {
            long currentTime = System.currentTimeMillis();
            playedTimeLabel.setText("Total Minutes Played: " + player.getGameTimePlayed());
            // System.out.println("PlayedTimeLabel text: " + playedTimeLabel.getText());

            // Handle sleep state changes
            if (inputHandler.sPressed) {
                startSleep(currentTime);
            }

            if (inputHandler.wPressed && !activePet.isSleeping() && !activePet.isDead()) {
                inputHandler.wPressed = false;
                gamePanel.switchState(GameState.WALKING);
                player.walkPet();
            }
            if (inputHandler.vPressed && !activePet.isSleeping() && !activePet.isDead()) {
                inputHandler.vPressed = false;
                if (player.takeToVet()) {
                    gamePanel.switchState(GameState.VETSCREEN);
                } else {
                    showMessage("cooldown active", 2000);
                }
            }

            if (inputHandler.gPressed && !activePet.isSleeping() && !activePet.isDead()) {
                inputHandler.gPressed = false;

                player.giveGift(10);
                showMessage("Gift Used", 2000);
            }

            if (inputHandler.fPressed && !activePet.isSleeping() && !activePet.isDead()) {
                inputHandler.fPressed = false;
                player.feedPet(10);
                showMessage("Pet Fed", 2000);
            }

            updateAnimation(currentTime);
            playerScore.refresh();

        }

    }

    /**
     * Re-adds the progress bars to this panel if they are not already contained
     * in this panel. This is useful when the progress bars are temporarily
     * removed from this panel (e.g. when the player is in the vet screen). This
     * method is idempotent and will not cause any side effects if the progress
     * bars are already in this panel.
     */
    public void reAddProgressBars() {
        if (progressBars.getParent() != this) {
            Container oldParent = progressBars.getParent();
            if (oldParent != null) {
                oldParent.remove(progressBars);
            }
            this.add(progressBars);
            this.revalidate();
            this.repaint();
        }
    }

    /**
     * This method sets the maximum play time limit in minutes.
     *
     * @param minutes The maximum play time limit in minutes.
     *
     */
    public void setMaxPlayTimeMinutes(int minutes) {
        this.maxPlayTimeMinutes = minutes;
    }

    /**
     * This method checks if the player has exceeded the maximum play time
     * limit.
     *
     * @return true if the player has exceeded the maximum play time limit,
     * false otherwise.
     *
     */
    public boolean overTimeLimit() {
        return player.getGameTimePlayed() >= maxPlayTimeMinutes && maxPlayTimeMinutes > 0;
    }

    /**
     * Starts the sleep state for the active pet and sets the animation frame
     * index and time.
     * <p>
     * This method is called when the player presses the 'S' key and sets the
     * sleep flag to true. It also resets the last frame time and frame index to
     * the current time and 0, respectively to ensure that the sleep animation
     * is updated correctly when the game is paused or the state is changed.
     * </p>
     *
     * @param currentTime the current time in milliseconds
     */
    private void startSleep(long currentTime) {
        // player.GoToBed();
        activePet.goToBed();
        sleepStartTime = currentTime;
        frameIndex = 0;
        lastFrameTime = currentTime;
    }

    /**
     * Updates the animation frames for the active pet.
     * <p>
     * This method is called by the update method and updates the animation
     * frames for the active pet. It checks the current state of the pet (dead,
     * sleeping, or idle) and updates the animation frames accordingly. If the
     * animation is not null and has more than one frame, it increments the
     * frame index and updates the last frame time to the current time. It then
     * calls repaint to update the display.
     * </p>
     *
     * @param currentTime the current time in milliseconds
     */
    private void updateAnimation(long currentTime) {
        BufferedImage[] anim;
        if (gamePanel.getActivePet().isDead()) {
            anim = dead;
        } else if (activePet.isSleeping()) {
            anim = sleep;
        } else {
            anim = idle;
        }
        if (anim != null && anim.length > 0 && currentTime - lastFrameTime >= 1000) {
            frameIndex = (frameIndex + 1) % anim.length;
            lastFrameTime = currentTime;
            repaint();
        }
    }

    /**
     * Returns whether the active pet is dead.
     * <p>
     * This method is useful for determining the game state and whether the pet
     * needs to be revived.
     * </p>
     *
     * @return true if the pet is dead, false otherwise.
     */
    public boolean isDead() {
        return activePet.isDead();
    }

    /**
     * Paints the component for the playing screen.
     * <p>
     * This method is responsible for rendering the game's visual elements on
     * the playing screen. It first invokes the superclass's paintComponent
     * method to ensure standard component painting. It then uses a Graphics2D
     * object to draw the current game state, including the pet's animations and
     * any temporary messages regarding item usage or pet status. The message is
     * displayed if the current time is less than the message's expiration time.
     * </p>
     *
     * @param g The Graphics object used for painting the component.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);

        //shows messages if item is used, or if health, hunger, or happiness is low
        if (System.currentTimeMillis() < messageExpireTime) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString(tempMessage, 500, 730);
        }
    }

    /**
     * Draws the game world for the playing screen, including the background and
     * the player's active pet's animation frame.
     * <p>
     * This method draws the background image, and then draws the appropriate
     * animation frame for the player's active pet, either the idle or sleep
     * animation frames, depending on the pet's current state. If the pet is
     * dead, it draws the dead animation frame instead.
     * </p>
     *
     * @param g The Graphics2D object used for rendering.
     */
    public void draw(Graphics2D g) {
        petWidth = isDog ? 64 : 32;
        petHeight = isDog ? 64 : 32;

        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }

        if (!gamePanel.getActivePet().isDead()) {
            BufferedImage[] anim = activePet.isSleeping() ? sleep : idle;
            if (anim != null) {
                //if its not a dog we know thge sprite is 32x32, if it is a dog we know the sprite is 64x64
                if (!isDog) {
                    g.drawImage(anim[frameIndex], 600, 400, 32 * 8, 32 * 8, null);
                } else {
                    g.drawImage(anim[frameIndex], 600, 400, 64 * 4, 64 * 4, null);
                }
            }
        } else {
            //if the pet is dead, we want to show the dead animation. we decided to just draw one of the frames
            g.drawImage(dead[7], 600, 400, 32 * 8, 32 * 8, null);
        }
    }

    /**
     * Handles a mouse click event.
     * <p>
     * This method is triggered when the user clicks the mouse and delegates the
     * handling of the event to the current GameStateMethods object. The method
     * determines which button was clicked and switches the game state
     * accordingly. It also handles the logic for feeding, playing with, and
     * taking the pet to the vet, as well as increasing the pet's health and
     * happiness.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Object code = e.getSource();
        if (code == insItem) {
            gamePanel.switchState(GameState.PLAYINGINS);
        } else if (code == backItem) {
            gamePanel.switchState(GameState.PARENTLOGIN);
        } else if (code == foodButton && canUse()) {
            activePet.useItem(ItemType.FOOD);
            player.feedPet(10);
            showMessage("Feeding Pet", 2000);

        } else if (code == toyButton && canUse()) {
            player.playWithPet(10);
            showMessage("Playing with pet!", 2000);
        } else if (code == walkPet && canUse()) {
            gamePanel.switchState(GameState.WALKING);
            player.walkPet();
            // progressBars.increaseHappiness(10);
        } else if (code == takeVet && canUse()) {
            if (player.takeToVet()) {
                gamePanel.switchState(GameState.VETSCREEN);
            } else {
                showMessage("cooldown active", 2000);
            }
        } else if (code == sleepButton && !isDead()) {
            startSleep(System.currentTimeMillis());
            player.goToBed();
            showMessage("putting Pet to Sleep", 2000);
        } else if (code == newGame) {
            if (isDead()) {
                gamePanel.switchState(GameState.NEWGAME);
            } else {
                showMessage("Pet is not dead, cannot start new game", 2000);
            }
        } else if (code == giftButton && canUse()) {
            player.giveGift(10);
            showMessage("Gift Used", 2000);
        }
    }

    /**
     * Checks if the pet can be used.
     * <p>
     * This method checks if the active pet is not dead and not sleeping, mainly
     * for buttons. if they are clicked we want to make sure pet isn't sleeping
     * or dead of course.
     * </p>
     *
     * @return true if the pet can be used, false otherwise.
     */
    private boolean canUse() {
        return !activePet.isDead() && !activePet.isSleeping();
    }

    /* Method from MouseListener interface
     * <p>
     * This method is triggered when the user clicks the mouse button but is not
    * used in the Playing state.
    * </p>
    * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Method from MouseListener interface.
     * <p>
     * This method is triggered when the user releases a mouse button but is not
     * used in the Playing state.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Method from MouseListener interface.
     * <p>
     * This method is triggered when the mouse pointer enters the component's
     * bounds but is not used in the Playing state.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Method from MouseListener interface.
     * <p>
     * This method is triggered when the mouse pointer exits the component's
     * bounds but is not used in the Playing state.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
