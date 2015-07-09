import java.awt.*;
import java.awt.image.*;
import javax.swing.*;




public class HolographicInterface { // a class to render and display tactical information (overhead view)
  private JFrame frame;
  private JPanel panel;
  private Canvas canvs;
  private BufferStrategy strat;
  private ShipControls listener;
  
  
  
  
  public HolographicInterface(Space universe, int width, int height) {
    frame = new JFrame("Relativity");
    panel = new JPanel();
    canvs = new Canvas();
    
    panel.setLayout(null);
    
    canvs.setBounds(0, 0, width, height);
    canvs.setIgnoreRepaint(true);
    canvs.addKeyListener(listener);
    canvs.addMouseListener(listener);
    canvs.setFocusable(true); // Sets the canvas to focussable (Necessary for a key listener).
    
    panel.add(canvs);
    panel.setPreferredSize(new Dimension(width, height));
    
    frame.add(panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(width, height);
    frame.setVisible(true);
    frame.pack();
    
    canvs.createBufferStrategy(2);
    strat = canvs.getBufferStrategy();
  }
  
  
  
  
  public void display() {
  }
}