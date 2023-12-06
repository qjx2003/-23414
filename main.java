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

public class main {


    public static void main(String[] args) {

        boolean debugOn = false;

        int rows = -1;
        int columns = -1;
        int mines = -1;
        int flags =-1;

        //initialize the board
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the difficulty: \nEasy: 0 \nMedium: 1 \nHard: 2");
        int difficulty = scanner.nextInt();
        System.out.println("Debug mod on/off ?\n ON: 0\nOff: 1");
        int debugMode = scanner.nextInt();


        if(difficulty == 0){
            rows = 5;
            columns = 5;
            mines = 5;
            flags = 5;
        }
        if(difficulty == 1){
            rows = 9;
            columns = 9;
            mines = 12;
            flags = 12;
        }
        if(difficulty == 2){
            rows = 20;
            columns = 20;
            mines = 40;
            flags = 40;
        }

        Minefield mode = new Minefield(rows, columns, mines);

        //starting point
        System.out.println("Enter the row: ");
        int row = scanner.nextInt();
        System.out.println("Enter the column: ");
        int col = scanner.nextInt();

        //create mines, evaluate the field and reveal starting area.
        if (row >= 0 && row < rows && col >= 0 && col < columns) {
            mode.createMines(row, col, mines);
            mode.evaluateField();
            mode.revealStartingArea(row,col);
        }

        while (!mode.gameOver()) {
            System.out.println("Enter the row: ");
            row = scanner.nextInt();
            System.out.println("Enter the starting column: ");
            col = scanner.nextInt();
            if (row >= 0 && row < rows && col >= 0 && col < columns) {
                boolean flag = scanner.nextBoolean();
                System.out.print("Enter the boolean value flag ");
                if(mode.guess(row, col, flag))
                    break;
                //should end;
            }
            if(debugOn == true)
                mode.debug();
                
            System.out.println("Initial Minefield:");
            System.out.println(mode.toString());
        }
    }
}
