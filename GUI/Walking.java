package GUI;

import Main.GamePanel;
import Main.GameState;
import Main.InputHandler;
import Pets.Bunny;
import Pets.Pet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Represents the walking screen where the pet moves around in the game.
 * <p>
 * Extends {@link javax.swing.JPanel} and provides the user interface for the
 * pet's walking state. It allows the player to control the movement of the pet
 * on the screen using the arrow keys, and it renders the pet's animations as it
 * moves.
 * </p>
 *
 * Manages pet movement, collision detection with the environment, and sprite
 * animation. Uses {@link InputHandler} to process keyboard input and update the
 * pet's position accordingly. The walking area is rendered using a grid-based
 * map, with collision detection preventing the pet from moving through solid
 * tiles.
 */
public class Walking extends JPanel implements MouseListener {

    private GamePanel gamePanel;
    private InputHandler IP;

    // Position of the pet
    private int x = 128; // 2 * 64
    private int y = 128; // 2 * 64
    private BufferedImage[] Run;
    private int frameIndex = 0;
    private int frameCounter = 0;

    //movement
    private int scale;
    private int speedBoost;

    //dimensions for the collision box
    private int hitboxWidth = 105;
    private int hitboxHeight = 105;
    private JButton playButton;

    // Map dimensions and tiles
    private int screenWidth = 1512;  // default for Mac 14 inch
    private int screenHeight = 982;  // default for Mac 14 inch
    private int newTileSize = 16 * 4;
    private int tileCols = screenWidth / newTileSize;
    private int tileRows = screenHeight / newTileSize;
    private int[][] tileMap = new int[tileRows][tileCols];
    private BufferedImage redTile, greenTile;

    /**
     * Constructor for the Walking screen.
     * <p>
     * Initializes the walking screen by setting up the layout, loading the map,
     * and configuring the pet's movement speed, animation, and collision
     * detection. It also initializes the "Back to Game" button, allowing the
     * player to return to the game once they are done navigating.
     * </p>
     *
     * <p>
     * Retrieves the active pet from the {@link GamePanel}, loads the
     * appropriate pet sprite based on the pet type (e.g., Cat, Dog, Bunny), and
     * applies any speed boost or scaling factors for the selected pet.
     * </p>
     *
     * @param gp {@link GamePanel} instance used to link this screen to the main
     * game interface and switch game states.
     * @param inputHand {@link InputHandler} instance used to handle keyboard
     * input for controlling the pet's movement.
     */
    public Walking(GamePanel gp, InputHandler inputHand) {
        this.gamePanel = gp;
        this.IP = inputHand;
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocusInWindow();
        loadMap();

        playButton = new JButton("Back to Game");
        playButton.setBounds(100, 100, 100, 50);
        this.add(playButton);
        playButton.addMouseListener(this);

        Pet activePet = gamePanel.getActivePet();

        String type = activePet.getType();

        if (type.equals("Dog")) {
            scale = 2; // Adjust for dog's larger natural size
        } else {
            scale = 4;
        }
        loadSprites();

        // Safely check if it's a Bunny
        if (activePet.getType().equals("Bunny")) {
            speedBoost = ((Bunny) activePet).getSpeedBoost();
        } else {
            speedBoost = 0;
        }
    }

