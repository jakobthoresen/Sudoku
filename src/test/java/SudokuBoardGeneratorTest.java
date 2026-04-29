
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.datastructure.CellPosition;
import no.uib.inf101.sample.datastructure.Grid;
import no.uib.inf101.sample.datastructure.GridDimension;
import no.uib.inf101.sample.datastructure.IGrid;
import no.uib.inf101.sample.model.SudokuBoardGenerator;
import no.uib.inf101.sample.model.SudokuCell;

public class SudokuBoardGeneratorTest {
    /**
     * Tests {@link SudokuBoardGenerator}
     */
    @Test
    void fixedCellsAreNotZero() {

        GridDimension gd = new GridDimension.Record(9, 9);
        IGrid<SudokuCell> grid = new Grid<>(gd, null);

        SudokuBoardGenerator.fillGrid(grid);
        
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                CellPosition pos = new CellPosition(r,c);
                SudokuCell cell = grid.get(pos);

                if (cell.isFixed()) {
                    assertNotEquals(0, cell.value());
                }
            }
        }
    }

    @Test
    void solutionHasNoZeroes() {

        GridDimension gd = new GridDimension.Record(9, 9);
        IGrid<SudokuCell> grid = new Grid<>(gd, null);

        int[][] solution = SudokuBoardGenerator.fillGrid(grid);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                assertNotEquals(0, solution[r][c]);
            }
        }
    }

    @Test
    void solutionMatchesFixedCells() {

        GridDimension gd = new GridDimension.Record(9, 9);
        IGrid<SudokuCell> grid = new Grid<>(gd, null);

        int[][] solution = SudokuBoardGenerator.fillGrid(grid);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                SudokuCell cell = grid.get(new CellPosition(r, c));
                
                if (cell.isFixed()) {
                    assertEquals(solution[r][c], cell.value());
                }
            }
        }
    }
}
