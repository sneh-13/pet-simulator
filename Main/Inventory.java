package Main;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a player's inventory and provides a way of managing items such as
 * food and toys.
 * <p>
 * The Inventory class provides methods for adding, removing, and retrieving
 * items from the player's inventory. This class also keeps track of the maximum
 * number of items that can be stored in the inventory, and the items that are
 * currently in the inventory.
 * <p>
 *
 * @author Brandon Chong
 * @version 1.0
 */
public class Inventory {

    @JsonIgnore
    private ArrayList<Item> items;
    private int maxItems;

    /**
     * Constructs a new Inventory object with a default maximum number of items.
     * <p>
     * This constructor is used by Jackson for deserialization.
     * </p>
     */
    public Inventory() {
        // Default constructor for Jackson.
        this.items = new ArrayList<>();
        this.maxItems = 100;  // or some default value
    }

    /**
     * Constructs the player's inventory with a maximum number of items.
     *
     * @param maxItems The maximum number of items the inventory can hold.
     */
    public Inventory(int maxItems) {
        this.items = new ArrayList<>();
        this.maxItems = maxItems;
    }

    /**
     * Adds an item to the inventory if there is space.
     * <p>
     * Add the item to the inventory if there is space. If the inventory is
     * full, print a message indicating that the item could not be added and
     * return false. Otherwise, add the item to the inventory and return true.
     * <p>
     *
     * @param item The item to add to the inventory.
     * @return true if the item was added successfully, otherwise false.
     */
    public boolean addItem(Item item) {
        if (items.size() >= maxItems) {
            System.out.println("Full inventory, can't add" + item.getName() + " item");
            return false;
        }
        items.add(item);
        return true;
    }

    /**
     * Retrieves an item from the inventory by name.
     * <p>
     * Searches through the inventory for an item with the specified name and
     * returns it if found. If the item isn't found, return null.
     * <p>
     *
     * @param name The name of the item to retrieve.
     * @return The named item, otherwise null if the item isn't found in the
     * inventory.
     */
    public Item getItem(ItemType type) {
        for (Item item : items) {
            if (item.getType() == type) {
                return item;
            }
        }
        return null;
    }

    /**
     * Removes an item from the inventory.
     * <p>
     * Searches the inventory items to find the item the player is trying to
     * remove. Removes it, prints a message, and returns true if found. Prints a
     * failure message and returns false if not found.
     * <p>
     *
     * @param item The item to remove from the inventory.
     * @return true if the item was found and removed, otherwise false.
     */
    public boolean removeItem(ItemType type) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getType() == type) {
                System.out.println("Item removed: " + items.get(i).getName());
                items.remove(i);
                return true;
            }
        }
        System.out.println("Item of type " + type + " not found in inventory");
        return false;
    }

    @JsonIgnore
    public int getItemCount() {
        return items.size();
    }

    /**
     * Checks if the inventory contains a specified item.
     * <p>
     * Searches through the inventory for a specified item. If the item is
     * found, return true. If the item isn't found, return null.
     * <p>
     *
     * @param item The item to check for in the inventory.
     * @return true if the item is found in the inventory, otherwise false.
     */
    public boolean hasItem(ItemType type) {
        for (Item item : items) {
            if (item.getType() == type) {
                return true;
            }
        }
        return false;
    }

    /**
     * Uses an item, then removes it from inventory.
     * <p>
     * Searches through inventory for a specified item. If the item is found,
     * remove it from the inventory and return it. If the item isn't found,
     * return null.
     * <p>
     * @param type The type of the item to be used, i.e food, gift or toy.
     * @return item effect if item is found, otherwise null
     */
    public int useItem(ItemType type) {
        for (Item item : items) {
            if (item.getType() == type) {
                items.remove(item);
                return item.getEffect();
            }
        }
        return 0;

    }

    /**
     * Displays all items in the inventory.
     * <p>
     * If the inventory is empty, print a message indicating that the inventory
     * is empty. Check inventory for each item and print its name and type. If
     * the inventory isn't empty, print the items line by line
     * <p>
     */
    public void showInventory() {
        if (items.isEmpty() == true) {
            System.out.println("Inventory is empty");
        } else {
            System.out.println("Inventory:");
            for (Item item : items) {
                System.out.println(item.getName());
            }
        }
    }
}
