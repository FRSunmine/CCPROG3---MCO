/**
 * Represents a PeaShooter plant that attacks zombies at a set range and cooldown.
 */
public class PeaShooter {
    private int sunCost;
    private int damage;
    private int range;
    private final int COOLDOWN;
    private int cooldownRemaining;
    private int row;
    private int col;

    /**
     * Constructs a PeaShooter with default attributes and specified position.
     *
     * @param row the row position of the peashooter
     * @param col the column position of the peashooter
     */
    public PeaShooter(int row, int col) {
        this.sunCost = 100;
        this.damage = 20;
        this.range = 3;
        this.COOLDOWN = 1;
        this.cooldownRemaining = 0;
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the sun cost to plant this peashooter.
     *
     * @return the sun cost
     */
    public int getSunCost() {
        return sunCost;
    }

    /**
     * Returns the row position of the peashooter.
     *
     * @return the row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column position of the peashooter.
     *
     * @return the column index
     */
    public int getCol() {
        return col;
    }

    /**
     * Advances the cooldown and determines if the peashooter can shoot this tick.
     *
     * @return true if the peashooter shoots this tick, false if on cooldown
     */
    public boolean tickAndShoot() {
        if (cooldownRemaining > 0) {
            cooldownRemaining--;
            return false;
        } else {
            cooldownRemaining = COOLDOWN;
            return true;
        }
    }

    /**
     * Returns the damage dealt per shot.
     *
     * @return the damage value
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Returns the attack range of the peashooter.
     *
     * @return the range value
     */
    public int getRange() {
        return range;
    }
}