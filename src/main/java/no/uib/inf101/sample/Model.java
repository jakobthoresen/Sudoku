package no.uib.inf101.sample;

/**
 * The model contains two bits of data: the size of the grid to draw, and
 * which cell in the grid is currently highlighted.
 */
public class Model {
  
  private GridDimension gd = new GridDimension.Record(5, 10);
  private CellPosition selectedPosition = null;

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
