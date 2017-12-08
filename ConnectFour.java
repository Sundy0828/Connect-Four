package com.projects;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * this is a game of connect four
 * @author Jerrod
 *
 * Created by jerrod on 10/7/16.
 */
public class ConnectFour {

    private static int[] dropLoc = new int[2];
    private static boolean dropped = false;

    public static void main(String[] args) {
        //make board
        String[][] board = {{"|"," ","|"," ","|"," ","|"," ","|"," ","|"," ","|"," ","|"},{"|"," ","|"," ","|"," ","|"," ","|"," ","|"," ","|"," ","|"},{"|"," ","|"," ","|"," ","|"," ","|"," ","|"," ","|"," ","|"},{"|"," ","|"," ","|"," ","|"," ","|"," ","|"," ","|"," ","|"},{"|"," ","|"," ","|"," ","|"," ","|"," ","|"," ","|"," ","|"},{"|"," ","|"," ","|"," ","|"," ","|"," ","|"," ","|"," ","|"}};
        Scanner theScanner = new Scanner(System.in);
        int column = 0, counter = 0;
        boolean validNum = true;
        boolean winner;
        String turn = "R";

        //continue game while there is no winner
        do {
            printBoard(board);

            //keep asking for valid column number to drop  piece in
            while (validNum) {
                System.out.print("Enter valid column number you wish to drop a piece in: ");
                column = theScanner.nextInt();
                validNum = !(column >= 1  && column <= 7);
            }

            //drop piece
            dropPiece(column * 2 - 1, board, turn);
            //each turn check board for winner
            winner = checkWinner(board, turn);
            // change turn if piece was dropped
            if (turn.equals("R") && dropped) {
                turn = "Y";
                counter += 1;
            }else {
                turn = "R";
                counter += 1;
            }

            if (counter == 42) {
                printBoard(board);
                System.out.println("This game was a tie!");
                break;
            }

            //reset valid number
            validNum = true;
        }while (!winner);

    }

    private static boolean checkWinner(String[][] board, String turn) {

        //set amended string to nothing and win to the dropped piece four times in a row RRRR or YYYY
        String amended = "", win = turn+turn+turn+turn;
        int[] bottomL = {dropLoc[0],dropLoc[1]}, bottomR = {dropLoc[0],dropLoc[1]};

        //make amended = to the row the piece was dropped in (horizontally)
        for (int i = 1; i <= 13; i += 2) {
            amended = amended + board[dropLoc[0]][i];
        }
        //if amended contains the win case then someone won
        if (amended.contains(win)) {
            printBoard(board);
            System.out.println("The winner is " + turn);
            return true;
        }
        //reset amended
        amended = "";

        //make amended = to the column the piece was dropped in (vertically)
        for (int i = 5; i >= 0; i--) {
            amended = amended + board[i][dropLoc[1]];
        }
        //if amended contains the win case then someone won
        if (amended.contains(win)) {
            printBoard(board);
            System.out.println("The winner is " + turn);
            return true;
        }
        //reset amended
        amended = "";


        int i = 0;
        //get the most bottom right that it could go
        do {
            //if within bounds of board then set new most bottom right location
            if (dropLoc[0] + i > 5 || dropLoc[1] + (i * 2) > 13) {
                break;
            }else {
                bottomR[0] = dropLoc[0] + i;
                bottomR[1] = dropLoc[1] + (i * 2);
            }
            i += 1;
        } while (true);

        i = 0;
        //get the most bottom left that it could go
        do {
            //if within bound of board then set new most bottom left
            if (dropLoc[0] + i > 5 || dropLoc[1] - (i * 2) < 1) {
                break;
            }else {
                bottomL[0] = dropLoc[0] + i;
                bottomL[1] = dropLoc[1] - (i * 2);
            }
            i += 1;
        } while (true);

        i = 0;
        //get amended string of bottom right to top left
        do {
            if (bottomR[0] - i < 0 || bottomR[1] - (i * 2) < 1) {
                break;
            }else {
                amended = amended + board[bottomR[0] - i][bottomR[1] - (i * 2)];
            }
            i += 1;
        } while (true);
        //check if amended contains the win
        if (amended.contains(win)) {
            printBoard(board);
            System.out.println("The winner is " + turn);
            return true;
        }

        amended = "";
        i = 0;
        //get amended string of bottom left to top right
        do {
            if (bottomL[0] - i < 0 || bottomL[1] + (i * 2) > 13) {
                break;
            }else {
                amended = amended + board[bottomL[0] - i][bottomL[1] + (i * 2)];
            }
            i += 1;
        } while (true);
        //check if amended contains the win
        if (amended.contains(win)) {
            printBoard(board);
            System.out.println("The winner is " + turn);
            return true;
        }






        //if none match win case then return false to continue game
        return false;

    }

