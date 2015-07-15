public class InertBody extends Body { // a body of mass that drifts helplessly through space
  public InertBody(double newX, double newY, double r, double th, Space s) {
    super(newX, newY, r*Math.cos(th), r*Math.sin(th), 5, s);
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