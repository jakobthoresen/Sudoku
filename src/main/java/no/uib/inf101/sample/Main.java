package no.uib.inf101.sample;

import javax.swing.JFrame;

/**
 * Main class showing the demo for a clickable grid
 */
public class Main {
  public static void main(String[] args) {
    Model model = new Model();
    View view = new View(model);
    new Controller(model, view);

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("INF101");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);
  }
}
