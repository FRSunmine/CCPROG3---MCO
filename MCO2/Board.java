/**
 * Represents the game board.
 */
public class Board {
    private int rows, cols;
    private Plant[][] plants;
    private Zombie[][] zombies;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        plants = new Plant[rows][cols];
        zombies = new Zombie[rows][cols];
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    // Helper method to check if a position is within bounds
    private boolean inBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public boolean placePlant(Plant p, int row, int col) {
        if (inBounds(row, col) && plants[row][col] == null) {
            plants[row][col] = p;
            p.setPosition(row, col);
            return true;
        }
        return false;
    }

    public void placeZombie(Zombie z, int row, int col) {
        if (inBounds(row, col)) {
            zombies[row][col] = z;
            z.setPosition(row, col);
        }
    }

    public void moveZombie(Zombie z) {
        int row = z.getRow();
        int col = z.getCol();
        if (col > 0) {
            zombies[row][col] = null; // Clear old position
            if (plants[row][col - 1] != null) {
                // Attack plant
                Plant p = plants[row][col - 1];
                p.takeDamage(z.getDamage());
                if (!p.isAlive()) {
                    plants[row][col - 1] = null;
                }
                zombies[row][col] = z; // Stay in place if attacking
            } else {
                z.setCol(col - 1); // Move left
                zombies[row][col - 1] = z; // Set new position
            }
        }
    }

    public void printBoard() {
        System.out.println("Board:");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (plants[r][c] != null) {
                    System.out.print("[P]");
                } else if (zombies[r][c] != null) {
                    System.out.print("[Z]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }

    public Plant getPlantAt(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) return plants[row][col];
        return null;
    }
    public Zombie getZombieAt(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) return zombies[row][col];
        return null;
    }

    public void removePlant(int row, int col) {
        if (inBounds(row, col)) {
            plants[row][col] = null;
        }
    }
    public void removeZombie(int row, int col) {
        if (inBounds(row, col)) {
            zombies[row][col] = null;
        }
    }
}