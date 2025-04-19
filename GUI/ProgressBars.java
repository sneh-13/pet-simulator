package GUI;

import Main.GamePanel;
import Main.GameState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.plaf.basic.BasicProgressBarUI;
import Main.*;
import Pets.*;

/**
 * ProgressBars panel displays and updates the vital statistics for the pet:
 * Health, Sleep, Fullness, Happiness.ProgressBars panel displays and updates
 * the vital statistics for the pet: Health, Sleep, Fullness, Happiness.
 * <p>
 * This class is responsible for initializing and updating the progress bars for
 * the vital statistics of the pet. Provides a visual representation of the
 * pet's current status using {@link JPanel} and {@link JProgressBar}. The pet's
 * vital stats are displayed in the form of progress bars, which are updated
 * periodically based on the pet's condition. These stats can be updated
 * automatically through a timer, and the progress bars' colors change based on
 * certain thresholds.
 * </p>
 *
 * <p>
 * It also handles the timer to update the vital statistics every 500
 * milliseconds. The timer is started when the game starts and stopped when the
 * game ends.
 * </p>
 *
 *
 */
public class ProgressBars extends JPanel {

    public JProgressBar health_bar;
    public JProgressBar sleep_bar;
    public JProgressBar fullness_bar;
    public JProgressBar happiness_bar;
    private GameState state;

    public int max_health;
    public int max_sleep;
    public int max_fullness;
    public int max_happiness;

    public int current_health;
    public int current_sleep;
    public int current_fullness;
    public int current_happiness;

    // Decline rates per timer tick 
    private int sleep_decline_rate = 1;
    private int fullness_decline_rate = 1;
    private int happiness_decline_rate = 1;

    private String health, sleep, full, happy;
    private boolean isAngry = false;
    private GamePanel gamePanel;
    private Pet activePet;

    // Timer to update stats every second 
    private Timer stat_timer;

    /**
     * Constructs the {@code ProgressBars} panel that displays and updates the
     * pet's vital statistics (health, sleep, fullness, happiness).
     * <p>
     * Initializes the progress bars with the specified maximum values for each
     * stat (health, sleep, fullness, happiness). It sets the initial values for
     * these stats to their maximum values and sets up the layout for displaying
     * the progress bars. Each progress bar is also initialized with its
     * respective maximum and current values.
     * </p>
     *
     * @param max_health
     * @param max_sleep
     * @param max_fullness
     * @param max_happiness
     * @param gamePanel
     */
    public ProgressBars(int max_health, int max_sleep, int max_fullness, int max_happiness, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.max_health = max_health;
        this.max_sleep = max_sleep;
        this.max_fullness = max_fullness;
        this.max_happiness = max_happiness;

        // Initialize all stats to maximum
        this.current_health = max_health;
        this.current_sleep = max_sleep;
        this.current_fullness = max_fullness;
        this.current_happiness = max_happiness;

        setLayout(new GridLayout(4, 1, 5, 5));
        initProgressBars();

    }

    /**
     * Initializes the progress bars for health, sleep, fullness, and happiness.
     * <p>
     * Sets the UI for each progress bar and adds them to the panel.
     *
     * This method sets up four JProgressBars corresponding to the pet's vital
     * statistics: health, sleep, fullness, and happiness. Each bar is
     * initialized with a maximum value, a current value, and a descriptive
     * label. The progress bars are configured to use a basic UI and display
     * their string representations.
     * </p>
     */
    private void initProgressBars() {
        // Health bar
        health_bar = new JProgressBar(0, max_health);
        health_bar.setUI(new BasicProgressBarUI()); // force basic UI
        health_bar.setValue(current_health);
        health_bar.setString("Health: " + current_health + "/" + max_health);
        health_bar.setStringPainted(true);
        // Sleep bar
        sleep_bar = new JProgressBar(0, max_sleep);
        sleep_bar.setUI(new BasicProgressBarUI());
        sleep_bar.setValue(current_sleep);
        sleep_bar.setString("Sleep: " + current_sleep + "/" + max_sleep);
        sleep_bar.setStringPainted(true);

        // Fullness bar
        fullness_bar = new JProgressBar(0, max_fullness);
        fullness_bar.setUI(new BasicProgressBarUI());
        fullness_bar.setValue(current_fullness);
        fullness_bar.setString("Fullness: " + current_fullness + "/" + max_fullness);
        fullness_bar.setStringPainted(true);

        // Happiness bar
        happiness_bar = new JProgressBar(0, max_happiness);
        happiness_bar.setUI(new BasicProgressBarUI());
        happiness_bar.setValue(current_happiness);
        happiness_bar.setString("Happiness: " + current_happiness + "/" + max_happiness);
        happiness_bar.setStringPainted(true);

        add(health_bar);
        add(sleep_bar);
        add(fullness_bar);
        add(happiness_bar);
    }

