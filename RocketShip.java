public class RocketShip extends Body { // a body of mass that propels itself based on user input
  Throttle xThrottle = Throttle.STALLED;
  Throttle yThrottle = Throttle.STALLED;
  
  
  
  public RocketShip(double startX, double startY) {
    super(startX, startY, 0, 0, 15);
  }
  
  
  
  
  public void adjustThrottle(char direction, Throttle magnitude) { // causes the rocket to accelerate or decelerate
    if (direction == 'x')
      xThrottle = magnitude;
    else if (direction == 'y')
      yThrottle = magnitude;
    else
      System.out.println("Error: the direction passed to RocketShip.adjustThrottle should be either \'x\' or \'y\'.");
  }
  
  
  @Override
  public final double getAX() {
    switch (xThrottle) {
      case FORWARD:
        return .03;
      case BACKWARD:
        return -.03;
      default:
        return 0;
    }
  }
  
  
  @Override
  public final double getAY() {
    switch (yThrottle) {
      case FORWARD:
        return -.03;
      case BACKWARD:
        return .03;
      default:
        return 0;
    }
  }
  
  
  @Override
  public final String getFilePath() {
    return "textures/ship.png";
  }
}