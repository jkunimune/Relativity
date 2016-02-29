import java.awt.*;
import java.applet.*;




public class RocketShip extends Body { // a body of mass that propels itself based on user input
  public AudioClip[] hitSound; // the sounds of the gaem
  public AudioClip deathSound;
  public AudioClip laserSound;
  public AudioClip warpSound;
  
  private Throttle xThrottle = Throttle.STALLED;
  private Throttle yThrottle = Throttle.STALLED;
  public boolean firing;
  private double cooldown;
  private int hitPoints;
  private EscapePod target;
  
  
  
  
  public RocketShip(double startX, double startY, Space s) {
    super(startX, startY, 0, 0, 0, 75, s);
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
      System.err.println("Error: the direction passed to RocketShip.adjustThrottle should be either \'x\' or \'y\'.");
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
    if (hitPoints < 0)  return 0;
    else                return hitPoints;
  }
  
  
  public void die() {
    shoot(new Explosion(getX(), getY(), Math.pow(getM()/10.0, 1/3.0), getUniverse()));
    deathSound.play();
  }
  
  
  public void warp() { // plays the sound corresponding to the ship attaining the speed of light
    warpSound.play();
  }
  
  
  public void setTarget(EscapePod pod) { // forces the ship to dock with the escape pod
    target = pod;
  }
  
  
  public void yaw() { // rotates the ship to face a certain direction based on throttle
    switch (xThrottle) {
      case FORWARD:
        switch (yThrottle) {
          case FORWARD:
            aimToward(Math.PI/4);
            break;
          case STALLED:
            aimToward(0);
            break;
          case BACKWARD:
            aimToward(7*Math.PI/4);
            break;
        }
        break;
      case STALLED:
        switch (yThrottle) {
          case FORWARD:
            aimToward(Math.PI/2);
            break;
          case STALLED: // if no throttle, coast
        	break;
          case BACKWARD:
            aimToward(3*Math.PI/2);
            break;
        }
        break;
      case BACKWARD:
        switch (yThrottle) {
          case FORWARD:
            aimToward(3*Math.PI/4);
            break;
          case STALLED:
            aimToward(Math.PI);
            break;
          case BACKWARD:
            aimToward(5*Math.PI/4);
            break;
        }
        break;
    }
  }
  
  
  @Override
  public final boolean collideWith(Body b) {
    inelasticallyMergeWith(b); // ships actually get bogged down by collision
    hitPoints = (int)(hitPoints + Math.random()*2.5 - 3);
    hitSound[(int)(Math.random()*hitSound.length)].play();
    return hitPoints <= 0;
  }
  
  
  @Override
  public boolean update(double delT) {
    super.update(delT);
    if (getUniverse().gameState == State.RUNNING) { // shoots lasers if the game is running
      if (cooldown > 0)
        cooldown -= delT;
      else if (firing) {
        shoot(new Laser(this.getX(), this.getY(), getAngle(), this.getUniverse()));
        laserSound.play();
        cooldown += 400;
      }
      yaw();
    }
    return false;
  }
  
  
  public final double getAngle() { // gets the angle of the mouse in radians
    return Math.atan2(MouseInfo.getPointerInfo().getLocation().getY()-HolographicInterface.HEIGHT/2,
                      MouseInfo.getPointerInfo().getLocation().getX()-HolographicInterface.WIDTH/2);
  }
  
  
  @Override
  public final double getAX() {
    switch (getUniverse().gameState) {
      case RUNNING:
        switch (xThrottle) {
          case FORWARD:
            return .0004; // the ship engine power
          case BACKWARD:
            return -.0004;
          default:
            return 0;
        }
        
      case VICTORIOUS:
        final double t = getUniverse().timeRemaining();
        return 8*getVX()/t - 18*(getX()-target.getX())/(t*t);
        
      case DEAD: // dead ships don't fly
        return 0;
        
      default:
        return 0;
    }
  }
  
  
  @Override
  public final double getAY() {
    switch (getUniverse().gameState) {
      case RUNNING:
        switch (yThrottle) {
          case FORWARD:
            return -.0004; // the ship engine power
          case BACKWARD:
            return .0004;
          default:
            return 0;
        }
        
      case VICTORIOUS:
        final double t = getUniverse().timeRemaining();
        return 8*getVY()/t - 18*(getY()-target.getY())/(t*t);
        
      case DEAD: // dead ships don't fly
        return 0;
        
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
      warpSound = Applet.newAudioClip(new java.net.URL("File:sounds/warp.wav"));
    }
    catch (java.net.MalformedURLException error) {
      System.err.println(error);
    }
  }
}