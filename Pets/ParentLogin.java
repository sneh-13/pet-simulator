package Pets;

import Main.GamePanel;
import Main.GameState;
import Main.Parent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ParentLogin extends JPanel implements MouseListener {

    private GamePanel gamePanel;
    private Parent parent;

    private JButton parentLogin;
    private JTextField userField;
    private JPasswordField passField;
    private JLabel userLabel, passLabel, failedEntry;
    private Image backgroundImage;

    public ParentLogin(GamePanel gp) {
        this.gamePanel = gp;

        this.setLayout(null);
        this.backgroundImage = new ImageIcon("GUI/Assets/background.png").getImage();
        backgroundImage = new ImageIcon("GUI/Assets/background.png").getImage();
        // this.setBackground(new Color(194, 242, 207));

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

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == parentLogin) {
            System.out.println("Login button pressed.");
            String username = userField.getText();
            String password = new String(passField.getPassword());

            parent = new Parent(username, password, gamePanel.getPlayer());

            if (parent.isAuthenticated()) {
                System.out.println("Succesful login");
                parent.viewPlayerStats();
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }


    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}

