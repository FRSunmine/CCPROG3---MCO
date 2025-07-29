import java.util.*;

/**
 * Main driver class for the Plants vs Zombies console simulation (MCO1).
 * Simulates a board, zombie and plant generation, and basic interactions.
 */
public class Main {
    public static void main(String[] args) {
        Game game = new Game(5, 9); // 5 lanes, 9 tiles per lane
        game.start();
    }
}