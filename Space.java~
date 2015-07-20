import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;




public class Space extends ArrayList<Body> { // a class that contains all the physical information of the universe
  public static final double m = .000008; // pixels
  public static final double s = 1000; // miliseconds
  public static final double C = 300000000*m/s; // the speed of light to a stationary observer
  public static final double G = 1.0; // Newton's universal gravitation constant
  public static final int RenderDist = 1000; // the size of the square in which space debris is rendered
  
  private RocketShip me;
  
  
  
  
  public Space() {
    me = new RocketShip(RenderDist>>1, RenderDist>>1, this);
    this.add(me);
    
    for (int j = -1; j <= 1; j ++)
      for (int k = -1; k <= 1; k ++)
        render(j,k);
  }
  
  
  
  
  public void update(double delT) {
    procedurallyGenerate(delT/me.getG());
    
    for (int i = 1; i < this.size(); i ++)
      get(i).update(delT/get(i).getG());
  }
  
  
  private void procedurallyGenerate(double delT) { // generates terrain as the player goes. Also moves the ship
    final Point oldChk = new Point((int)Math.floor(me.getX()/RenderDist), (int)Math.floor(me.getY()/RenderDist));
    me.update(delT/me.getG());
    final Point newChk = new Point((int)Math.floor(me.getX()/RenderDist), (int)Math.floor(me.getY()/RenderDist));
    
    if (newChk.x != oldChk.x) {
      if (newChk.y != oldChk.y) { // if the player is diagonally in a new chunk
        System.out.println("You got super luckly and broke the game because of it. Good job!");
        while (true) {}
      }
      else { // if the player is horizontally in a new chunk
        final int dir = newChk.x-oldChk.x; // render approaching chunks and derender irrelevant chunks
        derender(oldChk.x-dir, oldChk.x-dir, oldChk.y-1, oldChk.y+1);
        render(newChk.x+dir, newChk.y-1);
        render(newChk.x+dir, newChk.y);
        render(newChk.x+dir, newChk.y+1);
      }
    }
    else {
      if (newChk.y != oldChk.y) { // if the player is vertically in a new chunk
        final int dir = newChk.y-oldChk.y; // render approaching chunks and derender irrelevant chunks
        derender(oldChk.x-1, oldChk.x+1, oldChk.y-dir, oldChk.y-dir);
        render(newChk.x-1, newChk.y+dir);
        render(newChk.x, newChk.y+dir);
        render(newChk.x+1, newChk.y+dir);
      }
    }
  }
  
  
  private void render(int x, int y) { // generates an area and fills it with objects
    for (int i = 0; i < RenderDist*RenderDist>>16; i ++)
      if (Math.random() < .5)
        this.add(new InertBody((x+Math.random())*RenderDist, (y+Math.random())*RenderDist,
                               Math.exp(Math.random()*2-5), Math.random()*2*Math.PI, this));
  }
  
  
  private void derender(int x1, int x2, int y1, int y2) { // deletes all objects in a range to save memory
    for (int i = this.size()-1; i >= 0; i --)
      if ((int)Math.floor(get(i).getX()/RenderDist) >= x1 && (int)Math.floor(get(i).getX()/RenderDist) <= x2 &&
          (int)Math.floor(get(i).getY()/RenderDist) >= y1 && (int)Math.floor(get(i).getY()/RenderDist) <= y2)
        remove(i);
  }
  
  
  public RocketShip getReference() {
    return me;
  }
}