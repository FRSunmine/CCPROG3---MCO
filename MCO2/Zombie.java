/**
 * Abstract class for all zombies in Plants vs Zombies.
 * <p>
 * Defines common zombie properties such as speed, damage, health, position, and type,
 * as well as movement and damage logic.
 * </p>
 */
public abstract class Zombie {
    protected int speed, damage, health;
    protected int row, col;
    protected String type;
    protected int moveCounter = 0; // Used for movement timing

    /**
     * Constructs a zombie with the given attributes.
     * @param speed the number of ticks between moves (lower is faster)
     * @param damage the damage dealt to plants
     * @param health the zombie's health
     * @param type the display type or name
     */
    public Zombie(int speed, int damage, int health, String type) {
        this.speed = speed;
        this.damage = damage;
        this.health = health;
        this.type = type;
    }

    /**
     * Sets the zombie's position on the board.
     * @param row the row index
     * @param col the column index
     */
    public void setPosition(int row, int col) { this.row = row; this.col = col; }

    /**
     * Gets the zombie's current row.
     * @return the row index
     */
    public int getRow() { return row; }

    /**
     * Gets the zombie's current column.
     * @return the column index
     */
    public int getCol() { return col; }

    /**
     * Sets the zombie's column.
     * @param col the column index
     */
    public void setCol(int col) { this.col = col; }

    /**
     * Gets the damage this zombie deals to plants.
     * @return the damage value
     */
    public int getDamage() { return damage; }

    /**
     * Gets the type or display name of the zombie.
     * @return the type string
     */
    public String getType() { return type; }

    /**
     * Checks if the zombie is still alive.
     * @return true if health > 0, false otherwise
     */
    public boolean isAlive() { return health > 0; }

    /**
     * Reduces the zombie's health by the given damage amount.
     * @param dmg the amount of damage taken
     */
    public void takeDamage(int dmg) { health -= dmg; }

    /**
     * Gets the zombie's speed (ticks between moves).
     * @return the speed value
     */
    public int getSpeed() { return speed; }

    /**
     * Resets the zombie's move counter (for movement timing).
     */
    public void resetMoveCounter() { moveCounter = 0; }

    /**
     * Increments the zombie's move counter (for movement timing).
     */
    public void incrementMoveCounter() { moveCounter++; }

    /**
     * Gets the current move counter value.
     * @return the move counter
     */
    public int getMoveCounter() { return moveCounter; }

    @Override
    public String toString() {
        return String.format("Speed=%d, Damage=%d, Health=%d", speed, damage, health);
    }
}