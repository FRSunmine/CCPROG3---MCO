/**
 * Represents a Sunflower plant that generates sun points at a set rate and cooldown.
 */
public class Sunflower {
    private int sunCost;
    private int generateRate;
    private final int COOLDOWN;
    private int cooldownRemaining;
    private int row;
    private int col;

    /**
     * Constructs a Sunflower with default attributes and specified position.
     *
     * @param row the row position of the sunflower
     * @param col the column position of the sunflower
     */
    public Sunflower(int row, int col) {
        this.sunCost = 50;
        this.generateRate = 25;
        this.COOLDOWN = 2;
        this.cooldownRemaining = 0;
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the sun cost to plant this sunflower.
     *
     * @return the sun cost
     */
    public int getSunCost() {
        return sunCost;
    }

    /**
     * Returns the row position of the sunflower.
     *
     * @return the row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column position of the sunflower.
     *
     * @return the column index
     */
    public int getCol() {
        return col;
    }

    /**
     * Advances the cooldown and generates sun if ready.
     *
     * @return the amount of sun generated this tick (0 if on cooldown)
     */
    public int tickAndGenerate() {
        if (cooldownRemaining > 0) {
            cooldownRemaining--;
            return 0;
        } else {
            cooldownRemaining = COOLDOWN;
            return generateRate;
        }
    }

    /**
     * Checks if the sunflower can generate sun this tick.
     *
     * @return true if ready to generate, false if on cooldown
     */
    public boolean canGenerate() {
        return cooldownRemaining == 0;
    }
}