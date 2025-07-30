/**
 * Abstract class for all plants in Plants vs Zombies.
 * <p>
 * Defines common plant properties such as cost, health, damage, position, and type,
 * and provides basic methods for plant actions and state.
 * </p>
 */
import java.util.List;

public abstract class Plant {
    protected int cost, health, damage, row, col;
    protected String type;

    /**
     * Constructs a plant with the given attributes.
     * @param cost the sun cost to place the plant
     * @param health the plant's health
     * @param damage the plant's attack damage
     * @param type the display type or name
     */
    public Plant(int cost, int health, int damage, String type) {
        this.cost = cost;
        this.health = health;
        this.damage = damage;
        this.type = type;
    }

    /**
     * Sets the plant's position on the board.
     * @param row the row index
     * @param col the column index
     */
    public void setPosition(int row, int col) { this.row = row; this.col = col; }

    /**
     * Gets the sun cost of the plant.
     * @return the cost value
     */
    public int getCost() { return cost; }

    /**
     * Gets the type or display name of the plant.
     * @return the type string
     */
    public String getType() { return type; }

    /**
     * Checks if the plant is still alive.
     * @return true if health > 0, false otherwise
     */
    public boolean isAlive() { return health > 0; }

    /**
     * Reduces the plant's health by the given damage amount.
     * @param dmg the amount of damage taken
     */
    public void takeDamage(int dmg) { health -= dmg; }

    /**
     * Plant action per tick (e.g., attack, generate sun).
     * @param board the game board
     * @param zombies the list of zombies on the board
     */
    public abstract void act(Board board, List<Zombie> zombies);
}