    public void startstat_timer() {
        stat_timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStats();
            }
        });
        stat_timer.start();
    }

    /**
     * Restarts the timer if it is not already running.
     * <p>
     * This method checks if the timer is not running and starts it if it is
     * not.
     * </p>
     */
    public void restartTimer() {
        if (!stat_timer.isRunning()) {
            stat_timer.start();
        }
    }

    public void setHealth(int value) {
        current_health = value;
        updateProgressBar(health_bar, current_health, max_health, "Health");
        // gamePanel.getPlayer().getActivePet().setHealth(value);
    }

    /**
     * Updates each stat every timer tick. Sleep, Fullnessm, Happiness, stats
     * decline over time. Special conditions are triggered when stats reach
     * critical levels.
     */
    private void updateStats() {

        // Update all progress bars
        updateProgressBar(health_bar, gamePanel.getActivePet().getHealth(), max_health, "Health");
        updateProgressBar(sleep_bar, gamePanel.getActivePet().getSleep(), max_sleep, "Sleep");
        updateProgressBar(fullness_bar, gamePanel.getActivePet().getFullness(), max_fullness, "Fullness");
        updateProgressBar(happiness_bar, gamePanel.getActivePet().getHappiness(), max_happiness, "Happiness");

        // If health is 0, the pet dies.
        if (current_health <= 0) {
            stat_timer.stop();
        }
    }

    public int getHealth() {
        return current_health;
    }

    public int getSleep() {
        return current_sleep;
    }

    public int getFullness() {
        return current_fullness;
    }

    public int getHappiness() {
        return current_happiness;
    }

    /**
     * Updates the given JProgressBar with the new value and applies a warning
     * color based on thresholds.
     * <p>
     * This method sets the maximum value of the progress bar, sets the current
     * value, and sets the string displayed on the progress bar. It also sets
     * the foreground color of the progress bar based on the following
     * thresholds:
     * <ul>
     * <li>Below 25% of the maximum value: red</li>
     * <li>Below 50% of the maximum value but at least 25%: yellow</li>
     * <li>Otherwise: green (or the default color)</li>
     * </ul>
     * </p>
     *
     * @param bar The JProgressBar to update.
     * @param currentValue The current value of the stat.
     * @param maxValue The maximum value of the stat.
     * @param label The text to display on progress bar.
     */
    public void updateProgressBar(JProgressBar bar, int currentValue, int maxValue, String label) {
        bar.setMaximum(maxValue);
        bar.setValue(currentValue);
        bar.setString(label + ": " + currentValue + "/" + maxValue);

        // Apply colors based on thresholds:
        // If below 25% of max, set color to red; 
        // if below 50% (but at least 25%), set color to yellow;
        // otherwise, set color to green (or your default color).
        if (currentValue < 0.25 * maxValue) {
            bar.setForeground(Color.RED);
            // System.out.println("red");
        } else if (currentValue < 0.5 * maxValue) {
            bar.setForeground(Color.YELLOW);
            // System.out.println("yellow");
        } else {
            bar.setForeground(Color.GREEN);
            // System.out.println("green");
        }
    }
}
