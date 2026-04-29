package grid;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.datastructure.CellPosition;
import no.uib.inf101.sample.datastructure.Grid;
import no.uib.inf101.sample.datastructure.GridCell;
import no.uib.inf101.sample.datastructure.GridDimension;
import no.uib.inf101.sample.datastructure.IGrid;

/**
 * Testing the class {@link Grid}
 * (Reused from Semesterassingment 1, but changed to Generics)
 */
public class GridTest {

  @Test
  void gridTestGetRowsAndCols() {
    // Bruker GridDimension.Record for å matche din konstruktør
    GridDimension gd = new GridDimension.Record(3, 2);
    IGrid<Integer> grid = new Grid<>(gd, null);
    
    assertEquals(3, grid.rows());
    assertEquals(2, grid.cols());
  }

  @Test
  void gridSanityTest() {
    Character defaultValue = 'x';
    GridDimension gd = new GridDimension.Record(3, 2);
    IGrid<Character> grid = new Grid<>(gd, defaultValue);
    
    assertEquals(3, grid.rows());
    assertEquals(2, grid.cols());
    
    assertEquals('x', grid.get(new CellPosition(0, 0)));
    assertEquals('x', grid.get(new CellPosition(2, 1)));
    
    grid.set(new CellPosition(1, 1), 'y');
    
    assertEquals('y', grid.get(new CellPosition(1, 1)));
    assertEquals('x', grid.get(new CellPosition(0, 1)));
    assertEquals('x', grid.get(new CellPosition(1, 0)));
    assertEquals('x', grid.get(new CellPosition(2, 1)));
  }

  @Test
  void gridCanHoldNull() {
    Character defaultValue = 'x';
    GridDimension gd = new GridDimension.Record(3, 2);
    IGrid<Character> grid = new Grid<>(gd, defaultValue);
    
    grid.set(new CellPosition(1, 1), null);
    
    assertEquals(null, grid.get(new CellPosition(1, 1)));
    assertEquals('x', grid.get(new CellPosition(0, 1)));
  }

  @Test
  void coordinateIsOnGridTest() {
    GridDimension gd = new GridDimension.Record(3, 2);
    IGrid<Character> grid = new Grid<>(gd, 'a');
    
    assertTrue(grid.positionIsOnGrid(new CellPosition(2, 1)));
    assertFalse(grid.positionIsOnGrid(new CellPosition(3, 1)));
    assertFalse(grid.positionIsOnGrid(new CellPosition(2, 2)));
    
    assertTrue(grid.positionIsOnGrid(new CellPosition(0, 0)));
    assertFalse(grid.positionIsOnGrid(new CellPosition(-1, 0)));
    assertFalse(grid.positionIsOnGrid(new CellPosition(0, -1)));
  }

  @Test
  void throwsExceptionWhenCoordinateOffGrid() {
    GridDimension gd = new GridDimension.Record(3, 2);
    IGrid<Character> grid = new Grid<>(gd, 'x');
    
    // Bruker assertThrows som er den moderne JUnit-måten å teste exceptions på
    assertThrows(IndexOutOfBoundsException.class, () -> {
      grid.get(new CellPosition(3, 1));
    });
  }

  @Test
  void testIterator() {
    GridDimension gd = new GridDimension.Record(3, 2);
    IGrid<Character> grid = new Grid<>(gd, 'x');
    grid.set(new CellPosition(0, 0), 'a');
    grid.set(new CellPosition(1, 1), 'b');
    grid.set(new CellPosition(2, 1), 'c');
    
    List<GridCell<Character>> items = new ArrayList<>();
    for (GridCell<Character> coordinateItem : grid) {
      items.add(coordinateItem);
    }
    
    assertEquals(3 * 2, items.size());
    
    // Sjekker at iteratoren inneholder spesifikke verdier
    boolean foundA = false;
    for (GridCell<Character> item : items) {
        if (item.pos().equals(new CellPosition(0, 0)) && item.elem().equals('a')) {
            foundA = true;
        }
    }
    assertTrue(foundA, "Skal finne 'a' på (0,0)");
  }
}
