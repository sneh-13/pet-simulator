package Pets;

import Main.Item;
import Main.ItemType;

/**
 * Represents a Bunny, a concrete subclass of {@link Pet} that features a unique
 * speed boost attribute.
 * <p>
 * The speed boost increases the effectiveness of walking by adding to the
 * health increase when the bunny walks. This class loads its specific
 * inventory, images, and animations from predefined file paths.
 * </p>
 */
public class Bunny extends Pet {

    // Unique instance variable: speedBoost, which increases the effectiveness of walking
    private int speedBoost;

    public Bunny() {
        super();
        this.speedBoost = 0;
        // loadAnimations();
        // loadInventory();
        // loadImages();
    }

    /**
     * Constructs a new Bunny with the specified name and speed boost.
     * <p>
     * This constructor initializes the bunny by calling the superclass
     * constructor, setting the speed boost, and loading its animations,
     * inventory, and images.
     * </p>
     *
     * @param name the name of the bunny.
     * @param speedBoost the additional speed boost to enhance walking
     * effectiveness.
     */
    public Bunny(String name, int speedBoost) {
        super(name);
        this.speedBoost = speedBoost;
        loadAnimations();
        loadInventory();
        loadImages();
    }

    /**
     * initializes animations and images for our Bunny.
     * <p>
     * This method is called during the setup phase to ensure that the dog has
     * all necessary items and visual assets ready for use.
     * </p>
     */
    @Override
    public void setup() {
        loadAnimations();
        loadImages();
    }

    /**
     * Loads the bunny's inventory with predefined items.
     * <p>
     * The inventory is populated with items such as a carrot (food) and a toy.
     * </p>
     */
    private void loadInventory() {
        getInventory().addItem(new Item("carrot", ItemType.FOOD, 10));
        getInventory().addItem(new Item("toy", ItemType.TOY, 10));
    }

    /**
     * Loads the bunny's images from predefined file paths.
     * <p>
     * Images for bunny food, toy, and gift are loaded with specified
     * dimensions.
     * </p>
     */
    private void loadImages() {
        loadImages("Bunnyfood", "GUI/Assets/Bunny/bunnyfood.png", 16, 16);
        loadImages("Bunnytoy", "GUI/Assets/Bunny/bunnytoy.png", 16, 16);
        loadImages("Bunnygift", "GUI/Assets/Bunny/bunnygift.png", 16, 16);
    }

    /**
     * Overrides the {@link Pet#walkPet()} method to add the speed boost effect
     * when the bunny walks.
     * <p>
     * The bunny's health is increased by a base amount plus the speed boost. If
     * the resulting health exceeds {@link Pet#MAX_HEALTH}, it is capped at that
     * maximum.
     * </p>
     */
    @Override
    public void walkPet() {
        health += 10 + speedBoost;
        if (health > MAX_HEALTH) {
            health = MAX_HEALTH;
        }
    }

    /**
     * Loads the bunny's animations from predefined sprite sheets.
     * <p>
     * Animations for sleeping, running, idling, and death are loaded with
     * specified frame counts and dimensions.
     * </p>
     */
    private void loadAnimations() {
        loadAnimation("sleep", "GUI/Assets/Bunny/Sleep.png", 6, 32, 32);
        loadAnimation("run", "GUI/Assets/Bunny/Running.png", 6, 32, 32);
        loadAnimation("idle", "GUI/Assets/Bunny/Idle.png", 12, 32, 32);
        loadAnimation("dead", "GUI/Assets/Bunny/Death.png", 12, 32, 32);
    }

    /**
     * Returns the type of the pet.
     *
     * @return the string "Bunny", indicating the pet type.
     */
    @Override
    public String getType() {
        return "Bunny";
    }

    /**
     * Returns the name of the bunny.
     *
     * @return the name of the bunny.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Retrieves the speed boost value for the bunny.
     *
     * @return the speed boost value.
     */
    public int getSpeedBoost() {
        return speedBoost;
    }
}
