package Main;

import javax.swing.JFrame;

/**
 * The main class of the software. Initializes the main JFrame for the game.
 * <p>
 * Sets its properties, and adds the GamePanel. Plays background music and
 * starts the game thread.
 * </p>
 */
public class Main {

    /**
     * The entry point of the application. Initializes the main JFrame for the
     * game, sets its properties, and adds the GamePanel. Plays background music
     * and starts the game thread.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pet Game Test - NewGame");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Music music = new Music();
        music.playMusic();

        GamePanel gp = new GamePanel(); // defaults to NEWGAME screen
        frame.add(gp);
        // frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gp.startGameThread(); // optional unless you want game loop behavior
    }
}
