import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;




public class Space extends ArrayList<Body> { // a class that contains all the physical information of the universe
  public static final double C = 1.0; // the speed of light to a stationary observer
  public static final double G = 1.0; // Newton's universal gravitation constant
  
  private Body me;
  
  
  
  
  public Space() {
    me = new RocketShip();
    this.add(me);
  }
  
  
  
  
  public void update(double delT) {
  }
}