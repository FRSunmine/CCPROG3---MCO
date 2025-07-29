/**
 * Factory for creating zombies.
 */
public class ZombieFactory {
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