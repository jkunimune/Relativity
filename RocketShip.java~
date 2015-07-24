import java.awt.*;




public class RocketShip extends Body { // a body of mass that propels itself based on user input
  private Throttle xThrottle = Throttle.STALLED;
  private Throttle yThrottle = Throttle.STALLED;
  public boolean firing;
  private double cooldown;
  
  
  
  
  public RocketShip(double startX, double startY, Space s) {
    super(startX, startY, 0, 0, 60, s);
    firing = false;
    cooldown = 0;
  }
  

  
  
  public void adjustThrottle(char direction, Throttle magnitude) { // causes the rocket to accelerate or decelerate
    if (direction == 'x')
      xThrottle = magnitude;
    else if (direction == 'y')
      yThrottle = magnitude;
    else
      System.out.println("Error: the direction passed to RocketShip.adjustThrottle should be either \'x\' or \'y\'.");
  }
  
  
  public boolean canShoot() {
    return cooldown <= 0;
  }
  
  
  @Override
  public void update(double delT) {
    super.update(delT);
    if (cooldown > 0)
      cooldown -= delT;
    else if (firing) {
      shoot(new Laser(this.getX(), this.getY(), getAngle(), this.getUniverse()));
      cooldown += 300;
    } 
  }
  
  
  public final double getAngle() { // gets the angle of the mouse in radians
    return Math.atan2(MouseInfo.getPointerInfo().getLocation().getY()-HolographicInterface.HEIGHT/2,
                      MouseInfo.getPointerInfo().getLocation().getX()-HolographicInterface.WIDTH/2);
  }
  
  
  @Override
  public final double getAX() {
    switch (xThrottle) {
      case FORWARD:
        return .0003;
      case BACKWARD:
        return -.0003;
      default:
        return 0;
    }
  }
  
  
  @Override
  public final double getAY() {
    switch (yThrottle) {
      case FORWARD:
        return -.0003;
      case BACKWARD:
        return .0003;
      default:
        return 0;
    }
  }
  
  
  @Override
  public final String getFilePath() {
    return "textures/ship.png";
  }
}