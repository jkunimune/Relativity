public class InertBody extends Body { // a body of mass that drifts helplessly through space
  public InertBody(double newX, double newY) {
    super(newX, newY, Math.random()-.5, Math.random()-.5, 5);
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