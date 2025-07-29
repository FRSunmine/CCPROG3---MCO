/**
 * Sunflower plant.
 */
public class Sunflower extends Plant {
    public Sunflower() {
        super(50, 50, 0, "Sunflower");
    }

    @Override
    public void act(Board board, List<Zombie> zombies) {
        // Sun generation handled in Game for simplicity
    }
}