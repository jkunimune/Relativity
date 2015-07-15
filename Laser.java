public class Laser extends Body { // a massless collection of photons
  public Laser(double newX, double newY, double newTheta, Space s) {
    super(newX, newY, Space.C*Math.cos(newTheta), Space.C*Math.sin(newTheta), 1, s);
  }
  
  
  
  
  public final double getAX() {
    return 0;
  }
  
  
  public final double getAY() {
    return 0;
  }
  
  
  public final double getG() {
    return 1.0;
  }
  
  
  public final double getGX() {
    return 1.0;
  }
  
  
  public final double getGY() {
    return 1.0;
  }
  
  
  public final String getFilePath() {
    return "textures/laser.png";
  }
}