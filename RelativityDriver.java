public final class RelativityDriver {
  public static void main(String[] args) {
    Space universe = new Space();
    HolographicInterface screen = new HolographicInterface(universe);
    long time;
    
    while (true) {
      time = System.currentTimeMillis();
      screen.display();
      universe.update(System.currentTimeMillis()-time);
    }
  }
}