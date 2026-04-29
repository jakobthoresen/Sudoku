
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.datastructure.CellPosition;
import no.uib.inf101.sample.model.SudokuCell;
import no.uib.inf101.sample.model.SudokuModel;

public class SudokuModelTest {

    /**
     * Tests {@link SudokuModel}
     */
    @Test
    void fixedCellIsFixed() {
        
        SudokuModel model = new SudokuModel();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                CellPosition pos = new CellPosition(r,c);
                SudokuCell cell = model.getCell(pos);

                if (cell.isFixed()) {
                    int original = cell.value();
                    model.setSelected(pos);
                    model.setNumber(7);

                    assertEquals(original, model.getCell(pos).value());
                }
            }
        }
            

    }

    @Test
    void notFixedCanChange() {

        SudokuModel model = new SudokuModel();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                CellPosition pos = new CellPosition(r,c);
                SudokuCell cell = model.getCell(pos);
            
                if (!cell.isFixed()) {

                    assertEquals(0, cell.value());

                    model.setSelected(pos);
                    int targetValue = model.getSolutionValue(pos);
                    model.setNumber(targetValue);

                    assertEquals(targetValue, model.getCell(pos).value());
                }
            }
        }
    }

    @Test
    void isCorrectTest() {

        SudokuModel model = new SudokuModel();
        
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                CellPosition pos = new CellPosition(r,c);
                SudokuCell cell = model.getCell(pos);
            
                if (!cell.isFixed()) {
                    assertFalse(model.isCorrect(pos));

                    model.setSelected(pos);
                    model.setNumber(model.getSolutionValue(pos));

                    assertTrue(model.isCorrect(pos));
                }
                else if (cell.isFixed()) {
                    assertTrue(model.isCorrect(pos));

                }    
            }
        }
    }

    @Test
    void isSolvedTest() {

        SudokuModel model = new SudokuModel();

        assertFalse(model.isSolved());
    }

    @Test
    void SetSelectedOutsideGridTest() {
        SudokuModel model = new SudokuModel();
        CellPosition invalidPosition = new CellPosition(-1, 10);

        model.setSelected(invalidPosition);

        assertEquals(null, model.getSelected());
    }
}