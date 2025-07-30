import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

public class GameController {
    private Board board;
    private GameView view;
    private int sun = 50;
    private List<Zombie> zombies = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();
    private Timer timer;
    private Random rand = new Random();

    // Placement mode: which plant to place, or null if not placing
    private Plant plantToPlace = null;

    public GameController(Board board, GameView view) {
        this.board = board;
        this.view = view;
        setupListeners();
        setupGridListeners();
        updateView();
        startGameLoop();
    }

    private void setupListeners() {
        view.addSunflowerButtonListener(e -> plantToPlace = new Sunflower());
        view.addPeashooterButtonListener(e -> plantToPlace = new Peashooter());
    }

    private void setupGridListeners() {
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                final int row = r, col = c;
                view.addGridButtonListener(row, col, e -> handleGridClick(row, col));
            }
        }
    }

    private void handleGridClick(int row, int col) {
        if (plantToPlace == null) return; // Not in placement mode
        if (sun < plantToPlace.getCost()) {
            JOptionPane.showMessageDialog(view, "Not enough sun!");
            plantToPlace = null;
            return;
        }
        if (board.placePlant(plantToPlace, row, col)) {
            sun -= plantToPlace.getCost();
            plantToPlace.setPosition(row, col);
            plants.add(plantToPlace);
            plantToPlace = null;
            updateView();
        } else {
            JOptionPane.showMessageDialog(view, "Invalid position or already occupied!");
            plantToPlace = null;
        }
    }

    private void updateView() {
        view.setSun(sun);
        view.refresh();
    }

    private void startGameLoop() {
        timer = new Timer(1000, e -> gameTick());
        timer.start();
    }

    private void gameTick() {
        // 1. Possibly spawn a zombie
        if (rand.nextInt(3) == 0) { // ~33% chance per tick
            int row = rand.nextInt(board.getRows());
            Zombie z = ZombieFactory.createRandomZombie(1);
            z.setPosition(row, board.getCols() - 1);
            board.placeZombie(z, row, board.getCols() - 1);
            zombies.add(z);
        }

        // 2. Plants act
        for (Plant p : new ArrayList<>(plants)) {
            p.act(board, zombies);
            if (p instanceof Sunflower) {
                sun += 25;
            }
        }

        // 3. Zombies move and attack
        for (Zombie z : new ArrayList<>(zombies)) {
            if (!z.isAlive()) {
                board.removeZombie(z.getRow(), z.getCol());
                zombies.remove(z);
            }
        }
        for (Zombie z : new ArrayList<>(zombies)) {
            if (!z.isAlive()) continue;
            z.incrementMoveCounter();
            if (z.getMoveCounter() >= z.getSpeed()) {
                int row = z.getRow();
                int col = z.getCol();
                Plant target = null;
                if (col > 0) {
                    target = board.getPlantAt(row, col - 1);
                }
                if (target != null && target.isAlive()) {
                    target.takeDamage(z.getDamage());
                    if (!target.isAlive()) {
                        board.removePlant(row, col - 1);
                        plants.remove(target);
                    }
                    // Zombie stays in place if attacking
                } else {
                    board.moveZombie(z); // This should handle updating zombie's position
                    // Do NOT decrement z.setCol() here, it's handled in moveZombie
                }
                z.resetMoveCounter();
                if (z.getCol() <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(view, "Game Over! A zombie reached your house!");
                    return;
                }
            }
        }
        zombies.removeIf(z -> !z.isAlive());
        plants.removeIf(p -> !p.isAlive());
        updateView();
    }
}