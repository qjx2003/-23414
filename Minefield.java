import java.util.Queue;
import java.util.Random;

public class Minefield {
    /**
    Global Section
    */
    public static final String ANSI_YELLOW_BRIGHT = "\u001B[33;1m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE_BRIGHT = "\u001b[34;1m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_PURPLE = "\u001b[35m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001b[47m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001b[45m";
    public static final String ANSI_GREY_BACKGROUND = "\u001b[0m";

    /* 
     * Class Variable Section
     * 
    */

    /*Things to Note:
     * Please review ALL files given before attempting to write these functions.
     * Understand the Cell.java class to know what object our array contains and what methods you can utilize
     * Understand the StackGen.java class to know what type of stack you will be working with and methods you can utilize
     * Understand the QGen.java class to know what type of queue you will be working with and methods you can utilize
     */

    private int row;
    private int columns;
    private int flags;
    public Cell[][] cell;
    boolean flag=false;

    
    /**
     * Minefield
     * 
     * Build a 2-d Cell array representing your minefield.
     * Constructor
     * @param rows       Number of rows.
     * @param columns    Number of columns.
     * @param flags      Number of flags, should be equal to mines
     */
   public Minefield(int rows, int columns, int flags) {
                this.row = rows;
                this.columns = columns;
                this.flags = flags;
                cell = new Cell[rows][columns];
    }
    /**
     * evaluateField
     * 
     *
     * @function:
     * Evaluate entire array.
     * When a mine is found check the surrounding adjacent tiles. If another mine is found during this check, increment adjacent cells status by 1.
     * 
     */
    public void evaluateField() {
        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[0].length; j++) {
                if (cell[i][j].getStatus().equals("M")) {
                    for (int row = i - 1; row <= i + 1; row++) {
                        for (int col = j - 1; col <= j + 1; col++) {// Check for valid indices
                            if (row >= 0 && row < cell.length && col >= 0 && col < cell[0].length) {// Skip the current mine cell
                                if (row == i && col == j) {
                                    continue;
                                }
                                if ("M".equals(cell[row][col].getStatus())) {
                                    int currentStatus = Integer.parseInt(cell[row][col].getStatus());
                                    cell[row][col].setStatus(String.valueOf(currentStatus + 1));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * createMines
     * 
     * Randomly generate coordinates for possible mine locations.
     * If the coordinate has not already been generated and is not equal to the starting cell set the cell to be a mine.
     * utilize rand.nextInt()
     * 
     * @param x       Start x, avoid placing on this square.
     * @param y        Start y, avoid placing on this square.
     * @param mines      Number of mines to place.
     */
    public void createMines(int x, int y, int mines) {
        Random ran = new Random();
        int row = ran.nextInt();
        int column = ran.nextInt();
        if (row != x && column != y && !cell[row][column].getRevealed()) {
            cell[row][column].setStatus("M");
            mines--;
        }
    }
    /**
     * guess
     * 
     * Check if the guessed cell is inbounds (if not done in the Main class). 
     * Either place a flag on the designated cell if the flag boolean is true or clear it.
     * If the cell has a 0 call the revealZeroes() method or if the cell has a mine end the game.
     * At the end reveal the cell to the user.
     * 
     * \
     * @param x       The x value the user entered.
     * @param y       The y value the user entered.
     * @param flag    A boolean value that allows the user to place a flag on the corresponding square.
     * @return boolean Return false if guess did not hit mine or if flag was placed, true if mine found.
     */
    public boolean guess(int x, int y, boolean flag){
            if (x >= 0 && x < 9 && y >= 0 && y < 9) {
                if (flag) {
                    cell[x][y].setStatus("F");
                    flags = flags - 1;
                } else {
                    if (cell[x][y].getStatus() == "0") {
                        revealZeroes(x, y);
                    }
                    if (cell[x][y].getStatus() == "M") {
                        gameOver();
                        return true;
                    }
                }
            }
            return false;
        }

    /**
     * gameOver
     * 
     * Ways a game of Minesweeper ends:
     * 1. player guesses a cell with a mine: game over -> player loses
     * 2. player has revealed the last cell without revealing any mines -> player wins
     * 
     * @return boolean Return false if game is not over and squares have yet to be revealed, otheriwse return true.
     */
    public boolean gameOver() {
        Minefield point = new Minefield(row, columns, flags);
        if (point.guess(row, columns, flag) == false) {
            return true;
        } else {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < columns; j++) {
                    if (!cell[i][j].getRevealed() && !"M".equals(cell[i][j].getStatus())) {
                        return false;  // The game is not over yet
                    }
                }
            }
        }
        return true;
    }

    /**
     * Reveal the cells that contain zeroes that surround the inputted cell.
     * Continue revealing 0-cells in every direction until no more 0-cells are found in any direction.
     * Utilize a STACK to accomplish this.
     *
     * This method should follow the psuedocode given in the lab writeup.
     * Why might a stack be useful here rather than a queue?
     *
     * @param x      The x value the user entered.
     * @param y      The y value the user entered.
     */
    public void revealZeroes(int x, int y) {


    }

    /**
     * revealStartingArea
     *
     * On the starting move only reveal the neighboring cells of the inital cell and continue revealing the surrounding concealed cells until a mine is found.
     * Utilize a QUEUE to accomplish this.
     * 
     * This method should follow the psuedocode given in the lab writeup.
     * Why might a queue be useful for this function?
     *
     * @param x     The x value the user entered.
     * @param y     The y value the user entered.
     */
    public void revealStartingArea(int x, int y) {
        StackGen<Cell[][]> stack = new StackGen<>();
        stack.push(cell[x][y]);
        while (!stack.isEmpty()) {
            Cell [][] currentCell = stack.pop();
            currentCell.setRevealed(true);
                for (int row = - 1; row <= x + 1; row++) {
                    for (int col = y - 1; col <= y + 1; col++) {// Check for valid indices
                        if (row >= 0 && row < cell.length && col >= 0 && col < cell[0].length) {
                            if (!cell[row][col].getRevealed() && cell[row][col].getStatus() != "0") {
                                stack.push(cell[row][col]);
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * For both printing methods utilize the ANSI colour codes provided! 
     * 
     * 
     * 
     * 
     * 
     * debug
     *
     * @function This method should print the entire minefield, regardless if the user has guessed a square.
     * *This method should print out when debug mode has been selected. 
     */
    public void debug() {

    }

    /**
     * toString
     *
     * @return String The string that is returned only has the squares that has been revealed to the user or that the user has guessed.
     */
    public String toString() {

    }
