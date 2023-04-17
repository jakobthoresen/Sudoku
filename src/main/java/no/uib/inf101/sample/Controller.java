package no.uib.inf101.sample;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class Controller extends MouseAdapter {
  
  private Model model;
  private View view;

  /**
   * Construct a controller recating to key presses.
   * 
   * @param model model to update on key press
   * @param view view to listen to key presses in, and to be repainted when model changes
   */
  public Controller(Model model, View view) {
    this.model = model;
    this.view = view;
    this.view.addMouseListener(this);
  }

  @Override
  public void mousePressed(MouseEvent event) {
    Point2D mouseCoordinate = event.getPoint();
    CellPositionToPixelConverter converter = this.view.getCellPositionToPixelConverter();
    CellPosition pos = converter.getCellPositionOfPoint(mouseCoordinate);
    this.model.setSelected(pos);
    this.view.repaint();
  }
}
