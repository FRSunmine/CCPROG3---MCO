/**
 * Peashooter plant.
 */
import java.util.List;

public class Peashooter extends Plant {
    public Peashooter() {
        super(75, 50, 10, "Peashooter");
    }

    @Override
    public void act(Board board, List<Zombie> zombies) {
        // Attack the first zombie in the same row to the right
        for (Zombie z : zombies) {
            if (z.getRow() == row && z.getCol() > col && z.isAlive()) {
                z.takeDamage(damage);
                System.out.printf("Peashooter at Row %d, Col %d shot %s at Col %d\n",
                        row + 1, col + 1, z.getType(), z.getCol() + 1);
                break;
            }
        }
    }
}