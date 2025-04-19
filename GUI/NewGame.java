package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import Main.GamePanel;
import Main.GameSave;
import Main.GameState;
import Pets.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * Represents the New Game screen where the player can select a pet and name it.
 * <p>
 * The {@code NewGame} class extends {@link JPanel} and provides a user interface for
 * the player to choose a pet (Cat, Dog, or Bunny) and enter a name for it. This screen is displayed 
 * at the beginning of the game when the user starts a new game. The screen contains buttons to select 
 * the pet type, a text field to input the pet's name, and a "Continue" button to proceed to the game.
 * </p>
 * 
 * <p>
 * The class also handles loading pet sprites and background images, setting up the layout of the 
 * components, and handling mouse events for pet selection and navigation. Upon successful pet 
 * selection and naming, the game transitions to the playing state.
 * </p> 
 */
public class NewGame extends JPanel implements MouseListener {

    private JLabel namePetLabel;
    private JTextField namePet;
    private JButton catButton, dogButton, bunnyButton, continueButton;
    private String selectedPetType = null;
    private GamePanel gamePanel;  // reference to parent for state switching
    private BufferedImage backgroundImage;  // Use BufferedImage for background
    private GameSave gameSave;


    /**
     * Constructor for NewGame panel.
     * <p>
     * Initializes the screen, sets up the layout, and loads the background image and pet sprites. 
     * It configures the buttons for pet selection 
     * (Cat, Dog, Bunny), a text field for naming the pet, and the "Continue" button. 
     * Mouse listeners are added to handle user interactions for selecting a pet and proceeding to the next screen.
     * 
     * @param gp reference to parent GamePanel
     */
    public NewGame(GamePanel gp) {
        this.gamePanel = gp;
        this.gameSave = gamePanel.gameSaver;
        this.setLayout(null);
        
        // Load background image as a BufferedImage
        try {
            backgroundImage = ImageIO.read(new File("GUI/Assets/background.png"));
        } catch (IOException e) {
            System.out.println("Failed to load background image.");
            e.printStackTrace();
        }

        
        JLabel titleLabel = new JLabel("Select a Pet", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBounds(0, 20, 1400, 50);
        this.add(titleLabel);

        try {
            //loading our pet sprites and extracting subimages
            BufferedImage catSprite = ImageIO.read(new File("GUI/Assets/Cat/IdleCatt.png")).getSubimage(0, 0, 32, 32);
            BufferedImage dogSprite = ImageIO.read(new File("GUI/Assets/Dog/IdleDog.png")).getSubimage(0, 16, 64, 48);
            BufferedImage bunnySprite = ImageIO.read(new File("GUI/Assets/Bunny/Idle.png")).getSubimage(0, 0, 32, 32);

            //scaling sprites for the buttons
            catButton = new JButton(new ImageIcon(catSprite.getScaledInstance(320, 320, Image.SCALE_SMOOTH)));
            dogButton = new JButton(new ImageIcon(dogSprite.getScaledInstance(320, 320, Image.SCALE_SMOOTH)));
            bunnyButton = new JButton(new ImageIcon(bunnySprite.getScaledInstance(320, 320, Image.SCALE_SMOOTH)));

            catButton.setBounds(150, 100, 320, 320);
            dogButton.setBounds(530, 100, 320, 320);
            bunnyButton.setBounds(950, 100, 320, 320);

            namePet = new JTextField();
            namePet.setBounds(530, 500, 320, 50);

            namePetLabel = new JLabel("Name your pet:", SwingConstants.CENTER);
            namePetLabel.setFont(new Font("Arial", Font.BOLD, 20));
            namePetLabel.setBounds(530, 450, 320, 50);

            continueButton = new JButton("Continue");
            continueButton.setBounds(610, 600, 160, 50);

            catButton.addMouseListener(this);
            dogButton.addMouseListener(this);
            bunnyButton.addMouseListener(this);
            continueButton.addMouseListener(this);

            this.add(catButton);
            this.add(dogButton);
            this.add(bunnyButton);
            this.add(namePet);
            this.add(namePetLabel);
            this.add(continueButton);

        } catch (IOException e) {
            System.out.println("Failed to load sprites.");
            e.printStackTrace();
        }
    }

    /**
     * Paints the background image onto the game panel.
     * <p>
     * This method renders the background image onto the game panel by drawing
     * it onto the Graphics object passed as an argument. It is invoked by the
     * Swing event handling thread.
     * </p>
     * @param g the Graphics object to be used for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Handles mouse press events on the pet selection screen.
     * <p>
     * This method is invoked when a mouse button is pressed on the pet selection
     * screen. It determines which button was pressed and updates the selected pet
     * type accordingly. If the "Continue" button is pressed, it validates the 
     * pet selection and name input. If both are valid, it creates a new player 
     * with the selected pet and switches the game state to PLAYING.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override public void mousePressed(MouseEvent e) {
        Object code = e.getSource();
        if (code == catButton) {
            selectedPetType = "Cat";
        } else if (code == dogButton) {
            selectedPetType = "Dog";
        } else if (code == bunnyButton) {
            selectedPetType = "Bunny";
        } else if (code == continueButton) {
            String petName = namePet.getText();
            if (selectedPetType == null) {
                System.out.println("Please select a pet first.");
            } else if (petName.isEmpty()) {
                System.out.println("Please name your pet.");
            } else {
                Pet chosenPet = null;
                switch (selectedPetType) {
                    case "Cat":
                        chosenPet = new Cat(petName, 10); break;
                    case "Dog":
                        chosenPet = new Dog(petName, 10); break;
                    case "Bunny":
                        chosenPet = new Bunny(petName, 10); break;
                }
                if (chosenPet != null) {
                    gamePanel.createPlayer(petName, chosenPet);   
                    gamePanel.switchState(GameState.PLAYING);
                    System.out.println(gamePanel.getPlayer());
                    try {
                        gameSave.savePlayer(gamePanel.getPlayer(), gamePanel.getPlayer().getActivePet().getName()+"Data.json");
                        System.out.println("Player saved to playerData.json");
                    } catch (IOException ex) {
                        System.out.println("Failed to save player data.");
                        ex.printStackTrace();
                    }
                }
            }
        }
<<<<<<< HEAD
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
        
=======
    }
>>>>>>> c1b2d22095cc4a78d76ad20caa932d6932e50bdc


    /**
     * Handles mouse clicks on the pet selection screen.
     * <p>
     * This method is invoked when the user clicks on the pet selection screen.
     * It does nothing, as the mousePressed method handles all the logic for
     * the screen.
     * </p>
     */
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}


