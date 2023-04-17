package no.uib.inf101.sample;

/** 
 * A GridDimension is the dimension of a grid. It has methods for
 * retreiving the number of rows and the number of columns.
 */
public interface GridDimension {

  /** Number of rows in the grid */
  int rows();

  /** Number of columns in the grid */
  int cols();

  public record Record(int rows, int cols) implements GridDimension {}
}
