import java.util.*;
import java.util.concurrent.*;

/**
 * Console-based simulation for Plants vs Zombies.
 * Handles the main game logic, simulation loop, user input, and board updates.
 * Uses a scheduled executor for real-time ticking and supports plant placement,
 * zombie spawning, and win/loss conditions.
 */
public class Game {
    private Board board;
    private int sun = 50;
    private int time = 0; // in seconds
    private int level = 1;
    private Scanner scanner = new Scanner(System.in);
    private List<Zombie> zombies = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();
    private Random rand = new Random();
    private volatile boolean gameOver = false;

    /**
     * Constructs a new game with the specified board size.
     * @param rows number of rows
     * @param cols number of columns
     */
    public Game(int rows, int cols) {
        board = new Board(rows, cols);
    }

    /**
     * Starts the simulation loop with a real-time timer.
     * Handles zombie spawning, plant actions, user input, and win/loss conditions.
     */
    public void start() {
        System.out.println("Welcome to Plants vs Zombies Console Simulation!");
        System.out.println("Game starts. You have " + sun + " sun.");

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        executor.scheduleAtFixedRate(() -> {
            if (gameOver) {
                executor.shutdown();
                return;
            }
            time++;
            System.out.println("\nTime: " + String.format("%02d:%02d", time / 60, time % 60));
            // Sun generation every 5 seconds
            if (time % 5 == 0) {
                sun += 25;
                System.out.println("Sun generated! Current sun: " + sun);
            }
            // Generate zombies at intervals
            if (shouldGenerateZombie(time)) {
                int lane = rand.nextInt(board.getRows());
                Zombie z = ZombieFactory.createRandomZombie(level);
                zombies.add(z);
                board.placeZombie(z, lane, board.getCols() - 1);
                System.out.printf("%s appeared in Row %d, Column %d with attributes: %s\n",
                        z.getType(), lane + 1, board.getCols(), z);
            }
            // Move zombies
            for (Zombie z : new ArrayList<>(zombies)) {
                if (!z.isAlive()) continue;
                board.moveZombie(z);
                if (z.getCol() == 0) {
                    System.out.println("A zombie reached your home! Zombies win!");
                    gameOver = true;
                    executor.shutdown();
                    return;
                }
            }
            // Plants attack
            for (Plant p : plants) {
                p.act(board, zombies);
            }
            // Remove dead zombies
            zombies.removeIf(z -> !z.isAlive());
            // Print board state
            board.printBoard();

            if (time >= 180) {
                System.out.println("Time's up! Plants win!");
                gameOver = true;
                executor.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);

        // User input loop (runs in main thread)
        while (!gameOver) {
            userActions();
        }
    }

    /**
     * Determines if a zombie should be generated at the given time.
     * @param t the current time in seconds
     * @return true if a zombie should spawn, false otherwise
     */
    private boolean shouldGenerateZombie(int t) {
        if (t >= 30 && t <= 80 && t % 10 == 0) return true;
        if (t >= 81 && t <= 140 && t % 5 == 0) return true;
        if (t >= 141 && t <= 170 && t % 3 == 0) return true;
        if (t >= 171 && t <= 180) return true; // wave
        return false;
    }

    /**
     * Handles user actions for planting.
     * Prompts the user for plant type and position, and places the plant if possible.
     */
    private void userActions() {
        try {
            Thread.sleep(500); // Prevents busy waiting
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (gameOver) return;
        System.out.println("Current sun: " + sun);
        System.out.println("Available plants: 1) Sunflower (cost 50) 2) Peashooter (cost 100)");
        System.out.print("Do you want to add a plant? (y/n): ");
        String ans = scanner.nextLine();
        if (ans.equalsIgnoreCase("y")) {
            System.out.print("Enter 1 for Sunflower, 2 for Peashooter: ");
            int choice = Integer.parseInt(scanner.nextLine());
            Plant p = null;
            if (choice == 1 && sun >= 50) {
                p = new Sunflower();
                sun -= 50;
            } else if (choice == 2 && sun >= 100) {
                p = new Peashooter();
                sun -= 100;
            } else {
                System.out.println("Not enough sun or invalid choice.");
                return;
            }
            System.out.print("Enter row (1-" + board.getRows() + "): ");
            int row = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.print("Enter column (1-" + board.getCols() + "): ");
            int col = Integer.parseInt(scanner.nextLine()) - 1;
            if (board.placePlant(p, row, col)) {
                plants.add(p);
                System.out.printf("%s placed at Row %d, Column %d\n", p.getType(), row + 1, col + 1);
            } else {
                System.out.println("Tile occupied or invalid.");
                sun += p.getCost(); // refund
            }
        }
    }
}