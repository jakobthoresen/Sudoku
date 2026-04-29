package no.uib.inf101.sample.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import no.uib.inf101.sample.datastructure.CellPosition;
import no.uib.inf101.sample.datastructure.CellPositionToPixelConverter;
import no.uib.inf101.sample.datastructure.GridDimension;
import no.uib.inf101.sample.model.SudokuCell;
import no.uib.inf101.sample.model.SudokuModel;

/**
 * A view of a grid
 */
public class SudokuView extends JPanel {

  // All needed constants for layout
  private static final int PREFERRED_SIZE = 600;
  private static final int OUTER_MARGIN = 20;
  private static final int INNER_MARGIN = 0;
  private static final int BOX_STROKE = 3;
  private static final int FONT_SIZE = 24;

  // All needed constants for colors
  private static final Color COLOR_SELECTED = new Color(255, 243, 150); // Light Yellow
  private static final Color COLOR_CELL_BG = Color.WHITE;
  private static final Color COLOR_FIXED_NUM = Color.BLACK;
  private static final Color COLOR_FIXED_BG = Color.LIGHT_GRAY;
  private static final Color COLOR_USER_NUM = Color.BLUE;
  private static final Color COLOR_GRID_LINE = Color.BLACK;

  private SudokuModel model;

  /** Construct a new View */
  public SudokuView(SudokuModel model) {
    this.model = model;
    this.setPreferredSize(new Dimension(PREFERRED_SIZE, PREFERRED_SIZE));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    drawBoard(g2);
  }

  private void drawBoard(Graphics2D g2) {
    CellPositionToPixelConverter converter = this.getCellPositionToPixelConverter();
    
    for (int row = 0; row < model.getDimension().rows(); row++) {
      for (int col = 0; col < model.getDimension().cols(); col++) {
        CellPosition pos = new CellPosition(row, col);
        
        SudokuCell cell = model.getCell(pos); 
        Rectangle2D box = converter.getBoundsForCell(pos);

        // Fills the cell background (grey if fixed, light grey if selected, white otherwise) 
        g2.setColor(getBackgroundColor(pos));
        g2.fill(box);

        // Draws Cell border
        g2.setColor(COLOR_GRID_LINE);
        g2.setStroke(new BasicStroke(1));
        g2.draw(box);

        // Draw the number in the cell, if zero: keep blank (white)
        if (cell.value() != 0) {
          drawNumber(g2, box, cell);
        }
      }
    }
    // Drawing the thickened lines between the 3x3 boxes
    drawBoxBoarders(g2);
  }

  private void drawNumber(Graphics2D g2, Rectangle2D box, SudokuCell cell) {
      
      g2.setColor(cell.isFixed() ? Color.BLACK : Color.BLUE);
      g2.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));

      String text = String.valueOf(cell.value());
      FontMetrics fm = g2.getFontMetrics();

      // Centers the number in each cell
      float x = (float) (box.getX() + (box.getWidth() - fm.stringWidth(text)) / 2);
      float y = (float) (box.getY() + (box.getHeight() + fm.getAscent()) / 2 - fm.getDescent());

      g2.drawString(text, x, y);
    }

    private void drawBoxBoarders(Graphics2D g2) {
      CellPositionToPixelConverter converter = this.getCellPositionToPixelConverter();

      double boardLeft = converter.getBoundsForCell(new CellPosition(0, 0)).getMinX();
      double boardRight = converter.getBoundsForCell(new CellPosition(0, 8)).getMaxX();
      double boardTop = converter.getBoundsForCell(new CellPosition(0, 0)).getMinY();
      double boardBottom = converter.getBoundsForCell(new CellPosition(8, 0)).getMaxY();

      g2.setColor(COLOR_GRID_LINE);
      g2.setStroke(new BasicStroke(BOX_STROKE));


      // Draw the vertical lines that surround the 3x3 boxes
      for (int col: new int[]{0,3,6}) {
        double x = converter.getBoundsForCell(new CellPosition(0, col)).getMinX();
        
        g2.draw(new Line2D.Double(x, boardTop, x, boardBottom));
        g2.draw(new Line2D.Double(boardRight, boardTop, boardRight, boardBottom));
      }

      for (int row: new int[]{0,3,6}) {
        double y = converter.getBoundsForCell(new CellPosition(row, 0)).getMinY();
        
        g2.draw(new Line2D.Double(boardLeft, y, boardRight, y));
        g2.draw(new Line2D.Double(boardLeft, boardBottom, boardRight, boardBottom));
      }

    }

    private Color getBackgroundColor(CellPosition pos) {
      if (pos.equals(model.getSelected())) return COLOR_SELECTED;
      if (model.getCell(pos).isFixed()) return COLOR_FIXED_BG;
      return COLOR_CELL_BG;
    }
  /**
   * Gets an object which converts between CellPosition in a grid and 
   * their pixel positions on the screen.
   */
  public CellPositionToPixelConverter getCellPositionToPixelConverter() {
    Rectangle2D bounds = new Rectangle2D.Double(
        OUTER_MARGIN,
        OUTER_MARGIN,
        this.getWidth() - 2 * OUTER_MARGIN,
        this.getHeight() - 2 * OUTER_MARGIN);
    GridDimension gridSize = this.model.getDimension();
    return new CellPositionToPixelConverter(bounds, gridSize, INNER_MARGIN);
  }
}
