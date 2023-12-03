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
    public Cell[][] board;
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
  public Minefield(int rows, int columns, int flags) {//完成
        this.row = rows;
        this.columns =  columns;
        this.flags = flags;

        board = new Cell[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                board[row][col] = new Cell(false, "-");
            }
        }
    }
   public void evaluateField() {//应该可以
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < columns; j++) {

                if(board[i][j].getStatus().equals("M"))
                    continue;

                int mineSurround = 0;

                //check the cells above
                if(i-1 >= 0){
                    if(board[i-1][j].getStatus().equals("M"))
                        mineSurround++;
                    if(j-1 >= 0){
                        if(board[i-1][j-1].getStatus().equals("M"))
                            mineSurround++;
                    }
                    if(j+1 < columns){
                        if(board[i-1][j+1].getStatus().equals("M"))
                            mineSurround++;
                    }
                }

                //check the cells below
                if(i+1 < row){
                    if(board[i+1][j].getStatus().equals("M"))
                        mineSurround++;
                    if(j-1 >= 0){
                        if(board[i+1][j-1].getStatus().equals("M"))
                            mineSurround++;
                    }
                    if(j+1 < columns){
                        if(board[i+1][j+1].getStatus().equals("M"))
                            mineSurround++;
                    }
                }

                //check the cells to the left and right
                if(j-1 >= 0){
                    if(board[i][j-1].getStatus().equals("M"))
                        mineSurround++;
                }
                if(j+1 < row){
                    if(board[i][j+1].getStatus().equals("M"))
                        mineSurround++;
                }
                board[i][j] = new Cell(false, ""+mineSurround);
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
    public void createMines(int x, int y, int mines) {//应该没问题
        Random ran = new Random();
        while(mines > 0){
            //Generate random coordinates
            int mineRow = ran.nextInt(row);
            int mineCol = ran.nextInt(columns);

            //Test if the coordinate is equal to the starting point, or is mine, or has been revealed
            if((mineRow==x && mineCol==y) || board[mineRow][mineCol].getStatus() == "M" || board[mineRow][mineCol].getRevealed())
                continue;
            board[mineRow][mineCol] = new Cell(false, "M");
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
    public boolean guess(int x, int y, boolean flag) {//应该没有问题
        if (x >= row || x < 0 || y >= columns || y < 0)
            return false;

        if (flag) {//flag or de-flag
            if(board[x][y].getStatus() == "F"){
                board[x][y].setStatus("-");
                flags++;
            }
            else {
                board[x][y].setStatus("F");
                flags--;
            }
            return true;
        } else {//reveal the cell
            if (!board[x][y].getRevealed()) {
                board[x][y].setRevealed(true);
                if(board[x][y].getStatus() == "M")
                    gameOver();
                else if (board[x][y].getStatus() == "0")
                    revealZeroes(x,y);
                return true;
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
    public boolean gameOver() {//应该没问题
        for(int i = 0; i < row; i++){
            for(int j = 0; j < columns; j++){
                //if a mine is revealed, player lose, return true
                if(board[i][j].getRevealed() && board[i][j].getStatus().equals("M")){
                    System.out.println("You lose");
                    return true;
                }
                // if a cell is not mine and hasn't been revealed, return false;
                if(!(board[i][j].getRevealed()) && board[i][j].getStatus().equals("M")){
                    return false;
                }
            }
        }
        System.out.println("you win!");
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
    public void revealZeroes(int x, int y) {//应该可以,睡了不想了
        Stack1Gen<int[]> cells = new Stack1Gen<>();
        cells.push(new int[] {x, y});

        while(!cells.isEmpty()){
            int[] temp = cells.pop();
            x = temp[0];
            y = temp[1];

            board[x][y].setRevealed(true);

            //if a neighbor cell in bounds, equal to 0 and unrevealed, push into the stack
            //down
            if(x < row-1 && !board[x + 1][y].getRevealed() && board[x + 1][y].getStatus().equals("0"))
                cells.push(new int[] {x+1, y});
            //up
            if(x > 0 && !board[x - 1][y].getRevealed() && board[x - 1][y].getStatus().equals("0"))
                cells.push(new int[] { x - 1, y});
            //right
            if(y < columns-1 &&  !board[x][y + 1] .getRevealed() && board[x][y + 1].getStatus().equals("0"))
                cells.push(new int[] { x, y + 1});
            //left
            if (y > 0 && !board[x][y - 1].getRevealed() && board[x][y - 1].getStatus().equals("0"))
                cells.push(new int[] { x, y - 1});
        }
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
    public void revealStartingArea(int x, int y) {//应该ok,但是文件里的图好像把mine给reveal了,感觉很奇怪,可以去oh问一下,我觉得是图的问题
        Q1Gen<int[]> cells = new Q1Gen<>();
        cells.add(new int[] {x, y});

        while (cells.length() != 0){
            int[] temp = cells.remove();
            x = temp[0];
            y = temp[1];

            //break the loop if a mine is found
            if(board[x][y].getStatus().equals("M"))
                return;

            board[x][y].setRevealed(true);

            //down
            if(x < row-1 && !board[x + 1][y].getRevealed())
                cells.add(new int[] {x+1, y});
            //up
            if(x > 0 && !board[x - 1][y].getRevealed())
                cells.add(new int[] { x - 1, y});
            //right
            if(y < columns-1 &&  !board[x][y + 1] .getRevealed())
                cells.add(new int[] { x, y + 1});
            //left
            if (y > 0 && !board[x][y - 1].getRevealed())
                cells.add(new int[] { x, y - 1});
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
    public void debug() {//应该能用
        for(int i = 0; i < row; i++){
            for(int j = 0; j < columns; j++){
                System.out.print(board[i][j].getStatus() + " ");
            }
            System.out.println();
        }
    }

    /**
     * toString
     *
     * @return String The string that is returned only has the squares that has been revealed to the user or that the user has guessed.
     */
    public String toString() {

    }
