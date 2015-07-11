public final class RelativityDriver {
  public static void main(String[] args) {
    Space universe = new Space();
    for (int i = 0; i < 100; i ++)
      universe.add(new InertBody(Math.random()*Space.WIDTH, Math.random()*Space.LENGTH));
    HolographicInterface screen = new HolographicInterface(universe);
    long time;
    
    while (true) {
      time = System.currentTimeMillis();
      screen.display();
      universe.update(System.currentTimeMillis()-time);
    }
  }
}