import java.awt.geom.*;
import java.awt.image.*;




public class Explosion extends Body { // a purely asthetic ball of fire
  private double radius; // the constantly decreasing radius, between 0 and 1
  
  
  
  public Explosion(double newX, double newY, double newR, Space s) {
    super(newX, newY, 0, 0, 0, 0, s, newR, Math.random()*2*Math.PI);
    radius = newR;
  }
  
  
  
  @Override
  public final double getAX() {
    return 0;
  }
  
  
  @Override
  public final double getAY() {
    return 0;
  }
  
  
  @Override
  public final boolean canCollideWith(Body b) {
    return false;
  }
  
  
  @Override
  public final boolean collideWith(Body b) {
    return false;
  }
  
  
  @Override
  public final String getFilePath() {
    return "textures/blast.png";
  }
  
  
  @Override
  public final boolean update(double delT) {
    super.update(delT);
    radius -= delT/500;
    if (radius <= 0)  return true;
    shrinkSprite(radius/(radius+delT/500));
    return false;
  }
}