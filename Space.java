import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;




public class Space extends ArrayList<Body> { // a class that contains all the physical information of the universe
  public static final double m = .000005; // pixels
  public static final double s = 1000; // miliseconds
  public static final double C = 300000000*m/s; // the speed of light to a stationary observer
  public static final double G = 1.0; // Newton's universal gravitation constant
  public static final int WIDTH = 4095; // the width of the universe, which is finite to prevent the computer from exploding
  public static final int LENGTH = 4095; // the other dimension.
  
  private RocketShip me;
  
  
  
  
  public Space() {
    me = new RocketShip(WIDTH/2, LENGTH/2, this);
    this.add(me);
  }
  
  
  
  
  public void update(double delT) {
    for (int i = 0; i < this.size(); i ++)
      get(i).update(delT/get(i).getG());
  }
  
  
  public RocketShip getReference() {
    return me;
  }
}