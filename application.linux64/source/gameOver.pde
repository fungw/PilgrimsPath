public class gameOver {
  private PImage gameOverDisplay;
  private File f;
  private PrintWriter output;
  private String filename = "scores.csv";
  private boolean fileExists;
  private String[] lines;
  private String header = "SCORE,TIME,MANA,HOUR,MINUTE,MILLIS,DAY,MONTH,YEAR";

  public gameOver() {
    this.gameOverDisplay = loadImage("./images/gameover.png");
  }

  public void draw() {
    image(this.gameOverDisplay, 0, 0);
  } 

  public void writeToFile() {
    write = true;
    this.lines = loadStrings(filename);
    this.output = createWriter(filename);
    output.println(header);
    output.println(score + ","+ time/1000 + "," + magic_number
      + "," + hour() + "," + minute() + "," + millis() + ","
      + day() + "," + month() + "," + year());
    if (lines != null) {
      setHighScore(lines);
      if (checkScore() && !bonus) {
        println("NEW HIGH SCORE");
        cont_game = true; 
        bonus = true;
      } else {
        cont_game = false;  
      }
      for (int i=1; i<lines.length; i++) {
        output.println(lines[i]);
      }
    } 
    output.flush(); // Writes the remaining data to the file
    output.close(); // Finishes the file
  }

  private void setHighScore(String[] linesScore) {
    for (int i=1; i<lines.length; i++) {
      String str = linesScore[i];
      String[] strList = split(str, ",");
      if (Integer.parseInt(strList[0]) > high_score) {
        high_score = Integer.parseInt(strList[0]);
      }
    }
  }
  
  private boolean checkScore() {
    return score > high_score;  
  }
}

