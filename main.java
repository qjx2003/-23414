//Import Section
import java.util.Random;
import java.util.Scanner;

/*
 * Provided in this class is the neccessary code to get started with your game's implementation
 * You will find a while loop that should take your minefield's gameOver() method as its conditional
 * Then you will prompt the user with input and manipulate the data as before in project 2
 * 
 * Things to Note:
 * 1. Think back to project 1 when we asked our user to give a shape. In this project we will be asking the user to provide a mode. Then create a minefield accordingly
 * 2. You must implement a way to check if we are playing in debug mode or not.
 * 3. When working inside your while loop think about what happens each turn. We get input, user our methods, check their return values. repeat.
 * 4. Once while loop is complete figure out how to determine if the user won or lost. Print appropriate statement.
 */

public class main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the number of rows: ");
    int rows = scanner.nextInt();
    System.out.println("Enter the number of columns: ");
    int columns = scanner.nextInt();
    System.out.println("Enter the number of mines: ");
    int mines = scanner.nextInt();
    Minefield mode = new Minefield(rows,columns,mines);

    while(!mode.gameOver()){
            mode.createMines(0,0,mines);
            mode.evaluateField();
            System.out.println("Enter the row: ");
            int row = scanner.nextInt();
            System.out.println("Enter the starting column: ");
            int col = scanner.nextInt();
            if(row>=0 && row<rows && col>=0 && col<columns){
                boolean flag = scanner.nextBoolean();
                System.out.print("Enter the boolean value flag ");
                while(!mode.guess(row,col,flag)){
                    mode.revealStartingArea(row, col);
                    mode.revealZeroes(row,col);
                }
                //should end;
            }
            mode.debug();
            System.out.println("Initial Minefield:");
            System.out.println(mode.toString());
    }
}

