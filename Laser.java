public class Laser extends Body { // a massless collection of photons
  public final boolean canCollide = true; // whether the body should collide with other bodies
  
  
  
  
  public Laser(double newX, double newY, double newTheta, Space s) {
    super(newX, newY, Space.C*Math.cos(newTheta), Space.C*Math.sin(newTheta), .01, 1, s);
  }
  
  
  
  
  @Override
  public final double getAX() {
    return getUniverse().getReference().getAX(); // kind of cheaty, but keeps light moving at the correct speed
  }
  
  
  @Override
  public final double getAY() {
    return getUniverse().getReference().getAY();
  }
  
  
  @Override
  public final boolean collide() {
    return true;
  }
  
  
  @Override
  public boolean canCollideWith(Body b) {
    return b.getClass().getName().equals("InertBody") || b.getClass().getName().equals("Laser");
  }
  
  
  @Override
  public final double getG() {
    return 1.0;
  }
  
  
  @Override
  public final String getFilePath() {
    return "textures/laser.png";
  }
}