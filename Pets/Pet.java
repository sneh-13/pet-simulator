package Pets;

import Main.Inventory;
import Main.ItemType;
import Main.Item;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "petType"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Bunny.class, name = "Bunny"),
    @JsonSubTypes.Type(value = Cat.class, name = "Cat"),
    @JsonSubTypes.Type(value = Dog.class, name = "Dog")
})
/**
 * Abstract class representing a pet with various status attributes such as
 * health, sleep, fullness, and happiness. It provides methods to interact with
 * and manage the pet, including feeding, playing, walking, giving gifts,
 * visiting the vet, and sleeping. The class also supports loading and
 * retrieving images and animations for use in graphical interfaces.
 */
public abstract class Pet {
    // Pet status attributes

    protected int health, sleep, fullness, happiness;
    protected String name;

    protected static final int MAX_HEALTH = 100;
    protected static final int MAX_SLEEP = 100;
    protected static final int MAX_FULLNESS = 100;
    protected static final int MAX_HAPPINESS = 100;

    public boolean isDead, isSleeping, isHungry, isAngry;

    @JsonIgnore
    private Inventory inv;
    @JsonIgnore
    protected long lastVetTime, lastPlayTime;
    @JsonIgnore
    public static final long VET_COOLDOWN_MS = 10000;
    @JsonIgnore
    public static final long PLAY_COOLDOWN_MS = 2500;
    @JsonIgnore
    protected Map<String, BufferedImage[]> animations = new HashMap<>();
    @JsonIgnore
    protected Map<String, BufferedImage> images = new HashMap<>();

    /**
     * Default constructor for the Pet class. Initializes the pet's name to an
     * empty string and sets all status attributes to their maximum values.
     * Creates a new inventory with a capacity of 100 and sets initial values
     * for animation and timing. Used for Jackson package. x
     */
    public Pet() {
        this("");
    }

    /**
     * Constructs a new pet with the specified name. Initializes the pet's
     * attributes to their maximum values, creates a new inventory with a
     * capacity of 100, and sets initial values for animation and timing.
     *
     * @param name the name of the pet.
     */
    public Pet(String name) {
        this.name = name;
        this.health = MAX_HEALTH;
        this.sleep = MAX_SLEEP;
        this.fullness = MAX_FULLNESS;
        this.happiness = MAX_HAPPINESS;
        this.inv = new Inventory(100);
        this.lastVetTime = 0;
        this.lastPlayTime = 0;
    }

    /**
     * Feeds the pet by increasing its fullness by the specified effect. The pet
     * will only be fed if it is not dead, sleeping, or angry.
     *
     * @param effect the amount to increase the pet's fullness.
     */
    public void feedPet(int effect) {
        if (!isDead && !isSleeping && !isAngry) {
            fullness = Math.min(MAX_FULLNESS, fullness + effect);
            inv.useItem(ItemType.FOOD);
            System.out.println(name + " has been fed. Fullness: " + fullness);
        }
    }

    /**
     * Retrieves the pet's inventory.
     *
     * @return the items in the pets inventory
     */
    public Inventory getInventory() {
        return inv;
    }

    /**
     * Retrieves the animation frames for a given action.
     *
     * @param action the key representing the desired animation.
     * @return an array of {@link BufferedImage} objects representing the
     * animation frames, or {@code null} if the animation is not found.
     */
    public BufferedImage[] getAnimation(String action) {
        if (!animations.containsKey(action)) {
            System.out.println("Animation not found: " + action);
            return null;
        }
        return animations.get(action);
    }

    /**
     * Retrieves the image associated with a specific item.
     *
     * @param item the key representing the image.
     * @return the {@link BufferedImage} corresponding to the item, or
     * {@code null} if the image is not found.
     */
    public BufferedImage getImage(String Item) {
        if (!images.containsKey(Item)) {
            System.out.println("Image not found: " + Item);
            return null;
        }
        return images.get(Item);
    }

