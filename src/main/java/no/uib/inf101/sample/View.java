package no.uib.inf101.sample;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import javax.swing.JPanel;

/**
 * A view of a grid
 */
public class View extends JPanel {

  private static final int OUTER_MARGIN = 20;
  private static final int INNER_MARGIN = 5;
  private static final Color SELECTED_COLOR = Color.ORANGE.darker();
  private static final Color STD_COLOR = Color.BLACK;

  private Model model;

  /** Construct a new View */
  public View(Model model) {
    this.model = model;
    this.setPreferredSize(new Dimension(400, 150));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    drawBoard(g2);
  }

  private void drawBoard(Graphics2D g2) {
    CellPositionToPixelConverter converter = this.getCellPositionToPixelConverter();
    for (int row = 0; row < this.model.getDimension().rows(); row++) {
      for (int col = 0; col < this.model.getDimension().cols(); col++) {
        CellPosition pos = new CellPosition(row, col);
        Rectangle2D box = converter.getBoundsForCell(pos);

        Color color = Objects.equals(pos, this.model.getSelected()) ? SELECTED_COLOR : STD_COLOR;
        g2.setColor(color);
        g2.fill(box);
      }
    }
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
