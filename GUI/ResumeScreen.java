package GUI;

import Main.GamePanel;
import Main.GameSave;
import Main.Player;
import Pets.Pet;
import Main.GameState;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import javax.swing.JScrollPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;

public class ResumeScreen extends JPanel implements MouseListener {

    private GamePanel gamePanel;
    private List<Player> savedPlayers;
    private JPanel listPanel;
    private Map<Pet, JButton> playerButtons = new HashMap<>(); //using a map to store buttons so we can access which button was pressed through the name
    private JScrollPane scrollPane;
    private Image backgroundImage;

    /**
     * Constructor for the ResumeScreen class.
     * <p>
     * Initializes the Resume Screen with the provided GamePanel. Sets up the
     * layout and loads saved players from files.
     * </p>
     *
     * @param gp reference to parent GamePanel
     */
    public ResumeScreen(GamePanel gp) {
        this.gamePanel = gp;
        // Initialize savedPlayers as an empty list.
        this.savedPlayers = new ArrayList<>();
        this.setLayout(null);
        backgroundImage = new ImageIcon("GUI/Assets/background.png").getImage();

        //loading players from our files
        List<String> filenames = getFilenames();
        System.out.println("filenames" + filenames);
        savedPlayers = loadPlayersFromFiles(filenames);

        int yOffset = 150;
        for (Player p : savedPlayers) {
            Pet pet = p.getActivePet();
            JButton button = new JButton("Pet: " + p.getActivePet().getName() + ", Score: " + p.getScore() + ", Time Played: " + p.getGameTimePlayed());
            button.setBounds(500, yOffset, 400, 40);
            button.addMouseListener(this);
            this.add(button);

            // Store button with player name as key
            playerButtons.put(pet, button);

            yOffset += 45;
        }

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(400, 300));
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Retrieves the filenames of saved players from a text file.
     * <p>
     * This method reads the "players.txt" file line by line, where each line
     * contains the name of a player. It constructs a list of filenames based on
     * the player names, appending "Data.json" to each name.
     * </p>
     *
     * @return a list of filenames for saved players
     */
    private List<String> getFilenames() {
        List<String> filenames = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("players.txt"));
            String petName;
            while ((petName = reader.readLine()) != null) {
                filenames.add(petName + "Data.json");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filenames;
    }

    /**
     * Loads players from files.
     * <p>
     * This method takes a list of filenames, loads each player from the file,
     * and returns a list of Player objects.
     * </p>
     *
     * @param filenames the list of filenames to load players from
     * @return a list of loaded Player objects
     */
    public List<Player> loadPlayersFromFiles(List<String> filenames) {
        List<Player> players = new ArrayList<>();
        for (String filename : filenames) {
            try {
                Player p = GameSave.loadPlayer(filename);
                players.add(p);
            } catch (IOException e) {
                System.out.println("Failed to load player from " + filename);
                e.printStackTrace();
            }
        }

        return players;
    }

    /**
     * Overrides the paintComponent method to perform custom painting on this
     * component. This method is responsible for drawing the background image to
     * fit the size of the component.
     *
     * @param g the Graphics object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();

        for (Pet activePet : playerButtons.keySet()) {
            if (playerButtons.get(activePet) == source) {
                try {

                    Player p = GameSave.loadPlayer(activePet.getName() + "Data.json");
                    p.getActivePet().setup();
                    System.out.println("loaded pet" + activePet.getName());
                    gamePanel.loadPlayer(p);
                    gamePanel.switchState(GameState.PLAYING);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
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
