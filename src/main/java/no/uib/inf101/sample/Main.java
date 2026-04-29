package no.uib.inf101.sample;

import javax.swing.JFrame;

import no.uib.inf101.sample.controller.SudokuController;
import no.uib.inf101.sample.model.SudokuModel;
import no.uib.inf101.sample.view.SudokuView;

/**
 * Main class showing the demo for a clickable grid
 */
public class Main {
  public static void main(String[] args) {
    SudokuModel model = new SudokuModel();
    SudokuView view = new SudokuView(model);
    new SudokuController(model, view);

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("INF101");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);
  }
}
