import java.awt.*;
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
  private double aVelocity; // angular velocity
  private double delTheta; // angular displacement since last update
  private Space universe;
  private BufferedImage sprite;
  private AffineTransform transformer;
  private AffineTransformOp transOp;
  public boolean toBeDeleted;
  
  
  
  
  public Body(double newX, double newY, double newVX, double newVY, double newW, double newM, Space newU) {
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
    aVelocity = newW;
    mass = newM;
    universe = newU;
  }
  
  
  public Body(double newX, double newY, double newVX, double newVY, double newW, double newM, Space newU, double scale) {
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
    aVelocity = newW;
    mass = newM;
    universe = newU;
    
    AffineTransform a = new AffineTransform();
    a.scale(Math.cbrt(scale), Math.cbrt(scale));
    AffineTransformOp o = new AffineTransformOp(a, AffineTransformOp.TYPE_BILINEAR);
    sprite = o.filter(sprite, null);
  }
  
  
  public Body(double newX, double newY, double newVX, double newVY, double newW, double newM, Space newU, double scale, double delTheta) {
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
    aVelocity = newW;
    mass = newM;
    universe = newU;
    
    AffineTransform a = new AffineTransform();
    a.scale(Math.cbrt(scale), Math.cbrt(scale));
    a.rotate(delTheta, sprite.getWidth()/2, sprite.getHeight()/2);
    AffineTransformOp o = new AffineTransformOp(a, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    sprite = o.filter(sprite, null);
  }
  
  
  
  
  public boolean update(double delT) { // updates the object by delT milliseconds and keeps them on screen
    xVelocity += getAX()*delT;
    yVelocity += getAY()*delT;
    xPosition += xVelocity*delT;
    yPosition += yVelocity*delT;
    delTheta += aVelocity*delT;
    return false; // returns whether it should be deleted
  }
  
  
  public abstract String getFilePath(); // for finding sprite files
  
  
  public abstract double getAX(); // returns the x-component of the acceleration vector
  
  
  public abstract double getAY(); // returns the y-component of the acceleration vector
  
  
  public abstract boolean collide(); // performs a collision and returns whether the object has been destroyed
  
  
  public abstract boolean canCollideWith(Body b); // returns whether an object may interact with a given body
  
  
  public void shoot(Body projectile) { // causes the body to lose some momentum and fire a projectile
    universe.add(projectile);
    xVelocity -= projectile.getVX()*projectile.getM()/this.mass;
    yVelocity -= projectile.getVY()*projectile.getM()/this.mass;
    projectile.setVX(projectile.getVX()+xVelocity);
    projectile.setVY(projectile.getVY()+yVelocity);
  }
  
  
  public void eject(Body baby) { // causes the body to eject part of its mass in the form of a projectile
    final double scale = (mass-baby.getM())/mass;
    universe.add(baby);
    mass -= baby.getM();
    double delVX = baby.getVX();
    double delVY = baby.getVY();
    baby.setVX(delVX+this.xVelocity);
    baby.setVY(baby.getVY()+this.yVelocity);
    xVelocity -= delVX*baby.getM()/this.mass;
    yVelocity -= delVY*baby.getM()/this.mass;
    
    AffineTransform a = new AffineTransform();
    a.scale(Math.cbrt(scale), Math.cbrt(scale));
    AffineTransformOp o = new AffineTransformOp(a, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    sprite = o.filter(sprite, null);
  }
  
  
  public void setVX(double val) {
    xVelocity = val;
  }
  
  
  public void setVY(double val) {
    yVelocity = val;
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
  
  
  public double getW() { // returns the angular velocity (W stands for Omega)
    return aVelocity;
  }
  
  
  public Space getUniverse() { // returns the universe to which this object belongs
    return universe;
  }
  
  
  public double getG() { // returns the lorentz factor (G stands for Gamma)
    final double refVX = xVelocity-universe.getReference().getVX();
    final double refVY = yVelocity-universe.getReference().getVY();
    return 1.0 / Math.sqrt(1 - (refVX*refVX+refVY*refVY)/(Space.C*Space.C));
  }
  
  
  public double getD() { // returns the proportionality constant determined using the doppler effect
    final double rX = xPosition - universe.getReference().getX(); // the displacement vector x component
    final double rY = yPosition - universe.getReference().getY(); // y version
    if (rX == 0 && rY == 0)
      return 1;
    
    final double vR = (rX*universe.getReference().getVX() + rY*universe.getReference().getVY()) / Math.sqrt(rX*rX + rY*rY); // the relative speed of the rocket
    final double vO = (rX*xVelocity + rY*yVelocity) / Math.sqrt(rX*rX + rY*rY); // the relative speed of the object
    
    return (Space.C+vR)/(Space.C+vO);
  }
  
  
  public final BufferedImage getSprite() { // returns the sprite
    final double theta = Math.atan2(xVelocity-universe.getReference().getVX(), yVelocity-universe.getReference().getVY());
    
    transformer = new AffineTransform();
    transformer.rotate(-theta, sprite.getWidth()/2, sprite.getHeight()/2);
    transformer.scale(1, 1/getG());
    transformer.rotate(theta-delTheta, sprite.getWidth()/2, sprite.getHeight()/2);
    transOp = new AffineTransformOp(transformer, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    
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
  
  
  public final void shrinkSprite(double scale) {
    transformer = new AffineTransform();
    transformer.scale(scale, scale);
    transOp = new AffineTransformOp(transformer, AffineTransformOp.TYPE_BILINEAR);
    
    try {
      sprite = transOp.filter(sprite, null);
    }
    catch (Exception e) {
      try {
        sprite = ImageIO.read(new File("textures/null.png"));
      }
      catch (IOException f) {
        System.out.println(e+": "+getFilePath());
      }
    }
  }
  
  
  public final int getScreenX() { // just the regular x, but shifted back and space-contracted
    return (int)((xPosition - universe.getReference().getX() - (sprite.getWidth()>>1))/getG() + (HolographicInterface.WIDTH>>1));
  }
  
  
  public final int getScreenY() { // ditto for y
    return (int)((yPosition - universe.getReference().getY() - (sprite.getHeight()>>1))/getG() + (HolographicInterface.HEIGHT>>1));
  }
  
  
  public final double getWidth() {
    return sprite.getWidth();
  }
  
  
  public final double getHeight() {
    return sprite.getHeight();
  }
  
 
  public final Point getScreenCoords() { // returns the coordinates of the screen at which it should be drawn
    double xRel = xPosition - universe.getReference().getX(); // the x coordinate of the object relative to the reference point
    double yRel = yPosition - universe.getReference().getY(); // ditto for y
    final double vXRel = xVelocity - universe.getReference().getVX(); // and for velocity
    final double vYRel = yVelocity - universe.getReference().getVY();
    
    if (xRel*xRel+yRel*yRel > 0) {
      xRel -= (xRel-yRel*vXRel/vYRel) / getG() / (1+Math.pow(vXRel/vYRel,2)); // length contracts
      yRel -= (yRel-xRel*vYRel/vXRel) / getG() / (1+Math.pow(vYRel/vXRel,2)); // length contracts
    }
    
    return new Point((int)(xRel+ (HolographicInterface.WIDTH - sprite.getWidth() >>1)),
                     (int)(yRel+ (HolographicInterface.HEIGHT - sprite.getHeight() >>1)));
  }
}