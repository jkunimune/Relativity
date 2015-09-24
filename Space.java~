import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;




public class Space extends ArrayList<Body> { // a class that contains all the physical information of the universe
  public static final double m = .000005; // pixels
  public static final double s = 1000; // miliseconds
  public static final double C = 300000000*m/s; // the speed of light to a stationary observer
  public static final double G = 1.0; // Newton's universal gravitation constant
  public static final int RENDER_DISTANCE = 1000; // the size of the square in which space debris is rendered
  
  private RocketShip me; // the ship
  private int remainingLives; // the number of lives remaining. When this reaches 0, the game is over.
  private double destination; // the distance the ship must travel to complete the level
  private long timeCreated; // the time the universe was created
  boolean won;
  
  
  
  
  public Space() {
    me = new RocketShip(0, RENDER_DISTANCE>>1, this);
    this.add(me);
    remainingLives = 3;
    destination = 5000;
    timeCreated = System.currentTimeMillis();
    won = false;
    
    for (int j = 0; j <= 1; j ++)
      for (int k = -1; k <= 1; k ++)
        if (j!=0 || k!=0)
          render(j,k);
  }
  
  
  
  
  public boolean update(double delT) { // returns true when the game is in progress
    procedurallyGenerate(delT);
    
    for (int i = this.size()-1; i > 1; i --)
      if (get(i).update(delT/get(i).getG()*get(i).getD()))
        remove(i);
    
    if (won)  return false; // victory if the ship has found the pod
    
    for (int i = 0; i < this.size()-1; i ++) {
      for (int h = i+1; h < this.size(); h ++) {
        if (Math.abs(get(i).getX() - get(h).getX()) < (get(i).getWidth()+get(h).getWidth())/2 &&
            Math.abs(get(i).getY() - get(h).getY()) < (get(i).getHeight()+get(h).getHeight())/2) {
          if (get(i).canCollideWith(get(h))) { // only collide if the combination is right
            if (get(i).collide()) {
              remove(i);
              h --;
            }
            if (get(h).collide()) {
              remove(h);
            }
          }
        }
      }
    }
    
    if (!this.contains(me)) {
      me.deathSound.play();
      System.out.println("You lose. I'm a winner see my prize! You're a loser who sits and cries. HAHAHA!");
      return false; // death if the ship is gone
    }
    else  return true;
  }
  
  
  private void procedurallyGenerate(double delT) { // generates terrain as the player goes. Also moves the ship
    final Point oldChk = new Point((int)Math.floor(me.getX()/RENDER_DISTANCE), (int)Math.floor(me.getY()/RENDER_DISTANCE));
    me.update(delT);
    final Point newChk = new Point((int)Math.floor(me.getX()/RENDER_DISTANCE), (int)Math.floor(me.getY()/RENDER_DISTANCE));
    
    if (newChk.x != oldChk.x) {
      if (me.getX() < destination) {
        if (newChk.y != oldChk.y) { // if the player is diagonally in a new chunk
          System.err.println("You got super lucky and broke the game because of it. Good job!");
          while (true) {}
        }
        else { // if the player is horizontally in a new chunk
          final int dir = newChk.x-oldChk.x; // render approaching chunks and derender irrelevant chunks
          derender(newChk.x-1, newChk.x+1, oldChk.y-1, oldChk.y+1);
          render(newChk.x+dir, newChk.y-1);
          render(newChk.x+dir, newChk.y);
          render(newChk.x+dir, newChk.y+1);
        }
      }
      else { // if the player has reached his/her destination, do not render normally
        derender(newChk.x-1, newChk.x+1, oldChk.y-1, oldChk.y+1);
        add(new EscapePod(me.getX()+RENDER_DISTANCE, me.getY(), this));
      }
    }
    else {
      if (newChk.y != oldChk.y) { // if the player is vertically in a new chunk
        final int dir = newChk.y-oldChk.y; // render approaching chunks and derender irrelevant chunks
        derender(newChk.x-1, newChk.x+1, newChk.y-1, newChk.y+1);
        render(newChk.x-1, newChk.y+dir);
        render(newChk.x, newChk.y+dir);
        render(newChk.x+1, newChk.y+dir);
      }
    }
  }
  
  
  private void render(int x, int y) { // generates an area and fills it with objects
    for (int i = 0; i < RENDER_DISTANCE*RENDER_DISTANCE>>16; i ++)
      if (Math.random() < .5)
        this.add(new InertBody((x+Math.random())*RENDER_DISTANCE, (y+Math.random())*RENDER_DISTANCE, Math.log(1/Math.random()-1)/50,
                               Math.random()*2*Math.PI, Math.log(1/Math.random()-1)/10000, Math.random(),
                               this));
  }
  
  
  private void derender(int x1, int x2, int y1, int y2) { // deletes all objects outside of a range 
    for (int i = this.size()-1; i >= 0; i --)
      if ((int)Math.floor(get(i).getX()/RENDER_DISTANCE) < x1 || (int)Math.floor(get(i).getX()/RENDER_DISTANCE) > x2 ||
          (int)Math.floor(get(i).getY()/RENDER_DISTANCE) < y1 || (int)Math.floor(get(i).getY()/RENDER_DISTANCE) > y2)
        remove(i);
  }
  
  
  public RocketShip getReference() {
    return me;
  }
  
  
  public int extraLives() {
    return remainingLives - 1;
  }
  
  
  public double getProgress() {
    return destination/me.getX();
  }
  
  
  public String getTime() {
    String output = String.valueOf((System.currentTimeMillis()-timeCreated)/1000.0); // returns the age of this level in seconds, in String form
    while (output.indexOf(".") < 3)
      output = "0" + output;
    while (output.length() < 7)
      output += "0";
    return output;
  }
  
  
  public void triggerVictory() {
    won = true;
    System.out.println("You win!");
  }
  
  
  public int getScore() { // returns the player's current score
    if (won)  return (int)(destination*destination/(System.currentTimeMillis()-timeCreated));
    else      return (int)(me.getX()*destination/(System.currentTimeMillis()-timeCreated)/2);
  }
}