/**
 * Abstract class for all zombies.
 */
public abstract class Zombie {
    protected int speed, damage, health;
    protected int row, col;
    protected String type;
    protected int moveCounter = 0; // Add this line

    public Zombie(int speed, int damage, int health, String type) {
        this.speed = speed;
        this.damage = damage;
        this.health = health;
        this.type = type;
    }

    public void setPosition(int row, int col) { this.row = row; this.col = col; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }
    public int getDamage() { return damage; }
    public String getType() { return type; }
    public boolean isAlive() { return health > 0; }
    public void takeDamage(int dmg) { health -= dmg; }
    public int getSpeed() { return speed; } // Add getter if not present

    public void resetMoveCounter() { moveCounter = 0; }
    public void incrementMoveCounter() { moveCounter++; }
    public int getMoveCounter() { return moveCounter; }

    @Override
    public String toString() {
        return String.format("Speed=%d, Damage=%d, Health=%d", speed, damage, health);
    }
}