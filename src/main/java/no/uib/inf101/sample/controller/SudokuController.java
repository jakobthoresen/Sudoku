package no.uib.inf101.sample.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JOptionPane;

import no.uib.inf101.sample.datastructure.CellPosition;
import no.uib.inf101.sample.datastructure.CellPositionToPixelConverter;
import no.uib.inf101.sample.model.SudokuModel;
import no.uib.inf101.sample.view.SudokuView;

public class SudokuController extends MouseAdapter implements KeyListener {
  
  private final SudokuModel model;
  private final SudokuView view;

  /**
   * Construct a controller recating to key presses.
   * 
   * @param model model to update on key press
   * @param view view to listen to key presses in, and to be repainted when model changes
   */
  public SudokuController(SudokuModel model, SudokuView view) {
    this.model = model;
    this.view = view;

    // Register the controller as listener for both mouse and keyboard inputs 
    this.view.addMouseListener(this);
    this.view.addKeyListener(this);

    this.view.setFocusable(true);
  }


  @Override
  public void keyPressed(KeyEvent e) {
    char keyChar = e.getKeyChar();

    if (Character.isDigit(keyChar) && keyChar != '0') {
      int number = Character.getNumericValue(keyChar);
      this.model.setNumber(number);
    }
    else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
      this.model.setNumber(0);
    }
    
    this.view.repaint();

    if (model.isSolved()) {
      JOptionPane.showMessageDialog(view, "Congratulations, sudoku solved!");
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {}


  @Override
  public void keyTyped(KeyEvent e) {}


  @Override
  public void mousePressed(MouseEvent event) {
    Point2D mouseCoordinate = event.getPoint();
    CellPositionToPixelConverter converter = this.view.getCellPositionToPixelConverter();
    CellPosition pos = converter.getCellPositionOfPoint(mouseCoordinate);
    this.model.setSelected(pos);
    this.view.repaint();

    this.view.requestFocusInWindow();
  }
}
