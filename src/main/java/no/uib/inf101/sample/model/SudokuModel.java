package no.uib.inf101.sample.model;

import no.uib.inf101.sample.datastructure.CellPosition;
import no.uib.inf101.sample.datastructure.Grid;
import no.uib.inf101.sample.datastructure.GridDimension;
import no.uib.inf101.sample.datastructure.IGrid;

/**
 * The model contains two bits of data: the size of the grid to draw, and
 * which cell in the grid is currently highlighted.
 */
public class SudokuModel {
  
  private GridDimension gd = new GridDimension.Record(9, 9);
  private CellPosition selectedPosition = null;

  private final IGrid<SudokuCell> grid;

  public SudokuModel() {
    this.grid = new Grid<>(gd, null);

    for (int r = 0; r < gd.rows(); r++) {
      for (int c = 0; c < gd.cols(); c++) {
        CellPosition pos = new CellPosition(r, c);

        grid.set(pos, new SudokuCell(pos, 0, false));
          
      }
    }
        
    }

  public void setNumber(int number) {
    if (selectedPosition != null) {
      SudokuCell currentCell = grid.get(selectedPosition);

      if(!currentCell.isFixed()) {
        SudokuCell newCell = new SudokuCell(selectedPosition, number, false);
        grid.set(selectedPosition, newCell);
    }
    }

    
  }
  

  /**
   * Set the selected position in the grid.
   * 
   * @param selectedPosition new position to be selected, or null if new selection
   *                         should be the empty selection
   */
  public void setSelected(CellPosition selectedPosition) {
    this.selectedPosition = selectedPosition;
  }

  /** Gets the dimension of the grid. */
  public GridDimension getDimension() {
    return this.gd;
  }

  /** Gets the selected cell in the grid.  */
  public CellPosition getSelected() {
    return this.selectedPosition;
  }

}
