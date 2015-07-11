import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;




public abstract class Body { // a class for any physical object
  private double mass;
  private double xPosition;
  private double yPosition;
  private double xVelocity;
  private double yVelocity;
  private BufferedImage sprite;
  
  
  
  
  public Body (double newX, double newY, double newVX, double newVY, double newM) {
    try {
      sprite = ImageIO.read(new File(getFilePath()));
    }
    catch (IOException e) {
      System.out.println(e+": "+getFilePath());
    }
    
    xPosition = newX;
    yPosition = newY;
    xVelocity = newVX;
    yVelocity = newVY;
    mass = newM;
  }
  
  
  
  
  public void update(double delT) { // updates the object by delT milliseconds and keeps them on screen
    xVelocity += getAX();
    yVelocity += getAY();
    xPosition += xVelocity;
    yPosition += yVelocity;
    
    xPosition %= Space.WIDTH;
    yPosition %= Space.LENGTH;
  }
  
  
  public abstract String getFilePath(); // for finding sprite files
  
  
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
  
  
  public final BufferedImage getSprite() { // returns the sprite
    return sprite;
  }
  
  
  public final int getScreenX() { // just the regular x, but shifted back
    return (int)xPosition - Space.WIDTH/2;
  }
  
  
  public final int getScreenY() { // ditto for y
    return (int)yPosition - Space.LENGTH/2;
  }
}