    //each dog sprite is around 48X32
    //each cat sprite is around 24x24
    //each fox sprite is around
    /**
     * Loads the map image and initializes the tile map with the boundaries of
     * the map set to solid tiles (value 1) and all other tiles set to non-solid
     * tiles (value 0). The map is a 2D array of integers, where 0 represents an
     * empty tile and 1 represents a solid tile.
     * <p>
     * The map is divided into four regions: top, bottom, left, and right. The
     * top and bottom regions are two tiles thick, and the left and right
     * regions are two tiles thick. The remaining tiles are set to 0.
     * <p>
     * The purpose of this method is to create a physical boundary for the
     * player to collide with. The solid tiles will prevent the player from
     * moving outside the map.
     */
    public void loadMap() {

        try {
            redTile = ImageIO.read(getClass().getResourceAsStream("/GUI/Assets/tileSet.png")).getSubimage(0 * 16, 9 * 16, 16, 16);
            greenTile = ImageIO.read(getClass().getResourceAsStream("/GUI/Assets/tileSet.png")).getSubimage(2 * 16, 15 * 16, 16, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int y = 0; y < tileRows; y++) {
            for (int x = 0; x < tileCols; x++) {
                tileMap[y][x] = 0;
            }
        }

        int top = 2;
        int bottom = tileRows - 3;
        int left = 2;
        int right = tileCols - 3;

        // Top and bottom borders
        for (int x = left; x <= right; x++) {
            tileMap[top][x] = 1;
            tileMap[top + 1][x] = 1;

            tileMap[bottom][x] = 1;
            tileMap[bottom - 1][x] = 1;
        }

        // Left and right 2-tile-thick vertical borders
        for (int y = top; y <= bottom; y++) {
            tileMap[y][left] = 1;
            tileMap[y][left + 1] = 1;

            tileMap[y][right] = 1;
            tileMap[y][right - 1] = 1;
        }

    }

    /**
     * Determines if a tile type is solid (i.e. the player cannot walk through
     * it).
     * <p>
     * A tile type is solid if it is equal to 0.
     * </p>
     *
     * @param tileType the tile type to check
     * @return true if the tile type is solid, false if it is not
     */
    boolean isSolid(int tileType) {
        if (tileType == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * Checks for a collision between the player's future position, defined by
     * the given coordinates and sprite dimensions, and the solid tiles on the
     * map.
     * <p>
     * The method calculates the hitbox for the sprite and converts its
     * coordinates into map tile indices. It then checks if any of these tiles
     * are solid or if the hitbox goes outside the map bounds, indicating a
     * collision.
     * </p>
     *
     * @param futureX the future x-coordinate of the player.
     * @param futureY the future y-coordinate of the player.
     * @param spriteWidth the width of the sprite.
     * @param spriteHeight the height of the sprite.
     * @return true if there is a collision with a solid tile or the map
     * boundary, false otherwise.
     */
    //this method was generated as i was having issues getting working collision detection by myself
    public boolean isCollision(int futureX, int futureY, int spriteWidth, int spriteHeight) {
        // Center the hitbox inside the sprite.
        int hitboxX = futureX + (spriteWidth - hitboxWidth) / 2;
        int hitboxY = futureY + (spriteHeight - hitboxHeight) / 2;

        // Convert hitbox pixel coordinates into tile indices.
        int leftTile = hitboxX / newTileSize;
        int rightTile = (hitboxX + hitboxWidth - 1) / newTileSize;
        int topTile = hitboxY / newTileSize;
        int bottomTile = (hitboxY + hitboxHeight - 1) / newTileSize;

        // If the hitbox goes outside the map bounds, consider it a collision.
        if (leftTile < 0 || rightTile >= tileCols || topTile < 0 || bottomTile >= tileRows) {
            return true;
        }

        // Check every tile that the hitbox overlaps.
        for (int row = topTile; row <= bottomTile; row++) {
            for (int col = leftTile; col <= rightTile; col++) {
                if (isSolid(tileMap[row][col])) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * Loads the animation frames for the player's active pet's walking
     * animation.
     * <p>
     * Retrieves the animation frames for the "run" animation from the active
     * pet and stores them in the Run array. Prints a message to the console to
     * indicate that the animation frames have been loaded.
     * </p>
     */
    public void loadSprites() {
        // Pet currentPet = gamePanel.player1.getActivePet();
        // String petType = currentPet.getClass().getSimpleName();
        Run = gamePanel.getActivePet().getAnimation("run");
        int spriteWidth = Run[0].getWidth() * scale;
        int spriteHeight = Run[0].getHeight() * scale;
    }

    /**
     * Paints the game screen.
     * <p>
     * This method is responsible for rendering the game world. It calls the
     * draw method to perform the actual rendering.
     * </p>
     *
     * @param g The Graphics object to be used for rendering.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }

    /**
     * Updates the game state.
     * <p>
     * This method is responsible for updating the game state every frame. It
     * updates the frame counter, switches the game state to PLAYING after 300
     * frames, and checks for collisions with the map boundaries. If a collision
     * is detected, it moves the player's pet to the new position. If the player
     * is not pressing any movement keys, it does nothing.
     * </p>
     */
    public void update() {
        frameCounter++;
        if (frameCounter >= 5) {
            frameIndex = (frameIndex + 1) % 5;
            frameCounter = 0;
        }

        if (frameCounter > 300) {
            gamePanel.switchState(GameState.PLAYING);
        }

        int moveSpeed = 5 + speedBoost;
        // // Get scaled dimensions – your Run images are drawn at 4x scale
        int spriteWidth = Run[frameIndex].getWidth() * scale;
        int spriteHeight = Run[frameIndex].getHeight() * scale;

        if (IP.rightPressed && !isCollision(x + moveSpeed, y, spriteWidth, spriteHeight)) {
            x += moveSpeed;

        }
        if (IP.leftPressed && !isCollision(x - moveSpeed, y, spriteWidth, spriteHeight)) {
            x -= moveSpeed;
        }
        if (IP.upPressed && !isCollision(x, y - moveSpeed, spriteWidth, spriteHeight)) {
            y -= moveSpeed;
        }
        if (IP.downPressed && !isCollision(x, y + moveSpeed, spriteWidth, spriteHeight)) {
            y += moveSpeed;
        }

    }

    /**
     * Renders the game world, including the tile map and the player's active
     * pet's animation frame.
     * <p>
     * This method iterates over the tile map and draws each tile at its
     * corresponding position. Red tiles represent non-solid tiles, while green
     * tiles represent solid tiles. If the player's active pet animation frames
     * are available, it draws the current frame at the player's position,
     * scaling the sprite accordingly. If the left movement key is pressed, the
     * sprite is flipped horizontally to indicate leftward movement.
     * </p>
     *
     * @param g The Graphics2D object used for rendering.
     */
    public void draw(Graphics2D g) {

        for (int row = 0; row < tileRows; row++) {
            for (int col = 0; col < tileCols; col++) {
                int tileType = tileMap[row][col];
                BufferedImage tile = (tileType == 0) ? redTile : greenTile;
                g.drawImage(tile, col * newTileSize, row * newTileSize, newTileSize, newTileSize, null);
            }
        }

        if (Run != null && Run.length > 0) {
            int scaledWidth = Run[frameIndex].getWidth() * scale;
            int scaledHeight = Run[frameIndex].getHeight() * scale;
            //this will invert the image so that it looks like we are running to the left
            if (IP.leftPressed) {
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(Run[frameIndex], x + scaledWidth, y, -scaledWidth, scaledHeight, null);
            } else {
                g.drawImage(Run[frameIndex], x, y, scaledWidth, scaledHeight, null);
            }

        }
    }

    /**
     * Handles a mouse click event.
     * <p>
     * This method is triggered when the user clicks the mouse.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Handles a mouse press event.
     * <p>
     * This method is triggered when the user presses a mouse button. If the
     * source of the event is the play button, it switches the game state to
     * PLAYING. Otherwise, it does nothing.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Object code = e.getSource();
        if (code == playButton) {
            gamePanel.switchState(GameState.PLAYING);
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
     * This method is triggered when the mouse enters the component. It can be
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
     * This method is triggered when the mouse exits the component. It can be
     * used to perform actions such as removing a highlight or hiding a tooltip.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

}
