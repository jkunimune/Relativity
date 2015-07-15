public final class RelativityDriver {
  public static void main(String[] args) {
    Space universe = new Space();
    for (int i = 0; i < 100; i ++)
      universe.add(new InertBody(Math.random()*Space.WIDTH, Math.random()*Space.LENGTH, Math.exp(Math.random()*2)/128, Math.random()*2*Math.PI, universe));
    HolographicInterface screen = new HolographicInterface(universe);
    long time;
    
    while (true) {
      time = System.currentTimeMillis();
      screen.display();
      universe.update(System.currentTimeMillis()-time);
    }
  }
}