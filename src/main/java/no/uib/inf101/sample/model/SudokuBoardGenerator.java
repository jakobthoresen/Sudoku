package no.uib.inf101.sample.model;

import java.util.Random;

import no.uib.inf101.sample.datastructure.CellPosition;
import no.uib.inf101.sample.datastructure.IGrid;

/**
 * Generates a legal SudokuBoard, and checks its validity using backtracking.
 */
public class SudokuBoardGenerator {

    private static final int SIZE = 9;
    
    private static final int[][] SEED_BOARD = {
        {5, 3, 4, 6, 7, 8, 9, 1, 2},
        {6, 7, 2, 1, 9, 5, 3, 4, 8},
        {1, 9, 8, 3, 4, 2, 5, 6, 7},
        {8, 5, 9, 7, 6, 1, 4, 2, 3},
        {4, 2, 6, 8, 5, 3, 7, 9, 1},
        {7, 1, 3, 9, 2, 4, 8, 5, 6},
        {9, 6, 1, 5, 3, 7, 2, 8, 4},
        {2, 8, 7, 4, 1, 9, 6, 3, 5},
        {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };

    /**
     * Generates a new sudoku puzzle by shuffling the seed board, then removing cells
     * the generated board is solvable using recursion and backtracking
     * @param grid the grid to be filled with the puzzle
     * @return a 2D array representing the solution board (a fully filled out board)
     */
    public static int[][] fillGrid(IGrid<SudokuCell> grid) {
        Random rand = new Random();
        int[][] board = copySolution(SEED_BOARD);

        shuffleBoard(board, rand);
        fillCells(grid, board);

        int[][] solution = copySolution(board);
        removeRandomCells(grid, rand, 40);

        int[][] puzzle = new int[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                puzzle[r][c] = grid.get(new CellPosition(r, c)).value();
            }
        }
        if (!isSolvable(puzzle)) return fillGrid(grid);
        
        return solution;
    }

    // Fills in the board into the grid
    private static void fillCells(IGrid<SudokuCell> grid, int[][] board) {
        
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                CellPosition pos = new CellPosition(r, c);
                grid.set(pos, new SudokuCell(pos, board[r][c], true));
            }
        }
    }

    // Helper function removing *count* numbers from the fully completed board to create the puzzle 
    // selects random row and colum, and replaces the fixed cell with a new empty cell
    private static void removeRandomCells(IGrid<SudokuCell> grid, Random rand, int count) {
        int remaining = count; 
        while (remaining > 0) {
            int r = rand.nextInt(SIZE);
            int c = rand.nextInt(SIZE);
            CellPosition pos = new CellPosition(r, c);
            
            if (grid.get(pos).value() != 0) {
                grid.set(pos, new SudokuCell(pos, 0, false));
                remaining--;
            }
        }
    }

    // Helper function for shuffling the board within the 3x3 blocks, keeping the board legal
    private static void shuffleBoard(int[][] board, Random rand) {
        for (int i = 0; i < 10; i++) {
            int block = rand.nextInt(3) * 3;
            int r1 = block + rand.nextInt(3);
            int r2 = block + rand.nextInt(3);
            
            int[] temp = board[r1];
            board[r1] = board[r2];
            board[r2] = temp;
        }
    }

    // Uses recursive backtracking to check if the current board has a solution
    private static boolean isSolvable(int[][] board) {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if(isValidPlacement(board, r, c, num)) {
                            board[r][c] = num;
                            if (isSolvable(board)) return true;
                            board[r][c] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValidPlacement(int[][] board, int r, int c, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[r][i] == num) return false;
            if (board[i][c] == num) return false;
        }
        int boxRow = (r / 3) * 3;
        int boxCol = (c / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[boxRow + i][boxCol + j] == num) return false;
            }
        }
        return true;
    }

    // Helper function copying the board so i can compare a player solution to actual solution
    private static int[][] copySolution(int[][] board) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, SIZE);
        }
        return copy;
    }
}
