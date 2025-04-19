package Pets;

import Main.GamePanel;
import Main.GameState;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MenuState extends JPanel implements MouseListener {

    private JButton startButton, resumeButton, instructionsButton, parentalsButton;
    private GamePanel gamePanel;

    private Image backgroundImage;

    public MenuState(GamePanel gp) {
        this.gamePanel = gp;

        this.setLayout(null);

        backgroundImage = new ImageIcon("GUI/Assets/background.png").getImage();
        // this.setBackground(new Color(194, 242, 207));

        startButton = new JButton("Start");
        resumeButton = new JButton("Resume");
        instructionsButton = new JButton("Instructions");
        parentalsButton = new JButton("Parentals");

        Font buttonFont = new Font("Arial", Font.PLAIN, 30);
        startButton.setFont(buttonFont);
        resumeButton.setFont(buttonFont);
        instructionsButton.setFont(buttonFont);
        parentalsButton.setFont(buttonFont);

        startButton.setBounds(650, 150, 200, 100);
        resumeButton.setBounds(650, 300, 200, 100);
        instructionsButton.setBounds(650, 450, 200, 100);
        parentalsButton.setBounds(650, 600, 200, 100);

        startButton.addMouseListener(this);
        resumeButton.addMouseListener(this);
        instructionsButton.addMouseListener(this);
        parentalsButton.addMouseListener(this);

        this.add(startButton);
        this.add(resumeButton);
        this.add(instructionsButton);
        this.add(parentalsButton);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object code = e.getSource();

        if (code == startButton) {
            System.out.println("Start button clicked");
            gamePanel.switchState(GameState.NEWGAME);
        } else if (code == resumeButton) {
            System.out.println("Resume button clicked");
            gamePanel.switchState(GameState.PLAYING);
        } else if (code == instructionsButton) {
            System.out.println("Instructions button clicked");
            gamePanel.switchState(GameState.INSTRUCTIONS);
        } else if (code == parentalsButton) {
            System.out.println("Parentals button clicked");
            gamePanel.switchState(GameState.PARENTLOGIN);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    // Required methods
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}

