import java.awt.geom.*;
import java.awt.image.*;




public class InertBody extends Body { // a body of mass that drifts helplessly through space
  public InertBody(double newX, double newY, double p, double th, double l, double m, Space s) {
    super(newX, newY, p/m*Math.cos(th), p/m*Math.sin(th), l/Math.pow(m, 5.0/3.0), m*10, s, m);
  }
  
  
  
  
  public final double getAX() {
    return 0;
  }
  
  
  public final double getAY() {
    return 0;
  }
  
  
  public final String getFilePath() {
    return "textures/debris"+(int)(Math.random()*3)+".png";
  }
}