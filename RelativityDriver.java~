public final class RelativityDriver {
  public static void main(String[] args) {
    Space universe = new Space();
    for (int i = 0; i < 10; i ++)
      universe.add(new InertBody(Math.random()*1180, Math.random()*700));
    HolographicInterface screen = new HolographicInterface(universe);
    long time;
    
    while (true) {
      time = System.currentTimeMillis();
      screen.display();
      universe.update(System.currentTimeMillis()-time);
    }
  }
}