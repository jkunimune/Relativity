import java.io.*;



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
    saveScore("Bob", universe.getScore());
  }
  
  
  public static void saveScore(String name, int score) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("data/hiScores.txt"));
      writer.write(name+": "+score);
      writer.newLine();
      writer.close();
    }
    catch (IOException e) {
      System.err.println("HEY, did someone move hiScores?");
    }
  }
  
  
  public static String getScores() {
    try {
      String scoreList = "";
      BufferedReader reader = new BufferedReader(new FileReader("data/hiScores.txt"));
      for (int i = 0; i < 10; i ++) {
        scoreList += reader.readLine();
      }
      reader.close();
      return scoreList;
    }
    catch (IOException e) {
      return "Where did hiScores.txt go?";
    }
  }
}