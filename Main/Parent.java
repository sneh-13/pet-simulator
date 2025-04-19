package Main;

/**
 * The Parent class represents a user with authentication and play time tracking
 * capabilities.
 * <p>
 * The class has methods to authenticate the user, record play time, get total
 * minutes played, and set total minutes manually. It contains a flag to
 * indicate if the user is locked or not (parental controls). It also has a
 * reference to the player being monitored.
 * <p>
 */
public class Parent {

    private String name;
    private String password;
    private boolean isLocked;
    private long totalMinutes = 0;  // Changed from totalHours

    private Player player; // The player being monitored

    /**
     * Constructor for the Parent class.
     *
     * @param username the username entered
     * @param password the password entered
     * @param player the player object to monitor
     */
    public Parent(String username, String password, Player player) {
        String expectedName = "parent";
        String expectedPassword = "enter";

        if (username.equals(expectedName) && password.equals(expectedPassword)) {
            this.name = username;
            this.password = password;
            this.isLocked = false;
            this.player = player;
            System.out.println("Login successful!");
        } else {
            this.isLocked = true;
            System.out.println("Invalid credentials.");
        }
    }

    /**
     * Checks if the parent is authenticated.
     *
     * @return {@code true} if the parent is authenticated; {@code false}
     * otherwise.
     */
    public boolean isAuthenticated() {
        return !isLocked;
    }

    /**
     * Records the play time in minutes if valid.
     *
     * @param start the session start time in milliseconds
     * @param end the session end time in milliseconds
     */
    public void setPlayTime(Long start, Long end) {
        if (start != null && end != null && end > start) {
            long sessionMinutes = (end - start) / 60000;
            this.totalMinutes += sessionMinutes;
            System.out.println("Play time recorded: " + sessionMinutes + " minutes.");
        } else {
            System.out.println("Invalid time values provided.");
        }
    }

    /**
     * Prints total minutes played.
     */
    public double getTotalMinutes() {
        return player.getGameTimePlayed();
    }

    /**
     * Manually sets the total minutes played.
     *
     * @param totalMinutes the total minutes value
     */
    public void setTotalMinutes(Long totalMinutes) {
        if (totalMinutes != null && totalMinutes >= 0) {
            this.totalMinutes = totalMinutes;
            System.out.println("Total minutes manually set to: " + totalMinutes);
        } else {
            System.out.println("Invalid total minutes value.");
        }
    }

    /**
     * Authenticates a user by verifying the provided name and password against
     * the stored name and password.
     *
     * @param name The name to authenticate.
     * @param password The password to authenticate.
     * @return {@code true} if the provided name and password match the stored
     * name and password; {@code false} otherwise.
     */
    public boolean authenticate(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }
}
