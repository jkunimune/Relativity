import java.awt.event.*;




public class ShipControls implements MouseListener, KeyListener {
  private RocketShip ship;
  private Space space;
  
  
  
  
  public ShipControls (Space newSpace, RocketShip newShip) {
    space = newSpace;
    ship = newShip;
  }
  
  
  
  
  public final void mousePressed(MouseEvent e) {
    ship.firing = true;
  }
  
  
  public final void mouseReleased(MouseEvent e) {
    ship.firing = false;
  }
  
  public final void mouseClicked(MouseEvent e) {}
  
  public final void mouseExited(MouseEvent e) {}
  
  public final void mouseEntered(MouseEvent e) {}
  
  
  public final void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S)
      ship.adjustThrottle('y', Throttle.STALLED);
    else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D)
      ship.adjustThrottle('x', Throttle.STALLED);
  }
  
  
  public final void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_W)
      ship.adjustThrottle('y', Throttle.FORWARD);
    else if (e.getKeyCode() == KeyEvent.VK_S)
      ship.adjustThrottle('y', Throttle.BACKWARD);
    else if (e.getKeyCode() == KeyEvent.VK_D)
      ship.adjustThrottle('x', Throttle.FORWARD);
    else if (e.getKeyCode() == KeyEvent.VK_A)
      ship.adjustThrottle('x', Throttle.BACKWARD);
  }
  
  
  public final void keyTyped(KeyEvent e) {}
  
  
  public final void setSpace(Space spaaaaace) {
    space = spaaaaace;
  }
}