/**
 * Factory for creating zombies.
 * <p>
 * Provides a method to create random zombie instances based on the game level.
 * </p>
 */
public class ZombieFactory {
    /**
     * Creates a random zombie instance based on the given level.
     * @param level the current game level (can be used to adjust difficulty)
     * @return a new Zombie instance (Normal, Flag, or Conehead)
     */
    public static Zombie createRandomZombie(int level) {
        int r = (int) (Math.random() * 3);
        switch (r) {
            case 0: return new NormalZombie();
            case 1: return new FlagZombie();
            case 2: return new ConeheadZombie();
            default: return new NormalZombie();
        }
    }
}