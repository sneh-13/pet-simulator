package Pets;

import Main.Item;
import Main.ItemType;

/**
 * Represents a Dog, a concrete subclass of {@link Pet} that includes additional
 * behavior specific to dogs. This class adds a unique {@code happinessBoost}
 * attribute that increases the dog's happiness gain during interactions.
 */
public class Dog extends Pet {

    // Unique instance variable happinessBoost which increases happiness gains
    private int happinessBoost;

    public Dog() {
        super();
        this.happinessBoost = 0;
        // loadAnimations();
        // loadInventory();
        // loadImages();
    }

    /**
     * Constructs a new Dog with the specified name and happiness boost.
     * <p>
     * This constructor initializes the dog's attributes by calling the
     * superclass constructor, setting the {@code happinessBoost}, and loading
     * the dog's animations, inventory, and images.
     * </p>
     *
     * @param name the name of the dog.
     * @param happinessBoost the additional boost applied to happiness-related
     * actions.
     */
    public Dog(String name, int happinessBoost) {
        super(name);
        this.happinessBoost = happinessBoost;
        loadAnimations();
        loadInventory();
        loadImages();
    }

    //allows us to load all the animations and images needed for the pet
    /**
     * Loads the dog's animations and images.
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
     * Loads the dog's inventory with predefined items.
     * <p>
     * The inventory is populated with items such as dog food, a dog toy,
     * vitamins, and a gift bone.
     * </p>
     */
    private void loadInventory() {
        getInventory().addItem(new Item("dogfood", ItemType.FOOD, 20));
        getInventory().addItem(new Item("dogtoy", ItemType.TOY, 10));
        getInventory().addItem(new Item("dogvitamins", ItemType.MEDICINE, 10));
        getInventory().addItem(new Item("giftbone", ItemType.GIFT, 10));
    }

    /**
     * Increases the dog's happiness when playing with it.
     * <p>
     * The happiness is increased by the effect value plus the happiness boost.
     * If the resulting happiness exceeds {@link Pet#MAX_HAPPINESS}, it is
     * capped at that maximum value.
     * </p>
     *
     * @param effect the effect value from the item used to play with the dog.
     * @return true if the action was successful, false otherwise.
     */
    @Override
    public void playWithPet(int effect) {
        int totalEffect = effect + happinessBoost;
        happiness += totalEffect;
        if (happiness > MAX_HAPPINESS) {
            happiness = MAX_HAPPINESS;
        }
    }

    /**
     * Loads images associated with the dog for various items.
     * <p>
     * Images such as dog food, dog toy, and dog gift are loaded from predefined
     * file paths with specified dimensions.
     * </p>
     */
    private void loadImages() {
        loadImages("Dogfood", "GUI/Assets/Dog/dogfood.png", 16, 16);
        loadImages("Dogtoy", "GUI/Assets/Dog/dogtoy.png", 16, 16);
        loadImages("Doggift", "GUI/Assets/Dog/doggift.png", 16, 16);
    }

    /**
     * Loads animations for the dog corresponding to various actions.
     * <p>
     * Animations such as sleeping, running, idling, and dying are loaded from
     * sprite sheets located at predefined file paths, with specified frame
     * counts and dimensions.
     * </p>
     */
    private void loadAnimations() {
        loadAnimation("sleep", "GUI/Assets/Dog/SleepDog.png", 8, 64, 64);
        loadAnimation("run", "GUI/Assets/Dog/RunDog.png", 5, 64, 64);
        loadAnimation("idle", "GUI/Assets/Dog/IdleDog.png", 7, 64, 64);
        loadAnimation("dead", "GUI/Assets/Dog/DieDog.png", 11, 64, 64);
        // Add more as needed
    }

    /**
     * Returns the name of the dog.
     *
     * @return the name of the dog.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the type of the pet.
     *
     * @return the string "Dog" indicating the pet type.
     */
    @Override
    public String getType() {
        return "Dog";
    }

    /**
     * Simulates walking the dog.
     * <p>
     * The dog's health is increased by a fixed amount plus the happiness boost.
     * If the resulting health exceeds {@link Pet#MAX_HEALTH}, it is capped at
     * that maximum value.
     * </p>
     */
    @Override
    public void walkPet() {
        health += 10 + happinessBoost;
        if (health > MAX_HEALTH) {
            health = MAX_HEALTH;
        }
    }

    /**
     * Retrieves the dog's additional happiness boost.
     *
     * @return the happiness boost value.
     */
    public int getHappinessBoost() {
        return happinessBoost;
    }

}
