package GUI;

import Main.GamePanel;
import Main.GameState;
import Main.Parent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Represents the parent login screen where the user can enter their username
 * and password.
 * <p>
 * extends {@link JPanel} and provides the user interface for the parent to log
 * in to the game. This screen includes fields for entering a username and
 * password, and a login button. Upon successful authentication, the game
 * transitions to the parent playing state.
 * </p>
 */
public class ParentLogin extends JPanel implements MouseListener {

    private GamePanel gamePanel;
    private Parent parent;

    private JButton parentLogin;
    private JTextField userField;
    private JPasswordField passField;
    private JLabel userLabel, passLabel, failedEntry;
    private Image backgroundImage;

    /**
     * Constructor for the ParentLogin class.
     * <p>
     * Initializes the login screen by setting up the layout, background image,
     * username and password fields, and the login button. The "Login" button is
     * also equipped with a mouse listener to handle login attempts and
     * authenticate the user.
     * </p>
     *
     * <p>
     * The login process is handled through {@link Parent} class, authenticating
     * the credentials. If the authentication fails, an error message is
     * displayed on the screen.
     *
     * @param gp
     */
    public ParentLogin(GamePanel gp) {
        this.gamePanel = gp;

        this.setLayout(null);

        backgroundImage = new ImageIcon("GUI/Assets/background.png").getImage();
        //this.setBackground(new Color(194, 242, 207));

        parentLogin = new JButton("Login");
        userField = new JTextField();
        passField = new JPasswordField();
        userLabel = new JLabel("Username:");
        passLabel = new JLabel("Password:");

        userLabel.setBounds(600, 200, 100, 30);
        userField.setBounds(680, 200, 200, 30);
        passLabel.setBounds(600, 250, 100, 30);
        passField.setBounds(680, 250, 200, 30);

        parentLogin.setBounds(700, 300, 100, 40);
        parentLogin.addMouseListener(this);

        this.add(userLabel);
        this.add(userField);
        this.add(passLabel);
        this.add(passField);
        this.add(parentLogin);
    }

    /**
     * Handles a mouse pressed event.
     * <p>
     * This method is triggered when the user presses the mouse button. If the
     * source of the event is the 'Login' button, it attempts to authenticate
     * the user using the provided username and password. If the authentication
     * is successful, it prints a success message, displays the player's
     * statistics, and switches to the next game state. If the authentication
     * fails, it prints a failure message and displays an error message on the
     * screen.
     * </p>
     *
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == parentLogin) {
            System.out.println("Login button pressed.");
            String username = userField.getText();
            String password = new String(passField.getPassword());

            parent = new Parent(username, password, gamePanel.getPlayer());

            if (parent.isAuthenticated()) {
                System.out.println("Succesful login");
                gamePanel.switchState(GameState.PARENTPLAYING); // or any next screen
            } else {
                System.out.println("Wrong username or password");
                failedEntry = new JLabel("Wrong username or password");
                failedEntry.setForeground(Color.RED);
                failedEntry.setBounds(670, 350, 200, 30);
                this.add(failedEntry);
            }
        }
    }

    /**
     * Paints the parent login screen.
     * <p>
     * This method is responsible for rendering the parent login screen. It
     * draws the background image to fill the entire panel.
     * </p>
     *
     * @param g The Graphics object to be used for rendering.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Handles a mouse clicked event.
     * <p>
     * This method is triggered when the user clicks a mouse button.
     * </p>
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
