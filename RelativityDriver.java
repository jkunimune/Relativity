public final class RelativityDriver {
  public static void main(String[] args) {
    Space universe = new Space();
    HolographicInterface screen = new HolographicInterface(universe);
    long time;
    
    do {
      time = System.currentTimeMillis();
      screen.display();
    } while (universe.update(System.currentTimeMillis()-time));
    
    System.out.println("Your score is "+universe.getScore());
  }
}