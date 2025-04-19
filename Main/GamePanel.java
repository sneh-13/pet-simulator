package Main;

import GUI.ParentLogin;
import GUI.MenuState;
import GUI.*;
import Pets.*;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.swing.JPanel;

/**
 * GamePanel class represents the main panel of the game.
 * <p>
 * This class extends (@link java.swing.JPanel) and implements (@link
 * java.lang.Runnable) to handle the graphical user interface (GUI) for the
 * game. It handles the game loop, user input, and game state management. It
 * provides methods to switch between different game states. It provides methods
 * to set and get the current game state.
 * </p>
 *
 */
/**
 * GamePanel class represents the main panel of the game.
 * <p>
 * This class extends (@link java.swing.JPanel) and implements (@link
 * java.lang.Runnable) to handle the graphical user interface (GUI) for the
 * game. It handles the game loop, user input, and game state management. It
 * provides methods to switch between different game states. It provides methods
 * to set and get the current game state.
 * </p>
 *
 */
public class GamePanel extends JPanel implements Runnable {

    private CardLayout layout;
    InputHandler IP = new InputHandler();
    public Player player1;
    Thread gameThread;
    GameState currentState;
    MenuState menuState;
    ParentLogin login;
    NewGame newGame;
    private Playing playingState;
    private Walking walkingState;
    private PlayingInstructions playingInstructions;
    private MenuInstructions mInstructions;
    private VetScreen vetScreen;
    private ParentScreen parentScreen;
    public ProgressBars progressBars;
    public GameSave gameSaver;
    public boolean playerLoaded;
    private ResumeScreen resumeScreen;
    private long lastSaveTime = 0;
    private boolean finalSave;
    private static final long saveTime = 1000; // 10 seconds

    /**
     * Constructs a new GamePanel object.
     * <p>
     * The constructor initializes the game panel, sets the layout to
     * {@link java.awt.CardLayout}, and creates the necessary game screens (such
     * as menu, login, new game, and others). It also configures the panel's
     * visual properties and adds the screens to the {@code GamePanel} using
     * {@link java.awt.CardLayout} to manage different game states.
     * </p>
     */
    public GamePanel() {
        this.layout = new CardLayout();
        this.setLayout(layout);
        progressBars = new ProgressBars(100, 100, 100, 100, this);
        this.playerLoaded = false;

        menuState = new MenuState(this);
        login = new ParentLogin(this);
        newGame = new NewGame(this);
        mInstructions = new MenuInstructions(this);
        vetScreen = new VetScreen(this);
        resumeScreen = new ResumeScreen(this);

        //using cardlayout to keep all screens in one panel, these screens don't require dynamic draw method
        this.add(menuState, GameState.MENU.name());
        this.add(login, GameState.PARENTLOGIN.name());
        this.add(newGame, GameState.NEWGAME.name());

        this.add(mInstructions, GameState.INSTRUCTIONS.name());
        this.add(vetScreen, GameState.VETSCREEN.name());
        this.add(resumeScreen, GameState.RESUME.name());

        // this.setPreferredSize(new Dimension(1000,1000));
        // this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(IP);
        this.addMouseListener(IP);
        this.setFocusable(true);

        switchState(GameState.MENU);

    }

    /**
     * Starts the game thread which runs the game loop. This method should only
     * be called once, and is typically called from the main method. The game
     * loop updates and repaints the game panel at a fixed interval using the
     * run() method.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Returns the ProgressBars instance associated with the game panel. This
     * instance is used to display and update the pet's vital statistics such as
     * health, sleep, fullness, and happiness.
     *
     * @return the current ProgressBars instance
     */
    public ProgressBars getProgressBars() {
        return progressBars;
    }

    /**
     * Switches the game state to the given GameStateMethods enum.
     * <p>
     * This method changes the visible panel of the game to the one associated
     * with the given GameStateMethods enum. The game state is updated to the
     * given enum value.
     * </p>
     *
     * @param state the GameStateMethods enum to switch to
     */
    public void switchState(GameState state) {
        this.currentState = state;

        //
        if (state == GameState.PLAYING && playingState != null) {
            playingState.reAddProgressBars();  // <== ADD THIS LINE
        } else if (state == GameState.PARENTPLAYING && parentScreen != null) {
            parentScreen.reAddProgressBars();
        } else if (state == GameState.VETSCREEN) {
            vetScreen.reset();
        }

        layout.show(this, state.name());
    }

    /**
     * Creates a new player with the given pet name and selected pet.
     * <p>
     * This method initializes the player, assigns the selected pet, and sets up
     * the game states for playing, walking, and the parent screen. The newly
     * created game states are added to the panel for display.
     * </p>
     *
     * @param petName the name of the pet associated with the player
     * @param selectedPet the selected pet instance to be assigned to the player
     */
    public void createPlayer(String petName, Pet selectedPet) {

        this.player1 = new Player(petName, selectedPet, this, IP);
        this.playingState = new Playing(this, IP);
        this.walkingState = new Walking(this, IP);
        this.parentScreen = new ParentScreen(this, IP, player1);
        this.playingInstructions = new PlayingInstructions(this);

        GameSave.loadPlayerName(petName);
        this.add(playingState, GameState.PLAYING.name());
        this.add(walkingState, GameState.WALKING.name());
        this.add(parentScreen, GameState.PARENTPLAYING.name());
        this.add(playingInstructions, GameState.PLAYINGINS.name());
        playerLoaded = true;

        this.progressBars = new ProgressBars(getActivePet().getHealth(), getActivePet().getSleep(), getActivePet().getFullness(), getActivePet().getHappiness(), this);
    }

