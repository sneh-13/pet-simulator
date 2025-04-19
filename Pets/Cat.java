package Pets;

import Main.Item;
import Main.ItemType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Cat, a concrete subclass of {@link Pet} that includes behavior
 * specific to cats. This class adds a sleep boost attribute which can be used
 * to enhance the cat's sleep behavior.
 * <p>
 * It initializes the cat's animations, inventory, and images from predefined
 * file paths.
 * </p>
 */
public class Cat extends Pet {

    @JsonProperty
    private int sleepBoost;

    public Cat() {
        super();
        this.sleepBoost = 0;
        // loadAnimations();
        // loadInventory();
        // loadImages();
    }

    /**
     * Constructs a new Cat with the specified name and sleep boost.
     * <p>
     * This constructor calls the superclass constructor to initialize basic pet
     * attributes, sets the sleep boost, and loads animations, inventory, and
     * images specific to the cat.
     * </p>
     *
     * @param name the name of the cat.
     * @param sleepBoost the additional boost applied to sleep-related actions.
     */
    public Cat(String name, int sleepBoost) {
        super(name);
        this.sleepBoost = sleepBoost;
        loadAnimations();
        loadInventory();
        loadImages();
    }

    /**
     * Loads the cat's animations and images.
     * <p>
     * This method is called during the setup phase to ensure that the dog has
     * all necessary items and visual assets ready for use.
     * </p>
     */
    @Override
    public void setup() {
        loadAnimations();
        loadImages();
        System.out.println("Trying");
    }

    /**
     * Loads the cat's animations from predefined sprite sheets.
     * <p>
     * Each animation is loaded with a specified action name, file path, number
     * of frames, and frame dimensions. Cat sprites are assumed to be 32x32
     * pixels.
     * </p>
     */
    private void loadAnimations() {
        loadAnimation("sleep", "GUI/Assets/Cat/SleepCatt.png", 3, 32, 32);
        loadAnimation("run", "GUI/Assets/Cat/RunCatt.png", 7, 32, 32);
        loadAnimation("idle", "GUI/Assets/Cat/IdleCatt.png", 7, 32, 32);
        loadAnimation("dead", "GUI/Assets/Cat/DieCatt.png", 15, 32, 32);
        System.out.println("loading");

    }

    /**
     * Loads the cat's images from predefined file paths.
     * <p>
     * Images for cat food, cat toy, and cat gift are loaded with specified
     * dimensions.
     * </p>
     */
    private void loadImages() {
        loadImages("Catfood", "GUI/Assets/Cat/catfood.png", 32, 32);
        loadImages("Cattoy", "GUI/Assets/Cat/cattoy.png", 32, 32);
        loadImages("Catgift", "GUI/Assets/Cat/catgift.png", 16, 16);
    }

    /**
     * Loads the cat's inventory with predefined items.
     * <p>
     * Items such as cat food, cat toy, and cat gift are added to the cat's
     * inventory.
     * </p>
     */
    private void loadInventory() {
        getInventory().addItem(new Item("catfood", ItemType.FOOD, 15));
        getInventory().addItem(new Item("cattoy", ItemType.TOY, 10));
        getInventory().addItem(new Item("catgift", ItemType.GIFT, 10));
    }

    /**
     * Retrieves the name of the cat.
     *
     * @return the name of the cat as a {@code String}.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the type of the pet.
     *
     * @return the string "Cat", indicating the pet type.
     */
    @Override
    public String getType() {
        return "Cat";
    }

    /**
     * Retrieves the sleep boost value for the cat.
     *
     * @return the sleep boost value.
     */
    public int getSleepBoost() {
        return sleepBoost;
    }
}