    private static void printBoard(String[][] board) {
        //set column numbers
        String[] rowID = {" ","1"," ","2"," ","3"," ","4"," ","5"," ","6"," ","7"," "};

        //print column numbers
        for (String aRowID : rowID) {
            System.out.print(aRowID + " ");
        }
        System.out.println();

        //print columns |   |   |   |
        for (String[] aBoard : board) {
            for (String anABoard : aBoard) {
                System.out.print(anABoard + " ");
            }
            System.out.println();
        }
    }

    private static void dropPiece(int column, String[][] board, String turn) {

        //drop piece
        dropped = false;

        for (int i = 5; i >= 0; i--) {
            //if the board has an empty space then drop piece and say it was dropped
            if (Objects.equals(board[i][column], " ")) {
                board[i][column] = turn;
                dropLoc[0] = i;
                dropLoc[1] = column;
                dropped = true;
                break;
            }
        }
/*
        switch (column) {
            case 1:
                for (int i = 5; i >= 0; i--) {
                    //if the board has an empty space then drop piece and say it was dropped
                    if (Objects.equals(board[i][1], " ")) {
                        board[i][1] = turn;
                        dropLoc[0] = i;
                        dropLoc[1] = 1;
                        dropped = true;
                        break;
                    }
                }
                break;
            case 2:
                for (int i = 5; i >= 0; i--) {
                    //if the board has an empty space then drop piece and say it was dropped
                    if (Objects.equals(board[i][3], " ")) {
                        board[i][3] = turn;
                        dropLoc[0] = i;
                        dropLoc[1] = 3;
                        dropped = true;
                        break;
                    }
                }
                break;
            case 3:
                for (int i = 5; i >= 0; i--) {
                    //if the board has an empty space then drop piece and say it was dropped
                    if (Objects.equals(board[i][5], " ")) {
                        board[i][5] = turn;
                        dropLoc[0] = i;
                        dropLoc[1] = 5;
                        dropped = true;
                        break;
                    }
                }
                break;
            case 4:
                for (int i = 5; i >= 0; i--) {
                    //if the board has an empty space then drop piece and say it was dropped
                    if (Objects.equals(board[i][7], " ")) {
                        board[i][7] = turn;
                        dropLoc[0] = i;
                        dropLoc[1] = 7;
                        dropped = true;
                        break;
                    }
                }
                break;
            case 5:
                for (int i = 5; i >= 0; i--) {
                    //if the board has an empty space then drop piece and say it was dropped
                    if (Objects.equals(board[i][9], " ")) {
                        board[i][9] = turn;
                        dropLoc[0] = i;
                        dropLoc[1] = 9;
                        dropped = true;
                        break;
                    }
                }
                break;
            case 6:
                for (int i = 5; i >= 0; i--) {
                    //if the board has an empty space then drop piece and say it was dropped
                    if (Objects.equals(board[i][11], " ")) {
                        board[i][11] = turn;
                        dropLoc[0] = i;
                        dropLoc[1] = 11;
                        dropped = true;
                        break;
                    }
                }
                break;
            case 7:
                for (int i = 5; i >= 0; i--) {
                    //if the board has an empty space then drop piece and say it was dropped
                    if (Objects.equals(board[i][13], " ")) {
                        board[i][13] = turn;
                        dropLoc[0] = i;
                        dropLoc[1] = 13;
                        dropped = true;
                        break;
                    }
                }
                break;
        }
*/


    }
}
