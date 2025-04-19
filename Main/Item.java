package Main;

/**
 * Represents an item that can be stored in the inventory and its characteristics.
 * <p>
 * Gets the attributes of an item in the virtual pet game that interacts with the inventory and can be used to affect to pet's stats.
 * Each item has a name, type (food, toy, etc), and an effect value that is used to modify the pet's attributes.
 * <p>
 * 
 * @author Brandon Chong
 * @version 1.0
 */
public class Item {
    private String name;      
    private ItemType type;    
    private int effect;

    /**
     * Constructs a new Item object (item) with a specified name, type, and effect.
     * 
     * @param name The name of the item.
     * @param type The type of the item.
     * @param effect The effect value of the item (impact on pet attributes).
     */
    public Item(String name, ItemType type, int effect) {
        // Initialize instance variables
        this.name = name;
        this.type = type;
        this.effect = effect;
    }

    /**
     * Returns the name of the item.
     * 
     * @param name The name of the item.
     * @return The name of the item (String).
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of the item.
     * 
     * @param type The type of the item.
     * @return The type of the item (String).
     * 
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Returns the effect value of the item.
     * 
     * @param effect The effect value of the item.
     * @return The effect value of the item (int).
     * 
     */
    public int getEffect() {
        return effect;
    }
}
