/**
 * Sunflower plant.
 */
import java.util.List;

public class Sunflower extends Plant {
    private int sunCounter = 0;

    public Sunflower() {
        super(25, 50, 0, "Sunflower");
    }

    @Override
    public void act(Board board, List<Zombie> zombies) {
        sunCounter++;
    }

    public boolean canGenerateSun() {
        if (sunCounter >= 5) { // Increase this value to reduce output (was 3)
            sunCounter = 0;
            return true;
        }
        return false;
    }
}