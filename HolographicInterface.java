import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;




public class HolographicInterface { // a class to render and display tactical information (overhead view)
  public static final int WIDTH = 1280;
  public static final int HEIGHT = 800;
  private JFrame frame;
  private JPanel panel;
  private Canvas canvs;
  private BufferStrategy strat;
  
  private Space universe;
  private BufferedImage background;
  
  
  
  
  public HolographicInterface(Space wholeNewUniverse) {
    final ShipControls listener = new ShipControls(wholeNewUniverse, wholeNewUniverse.getReference());
    frame = new JFrame("Relativity");
    panel = new JPanel();
    canvs = new Canvas();
    
    panel.setLayout(null);
    
    canvs.setBounds(0, 0, WIDTH, HEIGHT);
    canvs.setIgnoreRepaint(true);
    canvs.addKeyListener(listener);
    canvs.addMouseListener(listener);
    canvs.setFocusable(true); // Sets the canvas to focussable (Necessary for a key listener).
    
    panel.add(canvs);
    panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    
    frame.add(panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(WIDTH, HEIGHT);
    frame.setVisible(true);
    frame.pack();
    
    canvs.createBufferStrategy(2);
    strat = canvs.getBufferStrategy();
    
    universe = wholeNewUniverse;
    
    try {
      background = ImageIO.read(new File("textures/space.png"));
    }
    catch (IOException e) {
      System.out.println(e+": textures/space.png");
    }
  }
  
  
  
  
  public void display() {
    Graphics2D g = (Graphics2D) strat.getDrawGraphics();
    
    g.clearRect(0, 0, WIDTH, HEIGHT); // Clears the screen
    g.drawImage(background, 0, 0, null); // Draws the background first and everything else on top of it
    
    for (Body b : universe) {
      g.drawImage(b.getSprite(), (int)b.getScreenX(), (int)b.getScreenY(), null); // Draws all of the enemies currently alive.
    }
    
    g.dispose();
    strat.show(); // Shows the screen.
  }
}