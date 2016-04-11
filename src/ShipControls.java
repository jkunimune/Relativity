import java.awt.event.*;

import javax.swing.JFrame;




public class ShipControls implements MouseListener, KeyListener {
  private Space universe;
  private RocketShip ship;
  private JFrame frame;
  
  
  
  
  public ShipControls (Space newSpace, JFrame newFrame) {
    universe = newSpace;
    ship = newSpace.getReference();
    frame = newFrame;
  }
  
  
  
  
  public final void mousePressed(MouseEvent e) {
    if (universe.gameState == State.DISPLAY) {
      if (Math.hypot(e.getX() - HolographicInterface.WIDTH/2, e.getY() - HolographicInterface.HEIGHT/2 + 200) < 100)
        universe.gameState = State.VICTORIOUS;										// start
      else if (Math.hypot(e.getX() - HolographicInterface.WIDTH/2, e.getY() - HolographicInterface.HEIGHT/2 - 200) < 100)
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));	// exit
    }
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
}