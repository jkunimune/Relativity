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
  public final boolean collideWith(Body b) {
    if (Math.random()*11 >= getM()) { // smaller asteroids are often incinerated on impact
      shoot(new Explosion(getX(), getY(), Math.pow(getM()/10.0, 1/3.0), getUniverse())); // explodes
      return true;
    }
    else {
      eject(new InertBody(getX(), getY(), getUniverse().randomSpeed(), Math.random()*2*Math.PI, // otherwise they throw off pieces
                          Math.log(1/Math.random()-1)/10000, (.5-Math.random()/3)*getM()/10, getUniverse()));
      shoot(new Explosion(getX(), getY(), Math.pow(getM()/10.0, 1/3.0), getUniverse())); // explodes
      return false;
    }
  }
  
  
  @Override
  public final String getFilePath() {
    return "textures/debris"+(int)(Math.random()*5)+".png";
  }
}