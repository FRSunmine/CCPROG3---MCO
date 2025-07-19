/**
 * CCPROG3 MCO1 Demo
 * Demonstrates the creation and basic interaction of Sunflower, PeaShooter, and Zombie
 * according to the MCO1 specs.
 *
 * - Sunflower generates sun every 2 turns (25 sun per generation)
 * - PeaShooter shoots every turn (20 damage, range 3)
 * - Zombie moves left by 4 columns per turn, starts at col 8, row 0 (health 70)
 * - Demo runs for 5 turns, showing sun generation, shooting, and zombie movement/damage
 */
public class MCO1Demo {
    public static void main(String[] args) {
        // Plant a Sunflower at (0, 0)
        Sunflower sunflower = new Sunflower(0, 0);
        // Plant a PeaShooter at (0, 2)
        PeaShooter peashooter = new PeaShooter(0, 2);
        // Spawn a Zombie at (0, 8)
        Zombie zombie = new Zombie(0, 8);

        int totalSun = 0;

        System.out.println("=== MCO1 DEMO START ===");
        System.out.println("Sunflower planted at (" + sunflower.getRow() + ", " + sunflower.getCol() + ")");
        System.out.println("PeaShooter planted at (" + peashooter.getRow() + ", " + peashooter.getCol() + ")");
        System.out.println("Zombie spawned at (" + zombie.getRow() + ", " + zombie.getCol() + ")");

        for (int turn = 1; turn <= 5; turn++) {
            System.out.println("\n--- Turn " + turn + " ---");

            // Sunflower generates sun if possible
            int sun = sunflower.tickAndGenerate();
            if (sun > 0) {
                System.out.println("Sunflower generates " + sun + " sun!");
                totalSun += sun;
            } else {
                System.out.println("Sunflower is on cooldown.");
            }

            // PeaShooter shoots if zombie is in range
            int distance = zombie.getCol() - peashooter.getCol();
            if (distance >= 0 && distance <= peashooter.getRange() && !zombie.isDead()) {
                if (peashooter.tickAndShoot()) {
                    zombie.takeDamage(peashooter.getDamage());
                    System.out.println("PeaShooter shoots! Zombie takes " + peashooter.getDamage() +
                            " damage. Zombie health: " + zombie.getHealth());
                } else {
                    System.out.println("PeaShooter is on cooldown.");
                }
            } else {
                peashooter.tickAndShoot(); // still tick cooldown
                if (!zombie.isDead())
                    System.out.println("Zombie out of range for PeaShooter.");
            }

            // Zombie moves if alive
            if (!zombie.isDead()) {
                zombie.move();
                System.out.println("Zombie moves to (" + zombie.getRow() + ", " + zombie.getCol() + ")");
            } else {
                System.out.println("Zombie is dead!");
            }
        }

        System.out.println("\nTotal sun collected: " + totalSun);
        System.out.println("Zombie final health: " + zombie.getHealth());
        System.out.println("Zombie final position: (" + zombie.getRow() + ", " + zombie.getCol() + ")");
        System.out.println("=== MCO1 DEMO END ===");
    }
}