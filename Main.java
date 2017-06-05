package kri;

import javax.swing.*;
import java.awt.*;


public class Main {
  private Canvas panel;
  private JPanel panelBattons;

  public static void main(String[] args) {
    Main gui = new Main();
    gui.go();
  }

  private void go() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    panel = new Canvas();

    frame.setSize(640, 480);
    frame.setTitle("Bezier Curves");

    panelBattons = new JPanel();
    panelBattons.setBackground(Color.GRAY);
    panelBattons.setLayout(new BoxLayout(panelBattons, BoxLayout.Y_AXIS));

    JButton plotAButton = new JButton("Plot Animation");
    plotAButton.addActionListener(e -> panel.plotAnimation());

    JButton plotButton = new JButton("     Plot curve   ");
    plotButton.addActionListener(e -> panel.plot());

    JButton splineButton = new JButton("   Plot spline   ");
    splineButton.addActionListener((event) -> panel.plotSpline());

    JButton eraseButton = new JButton("    Erase all!!    ");
    eraseButton.addActionListener((event) -> panel.erase());

    JButton eraseLastButton = new JButton("   Erase last!!   ");
    eraseLastButton.addActionListener((event) -> panel.eraseLast());

    JButton eraseSplineButton = new JButton(" Erase spline!! ");
    eraseSplineButton.addActionListener((event) -> panel.eraseSpline());

    panelBattons.add(plotButton);
    panelBattons.add(plotAButton);
    //panelBattons.add(splineButton);
    panelBattons.add(eraseButton);
    panelBattons.add(eraseLastButton);
    //panelBattons.add(eraseSplineButton);

    frame.getContentPane().add(panel);
    frame.getContentPane().add(BorderLayout.EAST, panelBattons);
    frame.setVisible(true);
  }

}
