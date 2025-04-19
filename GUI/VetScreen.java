package GUI;

import Main.GamePanel;
import Main.GameState;
import java.awt.*;
import javax.swing.JPanel;

/**
 * Represents the VetScreen in the Virtual Pet Game.
 * <p>
 * The {@code VetScreen} class extends {@link javax.swing.JPanel} and provides
 * the user interface for the player to take their pet to the vet. It simulates
 * the process of taking the pet to the vet with a loading animation while the
 * pet’s health is being restored.
 * </p>
 *
 * <p>
 * The screen includes a loading animation and a message that appears on the
 * screen. Once the animation completes, the game state is switched to the main
 * playing screen.
 * </p>
 */
public class VetScreen extends JPanel {

    private GamePanel gamePanel;
    private int dotCount = 0;
    private int frameCounter = 0;
    private int totalElapsedFrames = 0;
    private boolean done = false;

    /**
     * Constructs the {@code VetScreen} for displaying the pet's vet visit
     * interface.
     * <p>
     * Initializes the vet screen by setting the background color and preparing
     * the panel to handle the drawing and updating of the vet visit process. It
     * links the screen with the {@link GamePanel} to manage state transitions.
     * </p>
     *
     * @param gp {@link GamePanel} instance used to link this screen with the
     * main game interface and to switch game states once the vet visit process
     * is complete.
     */
    public VetScreen(GamePanel gp) {
        this.gamePanel = gp;
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }

    /**
     * Updates the VetScreen by incrementing frame counters and switching to the
     * playing screen when the update is done.
     * <p>
     * This method is responsible for updating the VetScreen by incrementing the
     * frame and total elapsed frame counters, and then switching to the playing
     * screen when the update is done. It also includes logic to increase the
     * pet's health and update the progress bars for health.
     * </p>
     *
     */
    public void update() {
        if (done) {
            return;
        }

        frameCounter++;
        totalElapsedFrames++;

        if (frameCounter >= 30) { // every ~0.5 sec
            dotCount = (dotCount + 1) % 4;
            frameCounter = 0;
        }

        if (totalElapsedFrames >= 200) {
            done = true;
            gamePanel.switchState(GameState.PLAYING);
        }
    }

    /**
     * Resets the VetScreen to its initial state.
     * <p>
     * This method resets the dot count, frame counter, total elapsed frames,
     * and the done flag to prepare the VetScreen for a new vet visit process.
     * Originally was having troubles, as it would pause the game.
     * </p>
     */
    public void reset() {
        this.dotCount = 0;
        this.frameCounter = 0;
        this.totalElapsedFrames = 0;
        this.done = false;
    }

    /**
     * Draws the VetScreen, which displays a message with a loading animation.
     * <p>
     * This method is responsible for rendering the VetScreen by drawing a black
     * rectangle as the background, and then drawing a white message with an
     * increasing number of dots to simulate a loading animation. The message is
     * drawn at the center of the screen, with the dots trailing after the
     * message.
     * </p>
     *
     * @param g The Graphics2D object to use for drawing.
     */
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));

        String message = "Taking to vet" + ".".repeat(dotCount);
        g.drawString(message, 500, 450);
    }

    /**
     * Paints the VetScreen component.
     * <p>
     * This method is responsible for rendering the VetScreen by calling the
     * draw method, which handles the actual drawing logic. It is called by the
     * Swing event handling thread whenever the component needs to be repainted.
     * </p>
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw((Graphics2D) g);
    }
}
