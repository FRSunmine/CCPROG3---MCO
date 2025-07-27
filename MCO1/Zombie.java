/**
 * Represents a Zombie in the game with speed, damage, health, and position.
 */
public class Zombie {
    private int speed;
    private int damage;
    private int health;
    private int row;
    private int col;

    /**
     * Constructs a Zombie with default attributes and specified position.
     *
     * @param row the row position of the zombie
     * @param col the column position of the zombie
     */
    public Zombie(int row, int col) {
        this.speed = 4;
        this.damage = 10;
        this.health = 70;
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the speed of the zombie.
     *
     * @return the speed of the zombie
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Returns the damage dealt by the zombie.
     *
     * @return the damage value
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Returns the current health of the zombie.
     *
     * @return the health value
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the row position of the zombie.
     *
     * @return the row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column position of the zombie.
     *
     * @return the column index
     */
    public int getCol() {
        return col;
    }

    /**
     * Moves the zombie left by its speed (decreases column).
     */
    public void move() {
        this.col -= speed;
    }

    /**
     * Represents the zombie's attack action.
     * Actual attack logic should be handled in the game loop.
     */
    public void attack() {
        // Attack logic handled in game loop
    }

    /**
     * Reduces the zombie's health by the specified amount.
     *
     * @param amount the amount of damage taken
     */
    public void takeDamage(int amount) {
        this.health -= amount;
    }

    /**
     * Checks if the zombie is dead (health is 0 or less).
     *
     * @return true if the zombie is dead, false otherwise
     */
    public boolean isDead() {
        return this.health <= 0;
    }
}