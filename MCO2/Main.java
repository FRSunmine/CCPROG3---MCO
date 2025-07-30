import java.util.*;

/**
 * Main driver class for the Plants vs Zombies console simulation (MCO1).
 * Simulates a board, zombie and plant generation, and basic interactions.
 */
public class Main {
    public static void main(String[] args) {
        Board board = new Board(5, 9);
        GameView view = new GameView(board);
        GameController controller = new GameController(board, view);
        view.setVisible(true);
    }
}