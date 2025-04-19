package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles user input and passes it to the current GameStateMethods object.
 * <p>
 * This class implements the KeyListener and MouseListener interfaces to listen for user input.
 * It delegates the handling of key and mouse events to the current GameStateMethods object.
 * </p>
 */
public class InputHandler implements KeyListener, MouseListener {
    
    // private GameStateMethods currState;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean vPressed;
    public boolean fPressed, sPressed, wPressed, gPressed;


    /**
     * Sets the current state of the game for the InputHandler.
     * <p>
     * This state is used to determine which actions are taken when the user inputs a key or mouse event.
     * </p>
     * @param state The GameStateMethods to set the current state as.
     */
    public void setState() {
        // this.currState = state;
    }

    /**
     * Unused method from KeyListener interface.
     * <p>
     * This method is not used in the game as the keyTyped event is not relevant to our game.
     * </p>
     * @param e The KeyEvent triggered by the user's input.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }


    /**
     * Unused method from KeyListener interface.
     * <p>
     * This method is triggered when the user presses a key and is used to set the relevant boolean field to true. 
     * </p>
     * @param e The KeyEvent triggered by the user's input.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        } else if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        } else if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        } else if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        } else if (code == KeyEvent.VK_V) {
            vPressed = true;
        } else if (code == KeyEvent.VK_W) {
            wPressed = true;
        } else if (code == KeyEvent.VK_F) {
            fPressed = true;
        } else if (code == KeyEvent.VK_S) {
            sPressed = true;
        } else if (code == KeyEvent.VK_G){
            gPressed = true;
            System.out.println("G pressed");
        }
    }

    /**
     * Handles the release of a key event.
     * <p>
     * This method is triggered when a key is released and
     * updates the corresponding boolean fields to indicate
     * that the key is no longer pressed.
     * </p>
     * @param e The KeyEvent triggered by the user's input.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        } else if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        } else if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        } else if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        } else if (code == KeyEvent.VK_V) {
            vPressed = false;
        } else if (code == KeyEvent.VK_W) {
            wPressed = false;
        } else if (code == KeyEvent.VK_F) {
            fPressed = false;
        } else if (code == KeyEvent.VK_S) {
            sPressed = false;
        } else if (code == KeyEvent.VK_G){
            gPressed = false;
            System.out.println("G released");
        }
    }

    /**
     * Handles a mouse click event.
     * <p>
     * This method is triggered when the user clicks the mouse and passes the MouseEvent
     * object to the current GameStateMethods object to be handled.
     * </p>
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // currState.mouseClicked(e);
    }

    /**
     * Handles a mouse pressed event.
     * <p>
     * This method is triggered when the user presses a mouse button and
     * delegates the handling of the event to the current GameStateMethods
     * object.
     * </p>
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // currState.mousePressed(e);
    }

    /**
     * Handles a mouse release event.
     * <p>
     * This method is triggered when the user releases a mouse button and
     * delegates the handling of the event to the current GameStateMethods
     * object.
     * </p>
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * Handles a mouse entered event.
     * <p>
     * This method is triggered when the mouse enters the component and
     * delegates the handling of the event to the current GameStateMethods
     * object.
     * </p>
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    /**
     * Handles a mouse exited event.
     * <p>
     * This method is triggered when the mouse exits the component
     * and delegates the handling of the event to the current 
     * GameStateMethods object.
     * </p>
     * @param e The MouseEvent triggered by the user's input.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        
    }

}