/**
 * Abstract class for all plants.
 */
import java.util.List;

public abstract class Plant {
    protected int cost, health, damage, row, col;
    protected String type;

    public Plant(int cost, int health, int damage, String type) {
        this.cost = cost;
        this.health = health;
        this.damage = damage;
        this.type = type;
    }

    public void setPosition(int row, int col) { this.row = row; this.col = col; }
    public int getCost() { return cost; }
    public String getType() { return type; }
    public boolean isAlive() { return health > 0; }
    public void takeDamage(int dmg) { health -= dmg; }

    /**
     * Plant action per tick (e.g., attack, generate sun).
     */
    public abstract void act(Board board, List<Zombie> zombies);
}