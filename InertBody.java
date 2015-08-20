import java.awt.geom.*;
import java.awt.image.*;




public class InertBody extends Body { // a body of mass that drifts helplessly through space
  public InertBody(double newX, double newY, double p, double th, double l, double m, Space s) {
    super(newX, newY, p/m*Math.cos(th), p/m*Math.sin(th), l/Math.pow(m, 5.0/3.0), m*10, s, m);
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
    return b.getClass().getName().equals("Laser") || b.getClass().getName().equals("RocketShip");
  }
  
  
  @Override
  public final boolean collide() {
    shoot(new Explosion(getX(), getY(), getVX(), getVY(), Math.pow(getM()/10.0, 1/3.0), getUniverse()));
    
    if (Math.random()*11 >= getM()) // smaller asteroids are often incinerated on impact
      return true;
    
    eject(new InertBody(getX(), getY(), Math.log(1/Math.random()-1)/50, Math.random()*2*Math.PI,
                         Math.log(1/Math.random()-1)/10000, getM()*Math.random()/10, getUniverse()));
    return false;
  }
  
  
  @Override
  public final String getFilePath() {
    return "textures/debris"+(int)(Math.random()*5)+".png";
  }
}