    /**
     * Loads an image from the specified file path and stores it in the images
     * map with the given key. If the provided width and height are valid and
     * within the image bounds, a subimage is extracted.
     *
     * @param item the key to associate with the loaded image.
     * @param path the file path of the image.
     * @param width the width for subimage extraction; if invalid, the full
     * image is used.
     * @param height the height for subimage extraction; if invalid, the full
     * image is used.
     */
    public void loadImages(String item, String path, int width, int height) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            if (width > 0 && height > 0 && width <= image.getWidth() && height <= image.getHeight()) {
                images.put(item, image.getSubimage(0, 0, width, height));
            } else {
                images.put(item, image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads an animation for a specified action from a sprite sheet located at
     * the given file path. The sprite sheet is divided into the specified
     * number of frames with given dimensions.
     *
     * @param action the key to associate with the loaded animation.
     * @param path the file path of the sprite sheet.
     * @param frames the number of frames in the animation.
     * @param frameWidth the width of each frame.
     * @param frameHeight the height of each frame.
     */
    protected void loadAnimation(String action, String path, int frames, int frameWidth, int frameHeight) {
        try {
            BufferedImage sheet = ImageIO.read(new File(path));
            BufferedImage[] animFrames = new BufferedImage[frames];
            for (int i = 0; i < frames; i++) {
                animFrames[i] = sheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            }
            animations.put(action, animFrames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the number of items in the pet's inventory.
     *
     * @return the number of items in the inventory.
     */
    public int getItemCount() {
        return inv.getItemCount();
    }

    /**
     * Sets up the pet by loading its animations and images. This method should
     * be implemented in subclasses to provide specific setup behavior.
     */
    public abstract void setup();

    /**
     * Checks if the pet is currently sleeping.
     *
     * @return {@code true} if the pet is sleeping; {@code false} otherwise.
     */
    public boolean isSleeping() {
        return isSleeping;
    }

    public abstract String getType();

    public abstract String getName();

    /**
     * Plays with the pet, increasing its happiness by the specified effect. The
     * pet will only play if it is not dead, sleeping, or angry, and if the play
     * cooldown period has elapsed.
     *
     * @param effect the amount to increase the pet's happiness.
     * @return {@code true} if the action was successful; {@code false}
     * otherwise.
     */
    public void playWithPet(int effect) {
        long now = System.currentTimeMillis();
        if (!isDead && !isSleeping && now - lastPlayTime >= PLAY_COOLDOWN_MS) {
            happiness = Math.min(MAX_HAPPINESS, happiness + effect);
            inv.useItem(ItemType.TOY);
            lastPlayTime = now;
        }
    }

    /**
     * Checks whether the pet is dead.
     *
     * @return {@code true} if the pet is dead; {@code false} otherwise.
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Sets the pet's dead status.
     *
     * @param dead {@code true} to mark the pet as dead, {@code false}
     * otherwise.
     */
    public void setDead(boolean dead) {
        this.isDead = dead;
    }

    /**
     * Simulates walking the pet. Walking increases the pet's health and
     * decreases its sleep and fullness. The pet will only walk if it is not
     * dead, sleeping, or angry.
     */
    public void walkPet() {
        if (!isDead && !isSleeping && !isAngry) {
            health = Math.min(MAX_HEALTH, health + 10);
            sleep = Math.max(0, sleep - 5);
            fullness = Math.max(0, fullness - 5);
        }
    }

    /**
     * Gives the pet a gift, increasing its happiness by the specified effect.
     * The pet will only receive a gift if it is not dead or sleeping.
     *
     * @param effect the amount to increase the pet's happiness.
     */
    public void giveGift(int effect) {
        if (!isDead && !isSleeping) {
            happiness = Math.min(MAX_HAPPINESS, happiness + effect);
            useItem(ItemType.GIFT);
            System.out.println(name + " received a gift. Happiness: " + happiness);
        }
    }

    /**
     * Takes the pet to the vet, restoring its health by a fixed amount. The pet
     * will only go to the vet if it is not dead, sleeping, or angry, and if the
     * cooldown period has elapsed.
     *
     * @return {@code true} if the action was successful; {@code false}
     * otherwise.
     */
    public boolean takeToVet() {
        long now = System.currentTimeMillis();
        if (!isDead && !isSleeping && !isAngry && now - lastVetTime >= VET_COOLDOWN_MS) {
            System.out.println("Taking to vet");
            health = Math.min(MAX_HEALTH, health + 35);
            lastVetTime = now;
            return true;
        }
        return false;
    }

    /**
     * Sends the pet to bed, setting its sleeping status to true. The pet will
     * only go to bed if it is not dead or angry.
     */
    public void goToBed() {
        if (!isDead && !isAngry) {
            isSleeping = true;
        }
    }

    /**
     * Updates the pet's state by applying stat decay and adjustments. Fullness
     * and happiness decrease over time. If the pet is not sleeping, its sleep
     * decreases and health is penalized when sleep reaches zero, automatically
     * triggering sleep. If sleeping, the pet's sleep is restored until fully
     * rested, at which point it wakes up. Finally, the pet's overall state is
     * updated.
     */
    public void update() {
        fullness = Math.max(0, fullness - 1);
        happiness = Math.max(0, happiness - 1);

        if (isSleeping) {
            // Only recover if already sleeping
            sleep = Math.min(MAX_SLEEP, sleep + 2);
            if (sleep == MAX_SLEEP) {
                isSleeping = false;
            }
        } else {
            // Decay sleep
            sleep = Math.max(0, sleep - 1);

            if (sleep == 0) {
                // Apply penalty, then sleep starts NEXT tick
                health = Math.max(0, health - 10);
                // isSleeping = true;
            }
        }

        updateState();
    }

    /**
     * Updates the pet's state based on its current attributes. If health,
     * happiness, or fullness reaches zero, the corresponding state (dead,
     * angry, hungry) is set to true.
     */
    public void updateState() {
        if (health == 0) {
            isDead = true;
        } else if (happiness == 0) {
            isAngry = true;
        } else if (fullness == 0) {
            isHungry = true;
        }
    }

    /**
     * Adds an item to the pet's inventory.
     *
     * @param item the item to add.
     */
    public void addItem(Item item) {
        inv.addItem(item);
    }

    /**
     * Removes an item from the pet's inventory.
     *
     * @param type the type of item to remove.
     */
    public Item getItem(ItemType type) {
        return inv.getItem(type);
    }

    /**
     * Removes an item from the pet's inventory.
     *
     * @param type the type of item to remove.
     */
    public void useItem(ItemType type) {
        if (inv.hasItem(type)) {
            inv.useItem(type);
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    /**
     * Checks if the pet has a specific item in its inventory.
     *
     * @param type the type of item to check.
     * @return {@code true} if the item is found; {@code false} otherwise.
     */
    public boolean hasItem(ItemType type) {
        return inv.hasItem(type);
    }

    /**
     * Checks if the pet is hungry.
     *
     * @return {@code true} if the pet is hungry; {@code false} otherwise.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the pet's health.
     *
     * @param health the new health value.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Checks the pet's sleep level
     *
     * @return {@code true} if the pet is hungry; {@code false} otherwise.
     */
    public int getSleep() {
        return sleep;
    }

    /**
     * Sets the pet's sleep.
     *
     * @param sleep the new sleep value.
     */
    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    /**
     * returns the pet's fullness level.
     *
     * @return {@code true} if the pet is hungry; {@code false} otherwise.
     */
    public int getFullness() {
        return fullness;
    }

    /**
     * Sets the pet's fullness.
     *
     * @param fullness the new fullness value.
     */
    public void setFullness(int fullness) {
        this.fullness = fullness;
    }

    /**
     * returns the pet's happiness leve;.
     *
     * @return {@code true} if the pet is hungry; {@code false} otherwise.
     */
    public int getHappiness() {
        return happiness;
    }

    /**
     * Sets the pet's happiness.
     *
     * @param happiness the new happiness value.
     */
    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    /**
     * Sets the pet's name.
     *
     * @param name of the pet.
     */
    public void setName(String name) {
        this.name = name;
    }

}