    /**
     * Loads a player from the given Player instance.
     * <p>
     * This method initializes the game states for playing, walking, and the
     * parent screen. It sets up the progress bars and adds the game states to
     * the panel for display. Useful when player wants to resume a previously
     * saved game
     * </p>
     *
     * @param player the Player instance to be loaded
     */
    public void loadPlayer(Player player) {
        this.player1 = player;
        player.getActivePet().setup();
        this.playingState = new Playing(this, IP);
        this.walkingState = new Walking(this, IP);
        this.parentScreen = new ParentScreen(this, IP, player);
        this.playingInstructions = new PlayingInstructions(this);

        this.add(playingState, GameState.PLAYING.name());
        this.add(walkingState, GameState.WALKING.name());
        this.add(parentScreen, GameState.PARENTPLAYING.name());
        this.add(playingInstructions, GameState.PLAYINGINS.name());
        playerLoaded = true;

        this.progressBars = new ProgressBars(
                getActivePet().getHealth(),
                getActivePet().getSleep(),
                getActivePet().getFullness(),
                getActivePet().getHappiness(),
                this
        );

    }

    /**
     * Saves the current game state to a file named "playerNameData.json" using
     * Jackson. This will overwrite any existing file.
     *
     * @throws IOException if the file cannot be written.
     */
    private void saveGame() {
        try {
            GameSave.savePlayer(player1, player1.getName() + "Data.json");
        } catch (IOException e) {
            System.out.println("Auto-save failed.");
            e.printStackTrace();
        }
    }

    /**
     * Returns the InputHandler instance associated with this game panel.
     * <p>
     * This instance is used to handle user input events such as key presses and
     * mouse clicks within the game.
     * </p>
     *
     * @return the current InputHandler instance
     */
    public InputHandler getInputHandler() {
        return IP;
    }

    /**
     * Returns the current GameState of the game.
     * <p>
     * The GameState is used to determine which actions are taken when the user
     * inputs a key or mouse event.
     * </p>
     *
     * @return the current GameState
     */
    public GameState getState() {
        return currentState;
    }

    /**
     * Returns the currently active pet of the player.
     * <p>
     * The active pet is the pet that the player is currently interacting with.
     * </p>
     *
     * @return the currently active pet
     */
    public Pet getActivePet() {
        return player1.getActivePet();
    }

    /**
     * Checks if the player's inventory contains a specified item.
     * <p>
     * This method checks the player's inventory for the presence of the item
     * with the given name. If the item is present, the method returns true,
     * otherwise it returns false.
     * </p>
     *
     * @param name the name of the item to check for in the inventory
     * @return true if the item is found in the inventory, otherwise false
     */
    public boolean hasItem(ItemType type) {
        return getActivePet().hasItem(type);
    }

    /**
     * The main game loop.
     * <p>
     * This method is invoked by the game thread, and continuously updates and
     * repaints the game panel at a rate of 60 frames per second. It also keeps
     * track of time elapsed and the number of frames drawn. This code was
     * largely inspired by RyiSnow java2d tutorials.
     *
     * </p>
     *
     * @see #update()
     * @see #repaint()
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / 60.0;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval; //
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
        }
    }

    /**
     * Updates the current game state based on the active GameState.
     * <p>
     * This method checks the current game state and calls the corresponding
     * update method for that state. It ensures that the appropriate logic and
     * rendering updates occur for the active game state, such as PLAYING,
     * WALKING, or VETSCREEN. Unimplemented states are commented out for future
     * development.
     * </p>
     */
    public void update() {
        if (player1 != null) {
            player1.update();
        }

        switch (currentState) {
            case PLAYING:
                playingState.update();
                break;
            case WALKING:
                walkingState.update();
                break;
            case VETSCREEN:
                vetScreen.update();
                break;
            case PARENTPLAYING:
                parentScreen.update();
                break;
        }

        long now = System.currentTimeMillis();
        if (now - lastSaveTime >= 1000 && playerLoaded) {
            saveGame();
            lastSaveTime = now;
        }

    }

    /**
     * Retrieves the current player instance.
     * <p>
     * This method returns the player object that is currently being managed by
     * the game panel.
     * </p>
     *
     * @return the current player instance
     */
    public Player getPlayer() {
        return player1;
    }

    /**
     * Paints the appropriate game state onto the game panel.
     * <p>
     * This method renders the active game state by calling the draw method of
     * the corresponding game state object. It is responsible for presenting the
     * game world to the user and is invoked by the Swing event handling thread.
     * </p>
     *
     * @param g the graphics object to be used for rendering
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // TEMP: Draw a green background for test
        g2.setColor(Color.GREEN);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (currentState == GameState.WALKING) {
            walkingState.draw(g2);
        } else if (currentState == GameState.PLAYING) {
            playingState.draw(g2);
        } else if (currentState == GameState.VETSCREEN) {
            vetScreen.draw(g2);
        }

        g2.dispose();
    }

}
