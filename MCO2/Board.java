/**
 * Represents the game board for Plants vs Zombies.
 * <p>
 * Manages the placement and movement of plants and zombies,
 * and provides utility methods for board operations and state queries.
 * </p>
 */
public class Board {
    private int rows, cols;
    private Plant[][] plants;
    private Zombie[][] zombies;

    /**
     * Constructs a new board with the specified number of rows and columns.
     * @param rows the number of rows
     * @param cols the number of columns
     */
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        plants = new Plant[rows][cols];
        zombies = new Zombie[rows][cols];
    }

    /**
     * Returns the number of rows on the board.
     * @return the number of rows
     */
    public int getRows() { return rows; }

    /**
     * Returns the number of columns on the board.
     * @return the number of columns
     */
    public int getCols() { return cols; }

    /**
     * Checks if the given position is within the bounds of the board.
     * @param row the row index
     * @param col the column index
     * @return true if in bounds, false otherwise
     */
    private boolean inBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /**
     * Places a plant at the specified position if the tile is empty.
     * @param p the plant to place
     * @param row the row index
     * @param col the column index
     * @return true if the plant was placed, false otherwise
     */
    public boolean placePlant(Plant p, int row, int col) {
        if (inBounds(row, col) && plants[row][col] == null) {
            plants[row][col] = p;
            p.setPosition(row, col);
            return true;
        }
        return false;
    }

    /**
     * Places a zombie at the specified position.
     * @param z the zombie to place
     * @param row the row index
     * @param col the column index
     */
    public void placeZombie(Zombie z, int row, int col) {
        if (inBounds(row, col)) {
            zombies[row][col] = z;
            z.setPosition(row, col);
        }
    }

    /**
     * Moves a zombie one tile to the left, attacking any plant in the way.
     * Updates the zombie's position and the board state.
     * @param z the zombie to move
     */
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

    /**
     * Prints the current board state to the console.
     * [P] for plant, [Z] for zombie, [ ] for empty.
     */
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

    /**
     * Returns the plant at the specified position, or null if none.
     * @param row the row index
     * @param col the column index
     * @return the plant at the position, or null
     */
    public Plant getPlantAt(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) return plants[row][col];
        return null;
    }

    /**
     * Returns the zombie at the specified position, or null if none.
     * @param row the row index
     * @param col the column index
     * @return the zombie at the position, or null
     */
    public Zombie getZombieAt(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) return zombies[row][col];
        return null;
    }

    /**
     * Removes the plant at the specified position.
     * @param row the row index
     * @param col the column index
     */
    public void removePlant(int row, int col) {
        if (inBounds(row, col)) {
            plants[row][col] = null;
        }
    }

    /**
     * Removes the zombie at the specified position.
     * @param row the row index
     * @param col the column index
     */
    public void removeZombie(int row, int col) {
        if (inBounds(row, col)) {
            zombies[row][col] = null;
        }
    }
}