import java.io.*;
import java.applet.*;



public final class RelativityDriver {
  public static void main(String[] args) throws IOException {
    AudioClip tunes = Applet.newAudioClip(new java.net.URL("File:sounds/soundtrack.wav"));
    tunes.loop(); // the soundtrack
    
    Space universe = new Space(0);
    HolographicInterface screen = new HolographicInterface(universe);
    long time;
    
    while (true) {
      do {
        time = System.currentTimeMillis();
        screen.display();
      } while (universe.update(System.currentTimeMillis()-time));
      
      System.out.println("Your score is "+universe.getScore());
      
      try {
        if (saveScore("Bob", universe.getScore()))
          System.out.println("That's a new record!"); // tells you if you beat the high score
      } catch (IOException e) {
        System.err.println("This computer's going to explode in 5 seconds! Run!\n\n\nJK, you just did something funny to the game files.");
      }
      
      if (universe.won())
        universe = new Space(universe.getDif()+1);
      else
        universe = new Space(universe.getDif());
      screen.setSpace(universe);
    }
  }
  
  
  public static boolean saveScore(String name, int newScore) throws IOException { //returns true if there is a new high score
    String[] scores = getScores();
    
    for (int i = 0; i < scores.length; i ++) { // cycles through scores best to worst
      if (Integer.parseInt(scores[i].substring(scores[i].indexOf("0"))) < newScore) { // and saves once your score is better (if you didn't beat any of the hiscores, it ends)
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/hiScores.txt"));
        
        for (int k = 0; k < i; k ++) {
          writer.write(scores[k]);
          writer.newLine();
        }
        writer.write(name+"0"+newScore);
        writer.newLine();
        for (int k = i; k < scores.length; k ++) {
          writer.write(scores[k]);
          writer.newLine();
        }
        writer.close();
        return i == 0;
      }
    }
    return false;
  }
  
  
  public static String[] getScores() throws IOException {
    try {
      String[] scoreList = new String[10];
      BufferedReader reader = new BufferedReader(new FileReader("data/hiScores.txt"));
      for (int i = 0; i < 10; i ++) {
        scoreList[i] = reader.readLine();
      }
      reader.close();
      return scoreList;
    }
    catch (IOException e) {
      return new String[0];
    }
  }
}