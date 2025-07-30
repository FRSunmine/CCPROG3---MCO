import java.util.*;

/**
 * Main driver class for the Plants vs Zombies GUI simulation.
 * Initializes the game board, view, and controller, and starts the GUI.
 */
public class Main {
    /**
     * Entry point for the Plants vs Zombies game.
     */
    public static void main(String[] args) {
        Board board = new Board(5, 9);
        GameView view = new GameView(board);
        GameController controller = new GameController(board, view);
        view.setVisible(true);
    }
}