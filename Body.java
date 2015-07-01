import java.awt.image.*;




public abstract class Body { // a class for any physical object
  private double mass;
  private double xPosition;
  private double yPosition;
  private double xVelocity;
  private double yVelocity;
  
  
  
  
  public Body () {
  }
  
  
  
  
  public void update(double delT) { // updates the object by delT milliseconds
    xVelocity += getAX();
    yVelocity += getAY();
    xPosition += xVelocity;
    yPosition += yVelocity;
  }
  
  
  
  
  public abstract String getName(); // for finding sprite files
  
  
  public abstract double getAX(); // returns the x-component of the acceleration vector
  
  
  public abstract double getAY(); // returns the y-component of the acceleration vector
  
  
  public final double getM() { // returns the mass
    return mass;
  }
  
  
  public final double getX() { // returns the x-component of the position vector
    return xPosition;
  }
  
  
  public final double getY() { // returns the y-component of the position vector
    return yPosition;
  }
  
  
  public final double getVX() { // returns the x-component of the velocity vector
    return xVelocity;
  }
  
  
  public final double getVY() { // returns the y-component of the velocity vector
    return yVelocity;
  }
  
  
  public double getV() { // returns the magnitude of the velocity vector
    return Math.hypot(xVelocity, yVelocity);
  }
  
  
  public double getG() { // returns the lorentz factor (G stands for Gamma)
    return 1.0 / Math.sqrt(1 - (xVelocity*xVelocity+yVelocity*yVelocity)/(Space.C*Space.C));
  }
  
  
  public final BufferedImage getRawSprite() { // returns the sprite
    return null; // implement later with File reading code
  }
}