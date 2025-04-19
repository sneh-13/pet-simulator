package Main;

import Pets.Pet;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Player {

    private String name;
    private int score;
    private double gameTimePlayed;

    @JsonIgnore
    private long petUpdate = 0;

    @JsonIgnore
    private GamePanel gamePanel;

    @JsonIgnore
    private InputHandler input;

    @JsonIgnore
    private ArrayList<Pet> pets;

    private Pet activePet;

    //no argument constructor for Jackson package
    public Player() {
        this.name = "";
        this.score = 0;
        this.gameTimePlayed = 0;
        this.activePet = null;
        this.gamePanel = null;
        this.input = null;
        this.pets = new ArrayList<>();
    }

    /**
     * Constructor for the Player class.
     * <p>
     * Initializes the player's name, score, active pet, game panel, and input
     * handler.
     * <p>
     *
     * @param name
     * @param activePet
     * @param gamePanel
     * @param input
     */
    public Player(String name, Pet activePet, GamePanel gamePanel, InputHandler input) {
        this.name = name;
        this.score = 0;
        this.gameTimePlayed = 0;
        this.activePet = activePet;
        this.gamePanel = gamePanel;
        this.input = input;
        this.pets = new ArrayList<>();
        this.pets.add(activePet);
        this.gameTimePlayed = 0;
    }

    /**
     * Sets the active pet for the player.
     * <p>
     * Checks if the pet is already in the player's list of pets. Adds the pet
     * to the player's list of pets if it is not already there.
     * <p>
     * @param pet the pet to be set as active
     */
    public void setActivePet(Pet pet) {
        this.activePet = pet;
        if (!pets.contains(pet)) {
            pets.add(pet);
        }
    }

    /**
     * Returns the player's active pet.
     * <p>
     * The active pet is the pet that the player is currently interacting with.
     * </p>
     *
     * @return the player's active pet
     */
    public Pet getActivePet() {
        return activePet;
    }

    /**
     * Initializes the player's inventory with items specific to the active pet.
     * <p>
     * This method's called when a new game starts to ensure the player has
     * appropriate items for caring for their selected pet. Each pet type
     * receives a unique combination of food, toys, and optional items (e.g.,
     * vitamins or gifts) that reflect their specific care requirements.
     * </p>
     */
    public void initializeInventory() {
        String petType = activePet.getType();
        if (petType.equals("Bunny")) {
            activePet.addItem(new Item("food", ItemType.FOOD, 10));
            activePet.addItem(new Item("toy", ItemType.TOY, 10));
        } else if (petType.equals("Cat")) {
            activePet.addItem(new Item("catfood", ItemType.FOOD, 15));
            activePet.addItem(new Item("toy", ItemType.TOY, 10));
        } else if (petType.equals("Dog")) {
            activePet.addItem(new Item("food", ItemType.FOOD, 20));
            activePet.addItem(new Item("toy", ItemType.TOY, 10));
            activePet.addItem(new Item("toybone", ItemType.GIFT, 10));
        }
    }

    /**
     * Feeds the player's active pet, increasing its fullness and rewarding the
     * player with score.
     * <p>
     * The amount of fullness increased is determined by the effect parameter.
     * </p>
     *
     * @param effect the amount to increase the pet's fullness
     */
    public void feedPet(int effect) {
        if (activePet != null) {
            activePet.feedPet(effect);
            activePet.useItem(ItemType.FOOD);
            addScore(10);
        }
    }

    /**
     * Walks the player's active pet, increasing its health and rewarding the
     * player with score.
     * <p>
     * The active pet will be walked if it is not null. Walking the pet will
     * trigger the pet's own walk behavior and increase the player's score by 5
     * points.
     * </p>
     */
    public void walkPet() {
        if (activePet != null) {
            activePet.walkPet();
            addScore(15);
        }
    }

    /**
     * Sends the player's active pet to bed, setting its sleeping status to true
     * and increments the player's score by 10 points.
     * <p>
     * The pet will only go to bed if it is not null and is in a suitable state
     * (not dead or angry) as determined by the pet's own logic.
     * </p>
     */
    public void goToBed() {
        if (activePet != null) {
            activePet.goToBed();
            addScore(10);
        }
    }

    /**
     * Plays with the player's active pet, increasing its happiness and
     * rewarding the player with score.
     * <p>
     * The effect parameter determines the amount of happiness increased.
     * </p>
     *
     * @param effect the amount to increase the pet's happiness
     */
    public void playWithPet(int effect) {
        if (activePet != null) {
            activePet.useItem(ItemType.TOY);
            addScore(10);
            activePet.playWithPet(effect);
        }
    }

    /**
     * Takes the player's active pet to the vet, increasing its health and
     * rewarding the player with score.
     * <p>
     * The active pet will be taken to the vet if it is not null. Taking the pet
     * to the vet will trigger the pet's own vet behavior and increase the
     * player's score by 25 points.
     * </p>
     *
     * @return true if the pet was successfully taken to the vet, false
     * otherwise
     */
    public boolean takeToVet() {
        if (activePet != null) {
            addScore(25);
        }
        return activePet.takeToVet();
    }

    /**
     * Gives a gift to the player's active pet, increasing its happiness and
     * rewarding the player with score.
     * <p>
     * The effect parameter determines the amount of happiness increased.
     * </p>
     *
     * @param effect the amount to increase the pet's happiness
     */
    public void giveGift(int effect) {
        if (activePet != null) {
            activePet.giveGift(effect);
            activePet.useItem(ItemType.GIFT);
            addScore(20);
        }
    }

    // Update method
    /**
     * Updates the player's game time played and calls the update method of the
     * currently active panel.
     * <p>
     * This method increments the player's game time played by one tick and
     * calls the update method of the currently active game panel. This method
     * is called once per tick, and is responsible for keeping track of the
     * player's game time played and for ensuring that the currently active
     * panel is updated properly.
     * </p>
     */
    public void update() {
        long currentTime = System.currentTimeMillis();
        if (activePet != null && currentTime - petUpdate > 1000) { //gameTimePlayed increments every second given we are using ms
            activePet.update();
            petUpdate = currentTime;
            gameTimePlayed++;
        }

    }

    /**
     * Increments the player's score by the specified amount.
     * <p>
     * This method adds the specified amount to the player's current score.
     * </p>
     *
     * @param add the amount to add to the current score
     */
    public void addScore(int add) {
        score += add;
    }

    /**
     * Deducts the specified amount from the player's current score.
     * <p>
     * This method subtracts the specified amount from the player's current
     * score.
     * </p>
     *
     * @param deduct the amount to subtract from the current score
     */
    public void deductScore(int deduct) {
        score -= deduct;
    }

    /**
     * Returns the player's current score.
     * <p>
     * The player's score is incremented or decremented by calling the addScore
     * or deductScore method respectively.
     * </p>
     *
     * @return the player's current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets and returns the name of the player.
     *
     * @return the name of the player
     */
    /**
     * Gets and returns the game time played by the player.
     *
     * @return the game time played by the player
     */
    public double getGameTimePlayed() {
        return gameTimePlayed / 60.0;

    }

    /**
     * Gets and returns the name of the player.
     *
     * @return the name of the player
     */
    @JsonIgnore
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player.
     *
     * @param name the new name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the score of the player.
     *
     * @param score the new score of the player
     */
    public void setScore(int score) {
        this.score = score;
    }
}
