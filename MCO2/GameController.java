/**
 * The controller for the Plants vs Zombies game.
 * Handles user actions, plant placement, shovel mode, game logic, zombie spawning,
 * plant and zombie actions, win/loss conditions, and updates the view and model.
 */
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
    private int time = 180; // Start at 3 minutes

    // Placement mode: which plant to place, or null if not placing
    private Plant plantToPlace = null;

    private int zombieSpawnCounter = 0;
    private int zombieSpawnRate = 5; // Spawn a zombie every 5 ticks (increase this number to make it even slower)

    /**
     * Constructs the game controller, sets up listeners, and starts the game loop.
     * @param board the game board
     * @param view the game view (GUI)
     */
    public GameController(Board board, GameView view) {
        this.board = board;
        this.view = view;
        setupListeners();
        setupGridListeners();
        updateView();
        startGameLoop();
    }

    /**
     * Sets up listeners for plant selection buttons.
     */
    private void setupListeners() {
        view.addSunflowerButtonListener(e -> plantToPlace = new Sunflower());
        view.addPeashooterButtonListener(e -> plantToPlace = new Peashooter());
    }

    /**
     * Sets up listeners for each grid button on the board.
     */
    private void setupGridListeners() {
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                final int row = r, col = c;
                view.addGridButtonListener(row, col, e -> handleGridClick(row, col));
            }
        }
    }

    /**
     * Handles a click on the board grid for plant placement.
     * @param row the row clicked
     * @param col the column clicked
     */
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

    /**
     * Updates the view with the current sun and timer values, and refreshes the board.
     */
    private void updateView() {
        view.setSun(sun);
        view.setTimer(time); // Update the timer on the view
        view.refresh();
    }

    /**
     * Starts the main game loop using a Swing timer.
     */
    private void startGameLoop() {
        timer = new Timer(1000, e -> gameTick());
        timer.start();
    }

    /**
     * Executes one tick of the game:
     * - Decrements timer
     * - Spawns zombies at intervals
     * - Plants act (including sun generation)
     * - Zombies move and attack
     * - Removes dead plants and zombies
     * - Checks win/loss conditions
     */
    private void gameTick() {
        time--; // Decrement timer

        // Spawn zombie less frequently
        zombieSpawnCounter++;
        if (zombieSpawnCounter >= zombieSpawnRate) {
            if (rand.nextInt(3) == 0) { // ~33% chance per spawn interval
                int row = rand.nextInt(board.getRows());
                Zombie z = ZombieFactory.createRandomZombie(1);
                z.setPosition(row, board.getCols() - 1);
                board.placeZombie(z, row, board.getCols() - 1);
                zombies.add(z);
            }
            zombieSpawnCounter = 0;
        }

        // 2. Plants act
        for (Plant p : new ArrayList<>(plants)) {
            p.act(board, zombies);
            if (p instanceof Sunflower) {
                Sunflower s = (Sunflower) p;
                if (s.canGenerateSun()) {
                    sun += 25;
                }
            }
        }

        // 3. Remove dead zombies before moving
        for (Zombie z : new ArrayList<>(zombies)) {
            if (!z.isAlive()) {
                board.removeZombie(z.getRow(), z.getCol());
                zombies.remove(z);
            }
        }
        // 4. Zombies move and attack
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
        // Win condition for plants
        if (time <= 0) {
            timer.stop();
            JOptionPane.showMessageDialog(view, "Time's up! Plants win!");
            return;
        }
        updateView();
    }
}