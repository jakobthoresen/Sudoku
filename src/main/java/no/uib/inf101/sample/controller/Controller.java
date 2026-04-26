package no.uib.inf101.sample.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import no.uib.inf101.sample.datastructure.CellPosition;
import no.uib.inf101.sample.datastructure.CellPositionToPixelConverter;
import no.uib.inf101.sample.model.SudokuModel;
import no.uib.inf101.sample.view.View;

public class Controller extends MouseAdapter {
  
  private SudokuModel model;
  private View view;

  /**
   * Construct a controller recating to key presses.
   * 
   * @param model model to update on key press
   * @param view view to listen to key presses in, and to be repainted when model changes
   */
  public Controller(SudokuModel model, View view) {
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
