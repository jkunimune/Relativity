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
  private Space universe;
  private BufferedImage sprite;
  public AffineTransform contractor;
  private AffineTransformOp transOp;
  
  
  
  
  public Body (double newX, double newY, double newVX, double newVY, double newM, Space newU) {
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
    universe = newU;
  }
  
  
  
  
  public void update(double delT) { // updates the object by delT milliseconds and keeps them on screen
    xVelocity += getAX()*delT;
    yVelocity += getAY()*delT;
    xPosition += xVelocity*delT;
    yPosition += yVelocity*delT;
//    
//    xPosition = (xPosition + Space.WIDTH) % Space.WIDTH;
//    yPosition = (yPosition + Space.LENGTH) % Space.LENGTH;
  }
  
  
  public abstract String getFilePath(); // for finding sprite files
  
  
  public abstract double getAX(); // returns the x-component of the acceleration vector
  
  
  public abstract double getAY(); // returns the y-component of the acceleration vector
  
  
  public void shoot(Body projectile) { // causes the body to lose some momentum
    universe.add(projectile);
    xVelocity -= projectile.getVX()*projectile.getM()/this.mass/this.getG();
    yVelocity -= projectile.getVY()*projectile.getM()/this.mass/this.getG();
  }
  
  
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
  
  
  public Space getUniverse() { // returns the universe to which this object belongs
    return universe;
  }
  
  
  public double getG() { // returns the lorentz factor (G stands for Gamma)
    return 1.0 / Math.sqrt(1 - (xVelocity*xVelocity+yVelocity*yVelocity)/(Space.C*Space.C));
  }
  
  
  public double getG(double xVelocity2, double yVelocity2) { // gamma, taken with respect to another velocity
    final double refVX = xVelocity-xVelocity2;
    final double refVY = yVelocity-yVelocity2;
    return 1.0 / Math.sqrt(1 - (refVX*refVX+refVY*refVY)/(Space.C*Space.C));
  }
  
  
  public final BufferedImage getSprite() { // returns the sprite
    final double theta = Math.atan2(xVelocity-universe.getReference().getVX(), yVelocity-universe.getReference().getVY());
    
    contractor = new AffineTransform();
    contractor.rotate(-theta, sprite.getWidth()/2, sprite.getHeight()/2);
    contractor.scale(1, 1/getG(universe.getReference().getVX(), universe.getReference().getVY()));
    contractor.rotate(theta, sprite.getWidth()/2, sprite.getHeight()/2);
    transOp = new AffineTransformOp(contractor, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    
    try {
      return transOp.filter(sprite, null);
    }
    catch (Exception e) {
      try {
        return ImageIO.read(new File("textures/null.png"));
      }
      catch (IOException f) {
        System.out.println(e+": "+getFilePath());
        return null;
      }
    }
  }
  
  
  public final int getScreenX() { // just the regular x, but shifted back
    return (int)(xPosition - universe.getReference().getX() + (HolographicInterface.WIDTH>>1) - (sprite.getWidth()>>1));
  }
  
  
  public final int getScreenY() { // ditto for y
    return (int)(yPosition - universe.getReference().getY() + (HolographicInterface.HEIGHT>>1) - (sprite.getHeight()>>1));
  }
}