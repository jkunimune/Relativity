import java.awt.*;
import java.applet.*;




public class RocketShip extends Body { // a body of mass that propels itself based on user input
  public final boolean canCollide = true;
  
  public AudioClip[] hitSound; // the sounds of the gaem
  public AudioClip deathSound;
  public AudioClip laserSound;
  
  private Throttle xThrottle = Throttle.STALLED;
  private Throttle yThrottle = Throttle.STALLED;
  public boolean firing;
  private double cooldown;
  private int hitPoints;
  
  
  
  
  public RocketShip(double startX, double startY, Space s) {
    super(startX, startY, 0, 0, 0, 60, s);
    firing = false;
    cooldown = 0;
    hitPoints = 16;
    loadSound();
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
  
  
  public int laserCharge() {
    if (cooldown < 0)
      return 8;
    if (cooldown > 400)
      return 0;
    return 8 - (int)cooldown*8/400;
  }
  
  
  public int getHP() {
    return hitPoints;
  }
  
  
  @Override
  public final boolean collide() {
    hitPoints --;
    hitSound[(int)(Math.random()*hitSound.length)].play();
    return hitPoints <= 0;
  }
  
  
  @Override
  public void update(double delT) {
    super.update(delT);
    if (cooldown > 0)
      cooldown -= delT;
    else if (firing) {
      shoot(new Laser(this.getX(), this.getY(), getAngle(), this.getUniverse()));
      laserSound.play();
      cooldown += 400;
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
  public boolean canCollideWith(Body b) {
    return b.getClass().getName().equals("InertBody");
  }
  
  
  @Override
  public final String getFilePath() {
    return "textures/ship.png";
  }
  
  
  private final void loadSound() { // loads all sound files
    try {
      hitSound = new AudioClip[6];
      for (int i = 0; i < hitSound.length; i ++)
        hitSound[i] = Applet.newAudioClip(new java.net.URL("File:sounds/hit"+(i%3)+".wav"));
      deathSound = Applet.newAudioClip(new java.net.URL("File:sounds/shipDeath.wav"));
      laserSound = Applet.newAudioClip(new java.net.URL("File:sounds/laser.wav"));
    }
    catch (java.net.MalformedURLException error) {
      System.err.println(error);
    }
  }
}