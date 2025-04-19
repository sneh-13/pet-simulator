package Main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import java.io.File;
import Main.Player;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;

/**
 * The {@code GameSave} class provides utility methods for saving and loading
 * {@code Player} objects to and from JSON files, as well as appending player
 * names to a text file. It uses the Jackson library for JSON serialization and
 * deserialization.
 *
 * <p>
 * This class includes the following functionalities:
 * <ul>
 * <li>Saving a {@code Player} object to a JSON file.</li>
 * <li>Loading a {@code Player} object from a JSON file.</li>
 * <li>Appending player names to a text file.</li>
 * </ul>
 *
 * <p>
 * The {@code ObjectMapper} instance is configured to enable pretty-printing and
 * to allow visibility of all fields during serialization and deserialization.
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * Player player = new Player("John", 100);
 * GameSave.savePlayer(player, "player.json");
 * Player loadedPlayer = GameSave.loadPlayer("player.json");
 * GameSave.loadPlayerName("John");
 * }</pre>
 *
 * <p>
 * Note: Ensure that the {@code Player} class is properly annotated or
 * structured to work with Jackson's serialization and deserialization
 * mechanisms.
 *
 * @see com.fasterxml.jackson.databind.ObjectMapper
 * @see com.fasterxml.jackson.annotation.JsonAutoDetect
 * @see com.fasterxml.jackson.databind.SerializationFeature This class is used
 * to save and load player data from a JSON file.
 * <p>
 * The class uses the Jackson library to serialize and deserialize player
 * objects to/from JSON format and store them in a file.
 * </p>
 */
public class GameSave {

    // Create a single ObjectMapper instance.
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    /**
     * Saves a player to a JSON file using the filename provided.
     * <p>
     * This method uses the Jackson library to write the player object to a
     * file.
     * </p>
     *
     * @param player the player object to be saved
     * @param filename the name of the file to write to
     * @throws IOException if the file could not be written to
     */
    public static void savePlayer(Player player, String filename) throws IOException {
        mapper.writeValue(new File(filename), player);
    }

    /**
     * Appends a player name to the end of a file named "players.txt". This file
     * is used to store all the player names that have been created.
     * <p>
     * The file is in the same directory as the GameSave class.
     * </p>
     *
     * @param name the name of the player to be appended to the file.
     * @throws IOException if an I/O error occurs.
     */
    public static void loadPlayerName(String name) {
        try {
            // Passing true to FileWriter so it doesn't overwrite and will append instead
            FileWriter fileWriter = new FileWriter("players.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(name);
            bufferedWriter.newLine(); // Adds a line break

            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Successfully appended to the file.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a player from a JSON file created by the savePlayer method.
     * <p>
     * This method reads from a file and attempts to parse the JSON data into a
     * Player object. If the file does not exist or cannot be read, or if the
     * JSON data is malformed, an IOException is thrown.
     * </p>
     *
     * @param filename the name of the file to read from
     * @return the Player object loaded from the file
     * @throws IOException if the file could not be read
     *
     */
    public static Player loadPlayer(String filename) throws IOException {
        System.out.println("Trying to load from: " + new File(filename).getAbsolutePath());
        return mapper.readValue(new File(filename), Player.class);
    }

    /**
     * Saves a list of Player objects to a JSON file.
     *
     * @param players The list of Player objects to be saved.
     * @param filename The name of the file where the list will be saved.
     * @throws IOException If an I/O error occurs during the saving process.
     */
    public static void savePlayers(List<Player> players, String filename) throws IOException {
        mapper.writeValue(new File(filename), players);
    }

    /**
     * Loads a list of Player objects from a JSON file.
     *
     * @param filename The name of the file from which the list will be loaded.
     * @return The loaded list of Player objects.
     * @throws IOException If an I/O error occurs during the loading process.
     */
    public static List<Player> loadPlayers(String filename) throws IOException {
        return mapper.readValue(new File(filename),
                mapper.getTypeFactory().constructCollectionType(List.class, Player.class));
    }
}
