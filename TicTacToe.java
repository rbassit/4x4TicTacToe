/************ @author Ryan Bassit **************/
/**
 * Oct. 7, 2015
 * Homework #2 - CISC 3150
 * 
 * A simple TicTacToe++ game for two players, X and O, who take turns placing
 * their marks in a 4×4 board. The player who succeeds in placing four 
 * marks in a horizontal, vertical, diagonal, or 2×2-square way, wins the game.
 */

import java.util.*;

public class TicTacToe{

    //Member variables of class
    private int Rows = 4, Cols = 4, xpos, ypos, count=0;
    private char [][] Board = new char [Rows][Cols];
    private boolean[][] wasThere = new boolean [Rows][Cols];	//Used to keep track of inputted coordinate pairs

    //Constructor initializes the main Board game and wasThere 2D-Arrays
    TicTacToe(){
        for(int i = 0; i < Rows; i++)
            for (int j = 0; j < Cols; j++)
                Board[i][j] = '!';

        for(int i = 0; i < Rows; i++)
            for (int j = 0; j < Cols; j++)
                wasThere[i][j] = true;
    }

    //Prints the current Board 2D Array
    public void printBoard(){
        System.out.print("Current Board...\n");
        System.out.println(" 1 2 3 4 ");

        for(int i = 0; i < Rows; i++){
            System.out.print(i+1);
            for (int j = 0; j < Cols; j++)
                System.out.print(Board[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    //Get coordinates from user and begin the game until winner is found or tie then return
    public void setPos(Scanner keyboard, boolean track, String input, TicTacToe game){

        if (count%2==0)
            System.out.println("X's turn!");
        else System.out.println("O's turn!");

        System.out.println("Enter a valid X coordinate between 1 and 4(inclusive).");

        //Accept input from the user
        input = keyboard.next();
        char choice = input.toLowerCase().charAt(0);

        //Validate input from the user
        track = true;
        while(track){
            if(Character.getNumericValue(choice) > 0 && Character.getNumericValue(choice) < 5){
                xpos = Character.getNumericValue(choice)-1;
                track=false;
            }
            else if(choice == '$'){
                System.out.println("User ended game.\n\n");
                return;
            }
            else{
                System.out.println("Invalid entry. Please enter a valid integer value between 1 and 4 (inclusive)");
                input = keyboard.next();
                choice = input.toLowerCase().charAt(0);
            }
        }

        System.out.println("Enter a valid Y coordinate between 1 and 4(inclusive).");

        //Accept input from the user
        input = keyboard.next();
        choice = input.toLowerCase().charAt(0);

        //Validate input from the user
        track = true;
        while(track){
            if(Character.getNumericValue(choice) > 0 && Character.getNumericValue(choice) < 5){
                ypos = Character.getNumericValue(choice)-1;
                track=false;
            }
            else if(choice == '$'){
                System.out.println("User ended game.\n\n");
                return;
            }
            else{
                System.out.println("Invalid entry. Please enter a valid integer value between 1 and 4 (inclusive)");
                input = keyboard.next();
                choice = input.toLowerCase().charAt(0);
            }
        }

        //Check if coordinate was already used
        if(wasThere[ypos][xpos]== false){
            System.out.println("Coordinate already used. Please enter another pair.");
            game.setPos(keyboard, track, input, game);
        }
        else wasThere[ypos][xpos]= false;	//Set coordinate as marked

        //Accommodate alternating moves between X and O and place them on Board 2D Array
        //Check for winner
        if (count%2==0){
            count++;
            Board[ypos][xpos] = 'X';
            game.printBoard();
            if(game.isWinner()==true)
                return;
        }
        else{
            count++;
            Board[ypos][xpos] = 'O';
            game.printBoard();
            if(game.isWinner()==true)
                return;
        }

        if(count >= 16){
            System.out.println("Maximum moves reached. Game over. Tie Game.\n");
            return;
        }
        else game.setPos(keyboard, track, input, game);
    }

    // Check to see if all four marks are the same, and not empty, indicating a win
    public boolean checkMark(char c1, char c2, char c3, char c4) {
        return ((c1 != '!') && (c1 == c2) && (c2 == c3)) && (c3 == c4);
    }

    // When called, all of the following check methods are called together to check the entire board
    // for a win. It calls methods to check rows, column, diagonals, and 2by2 squares
    public boolean isWinner() {
        if (checkRows() || checkCols() || checkDiags() || check2by2() == true) {
            System.out.println("CONGRATULATIONS ON WINNING!!");
            return true;
        }
        else return false;
    }

    // Check all diagonals and determine if there are any winners
    public boolean checkDiags(){
        return ((checkMark(Board[0][0], Board[1][1], Board[2][2], Board[3][3]) == true) ||
                (checkMark(Board[0][3], Board[1][2], Board[2][1], Board[3][0]) == true));
    }

    // Check all rows and determine if there are any winners
    public boolean checkRows() {
        for (int i = 0; i < 4; i++) {
            if (checkMark(Board[i][0], Board[i][1], Board[i][2], Board[i][3]) == true) {
                return true;
            }
        }
        return false;
    }

    // Check all columns and determine if there are any winners
    public boolean checkCols() {
        for (int i = 0; i < 4; i++) {
            if (checkMark(Board[0][i], Board[1][i], Board[2][i], Board[3][i]) == true) {
                return true;
            }
        }
        return false;
    }

    // Check all 2by2 squares and determine if there are any winners
    public boolean check2by2() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                if (checkMark(Board[i][j], Board[i][j+1], Board[i+1][j], Board[i+1][j+1]) == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public void reset(){
        count = 0;

        for(int i = 0; i < Rows; i++)
            for (int j = 0; j < Cols; j++)
                Board[i][j] = '!';

        for(int i = 0; i < Rows; i++)
            for (int j = 0; j < Cols; j++)
                wasThere[i][j] = true;
    }

    //Driver program, create TicTacToe object and begin game
    public static void main (String args[]){
        Scanner keyboard = new Scanner(System.in);
        String input = " ";
        boolean track = true;

        TicTacToe game = new TicTacToe();

        System.out.println("\t\tWelcome to 4x4 TicTacToe!!!!\n");
        System.out.println("RULES:");
        System.out.println("1.\tA win consists of 4 X's or O's in a vertical, horizontal, or diagonal line.\n"
                + "\tIN ADDITION any 2x2 groupings of X's or O's is also considered a WIN.\n");
        System.out.println("2.\tX goes first followed by O.\n");
        System.out.println("3.\tAt any point in the game, you can end it by entering \"$\".\n");
        System.out.println("Would you like to play a game? Press (y/n) followed by Enter to begin.\t\n");

        //Accept user input if they want to play
        input = keyboard.next();
        char choice = input.toLowerCase().charAt(0);

        //Evaluate user input
        while(track){

            if (choice == 'y') {
                track = false;
                game.reset();
                game.printBoard();
                System.out.println("Lets begin!");
                game.setPos(keyboard, track, input, game);
            } else if (choice == 'n'|| choice == '$') {
                track = false;
                System.out.println("Program terminated.");
                break;
            } else {
                System.out.println("Invalid entry. Please re-enter (y/n) followed by Enter.");
                input = keyboard.next();
                choice = input.toLowerCase().charAt(0);
                continue;
            }

            System.out.println("Would you like to play another game? Please re-enter (y/n) followed by Enter.");
            track = true;
            input = keyboard.next();
            choice = input.toLowerCase().charAt(0);
        }

        System.out.println("\n\t\tTHANK YOU FOR PLAYING!!! :) :) :)\n\n");
    }
}
