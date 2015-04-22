import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_7766756e67 extends PApplet {

// Images
private player_character player;
private background_continuous bg1;
private obstacles obstacles, obstacles1, obstacles2, obstacles3, obstacles4;
private gameOver gg;
private scoreDisplay scoreDisplay;

public void setup() {
  // Configure window size
  size(window_width, window_height);

  // Setup for the background
  bg1 = new background_continuous(this.window_width);

  // Setup for the player class
  player = new player_character();

  // Setup for the path
  obstacles = new obstacles();
  obstacles1 = new obstacles();
  obstacles2 = new obstacles();
  obstacles3 = new obstacles();
  obstacles4 = new obstacles();

  // Framerate
  frameRate(60);

  gg = new gameOver();

  scoreDisplay = new scoreDisplay();

  startTime = millis();
}

public void draw() {
  if (!gameOver) {
    bg1.draw();
    obstacles.draw();
    obstacles1.draw();
    obstacles2.draw();
    obstacles3.draw();
    obstacles4.draw();
    player.draw();
    scoreDisplay.draw();
  } else if (gameOver) {
    gg.draw();
    if (!write) {
      gg.writeToFile();  
    }
  } 
  if (obstacles.getScreen()) {
    obstacles = new obstacles();
  } 
  if (obstacles1.getScreen()) {
    obstacles1 = new obstacles();
  } 
  if (obstacles2.getScreen()) {
    obstacles2 = new obstacles();
  } 
  if (obstacles3.getScreen()) {
    obstacles3 = new obstacles();
  } 
  if (obstacles4.getScreen()) {
    obstacles4 = new obstacles();
  }
}

public void keyPressed() {
  if (key == CODED) {
    if (keyCode == UP) {
      player.y_up();
    } else if (keyCode == DOWN) {
      player.y_down();
    }
    
  }
  if (keyCode == ' ') {
    if (gameOver) {
      gameOver = false;
      obstacles = new obstacles();
      obstacles1 = new obstacles(); 
      obstacles2 = new obstacles();
      obstacles3 = new obstacles(); 
      obstacles4 = new obstacles();
      speed = 2;
      firstDozenObstacles = true;
      firstDozenCount = 0;
      if (!cont_game) {
        startTime = millis();
        score = 0;
        magic_number = 100;
        bonus = false;
      }
    } else {
      player.setTransform();
    }
  }
}

public void keyReleased() {
  if (key == CODED) {
    if (keyCode == UP) {
      player.startHover();
    } else if (keyCode == DOWN) {
      player.startHover();
    }
  } 
  if (keyCode == ' ') {
    player.setTransformOff();  
  }
}

public boolean sketchFullScreen() {
  return true;
}

public class background_continuous {
  private PImage bg1, bg1_before, bg1_after;
  private int bg1_x, bg1_before_x, bg1_after_x;
  private int window_width;

  public background_continuous(int w_width) {
    // Load the images
    this.bg1 = loadImage("./images/bg1_full.png");
    this.bg1_before = loadImage("./images/bg1_3_full.png");
    this.bg1_after = loadImage("./images/bg1_2_full.png");

    // Setting the x coordinates of each image
    this.window_width = w_width;
    this.bg1_x = 0;
    this.bg1_before_x = 0 - bg1.width;
    this.bg1_after_x = w_width;
  }

  public void draw() {
    image(bg1, this.bg1_x, 0);
    image(bg1_before, this.bg1_before_x, 0);
    image(bg1_after, this.bg1_after_x, 0);
    this.bg1_x -= speed;
    this.bg1_before_x -= speed;
    this.bg1_after_x -= speed;
    checkRotate();
  }

  private void checkRotate() {
    if (this.bg1_x+this.bg1.width == 0) {
      bg1_before_x = this.window_width;
    } else if (this.bg1_after_x+this.bg1.width == 0) {
      bg1_x = this.window_width;
    } else if (this.bg1_before_x+this.bg1.width == 0) {
      bg1_after_x = this.window_width;
    }
  }
}
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

public int window_width = 1080;
public int window_height = 720;

public float speed = 2;
public float player_p1_x, player_p1_y;
public float player_p2_x, player_p2_y;
public float player_p3_x, player_p3_y;
public float player_p4_x, player_p4_y;
public float obstacle_p1_x, obstacle_p1_y;
public float obstacle_p2_x, obstacle_p2_y;
public float obstacle_p3_x, obstacle_p3_y;
public float obstacle_p4_x, obstacle_p4_y;
public float obstacle_p5_x, obstacle_p5_y;
public float obstacle_p6_x, obstacle_p6_y;
public float obstacle_p7_x, obstacle_p7_y;
public float obstacle_p8_x, obstacle_p8_y;

public boolean gameOver = false;
public int score = 0;
public float player_x_score = 0;
public boolean firstDozenObstacles = true;
public int firstDozenCount = 0;

public float magic_number = 100;
public int time = 0;
public int startTime = 0;
public boolean write = false;
public boolean stall = false;
public int high_score = 0;
public boolean cont_game = false;
public boolean bonus = false;
public class obstacles {
  private float x1_1, y1_1, x2_1, y2_1;
  private float x1_2, y1_2, x2_2, y2_2;
  private float x1_3, y1_3, x2_3, y2_3;
  private float x1_4, y1_4, x2_4, y2_4;
  private float r, g, b;
  private boolean offScreen;
  private boolean x1_1_pass, x1_2_pass, x1_3_pass, x1_4_pass;

  public obstacles() {
    if (firstDozenObstacles) {
      this.x1_1 = random(500, 1000);
      this.y1_1 = random(-100, 0);
      this.x2_1 = random(100, 200);
      this.y2_1 = random(250, 300);

      this.x1_2 = random(500, 1000);
      this.y1_2 = random(500, 600);
      this.x2_2 = random(100, 200);
      this.y2_2 = random(250, 350);
  
      this.x1_3 = random(1000, 1500);
      this.y1_3 = random(-100, 0);
      this.x2_3 = random(100, 200);
      this.y2_3 = random(250, 350);
      
      this.x1_4 = random(1000, 1500);
      this.y1_4 = random(500, 600);
      this.x2_4 = random(100, 200);
      this.y2_4 = random(250, 350);
    } else {
      this.x1_1 = random(0, 1000);
      this.y1_1 = random(-100, 0);
      this.x2_1 = random(100, 200);
      this.y2_1 = random(250, 300);

      this.x1_2 = random(0, 1000);
      this.y1_2 = random(500, 600);
      this.x2_2 = random(100, 200);
      this.y2_2 = random(250, 350);
  
      this.x1_3 = random(500, 1000);
      this.y1_3 = random(-100, 0);
      this.x2_3 = random(100, 200);
      this.y2_3 = random(250, 350);
      
      this.x1_4 = random(500, 1000);
      this.y1_4 = random(500, 600);
      this.x2_4 = random(100, 200);
      this.y2_4 = random(250, 350);
    }
    this.r = 0;
    this.g = 0;
    this.b = 255;
    this.x1_1_pass = false;
    this.x1_2_pass = false;
    this.x1_3_pass = false;
    this.x1_4_pass = false;
    this.offScreen = false;
    
    if (firstDozenCount >= 4) {
      firstDozenCount++;
      firstDozenObstacles = false;  
    }
    firstDozenCount++;
  } 

  public void draw() {
    this.r = random(0, 255);
    this.g = random(0, 255);
    this.b = random(0, 255);
    fill(this.r, this.g, this.b);

    rect(x1_1, y1_1, x2_1, y2_1);
    rect(x1_2, y1_2, x2_2, y2_2);
    rect(x1_3, y1_3, x2_3, y2_3);
    rect(x1_4, y1_4, x2_4, y2_4);
    x1_1 -= speed;
    x1_2 -= speed;
    x1_3 -= speed;
    x1_4 -= speed;
    obstacle_p1_x = x1_1;
    //println("OB_p1_x " + obstacle_p1_x);
    obstacle_p1_y = y1_1 + y2_1;
    //println("OB_p1_y " + obstacle_p1_y);
    obstacle_p2_x = x1_1 + x2_1;
    //println("OB_p2_x " + obstacle_p2_x);
    obstacle_p2_y = y1_1 + y2_1;
    //println("OB_p2_y " + obstacle_p2_y);

    obstacle_p3_x = x1_2;
    //println("OB_p3_x " + obstacle_p3_x);
    obstacle_p3_y = y1_2;
    //println("OB_p3_y " + obstacle_p3_y);
    obstacle_p4_x = x1_2 + x2_2;
    //println("OB_p4_x " + obstacle_p4_x);
    obstacle_p4_y = y1_2;
    //println("OB_p4_y " + obstacle_p4_y);
    
    obstacle_p5_x = x1_3;
    //println("OB_p1_x " + obstacle_p1_x);
    obstacle_p5_y = y1_3 + y2_3;
    //println("OB_p1_y " + obstacle_p1_y);
    obstacle_p6_x = x1_3 + x2_3;
    //println("OB_p2_x " + obstacle_p2_x);
    obstacle_p6_y = y1_3 + y2_3;
    //println("OB_p2_y " + obstacle_p2_y);
    
    obstacle_p7_x = x1_4;
    //println("OB_p3_x " + obstacle_p3_x);
    obstacle_p7_y = y1_4;
    //println("OB_p3_y " + obstacle_p3_y);
    obstacle_p8_x = x1_4 + x2_4;
    //println("OB_p4_x " + obstacle_p4_x);
    obstacle_p8_y = y1_4;
    //println("OB_p4_y " + obstacle_p4_y);
    
    if (collision()) { 
      gameOver = true;
      write = false;
      speed = 0;
    }
    if (this.x1_2 <= -100) {
      this.offScreen = true;
    }
    keepScore();
  }

  public boolean getScreen() {
    return this.offScreen;
  }

  private void keepScore() {
    if (!x1_1_pass) {
      if (player_x_score > x1_1) {
        score++;
        x1_1_pass = true;
        //println("x1_1 Score : " + score);
      }
    } else if (!x1_2_pass) {
      if (player_x_score > x1_2) {
        score++;
        x1_2_pass = true;
        //println("x1_2 Score : " + score);
      } 
    } else if (!x1_3_pass) {
      if (player_x_score > x1_3) {
        score++; 
        x1_3_pass = true;
        //println("x1_3 Score : " + score);
      } 
    } else if (!x1_4_pass) {
      if (player_x_score > x1_4) {
        score++;
        x1_4_pass = true;
        //println("x1_4 Score : " + score);
      }  
    }
  }

  private void reset() {
    this.x1_1 = 900;
    this.y1_1 = 0;
    this.x2_1 = 100;
    this.y2_1 = 250;
    this.x1_2 = 900;
    this.y1_2 = 0;
    this.x2_2 = 100;
    this.y2_2 = 250;
  }

  private boolean collision() {
    if ((player_p1_x >= obstacle_p1_x) && (player_p1_x <= obstacle_p2_x)
      && (player_p1_y <= obstacle_p1_y)) {
      return true;
    } else if ((player_p2_x >= obstacle_p1_x) && (player_p2_x <= obstacle_p2_x)
      && (player_p2_y <= obstacle_p1_y)) {
      return true;
    } else if ((player_p3_x >= obstacle_p3_x) && (player_p3_x <= obstacle_p4_x)
      && (player_p3_y >= obstacle_p3_y)) {
      return true;
    } else if ((player_p4_x >= obstacle_p3_x) && (player_p4_x <= obstacle_p4_x)
      && (player_p4_y >= obstacle_p3_y)) {
      return true;
    } else if ((player_p4_x >= obstacle_p3_x) && (player_p4_x <= obstacle_p4_x)
      && (player_p4_y >= obstacle_p3_y)) {
      return true;
    } else if ((player_p1_x >= obstacle_p5_x) && (player_p1_x <= obstacle_p6_x)
      && (player_p1_y <= obstacle_p5_y)) {
      return true;
    } else if ((player_p2_x >= obstacle_p5_x) && (player_p2_x <= obstacle_p6_x)
      && (player_p2_y <= obstacle_p5_y)) {
      return true;
    } else if ((player_p3_x >= obstacle_p7_x) && (player_p3_x <= obstacle_p8_x)
      && (player_p3_y >= obstacle_p7_y)) {
      return true;
    } else if ((player_p4_x >= obstacle_p7_x) && (player_p4_x <= obstacle_p8_x)
      && (player_p4_y >= obstacle_p7_y)) {
      return true;
    } else if ((player_p4_x >= obstacle_p7_x) && (player_p4_x <= obstacle_p8_x)
      && (player_p4_y >= obstacle_p7_y)) {
      return true;
    }     
    return false;
  }
}

public class player_character {
  private PImage player, player_left, player_right, player_transform;
  private float p_hover_direction, y_core_coordinate, speed;
  private float p_x_coord, p_y_coord;
  private boolean facing_right, alternated;
  private int time_1, time_2;
  private boolean goHover, transforming, cooloffCheck;
  private int transform_time_1, transform_time_2;

  public player_character() {
    // Character hovering configurations
    this.player_left = loadImage("./images/player_left.png");
    this.player_right = loadImage("./images/player_right.png");
    this.player_transform = loadImage("./images/player_transform.png");
    this.player = player_left;
    this.p_hover_direction = 1;
    this.p_x_coord = window_width/18;
    this.p_y_coord = window_height/3;
    this.speed = 3;
    this.facing_right = true;
    this.time_1 = second();
    this.time_2 = second();
    this.alternated = false;
    this.goHover = true;
    this.transforming = false;
    this.cooloffCheck = false;

    updateY(0);
  }

  public void draw() {
    if (this.transforming) {
      transform();
    } else {
      player_alternate();
    }
    fill(255, 0, 0, 63);
    rect(this.p_x_coord, this.p_y_coord, this.player.width, this.player.height);
    image(this.player, this.p_x_coord, this.p_y_coord);
    if (goHover) {
      hover(this.p_y_coord);
    }
    player_p1_x = this.p_x_coord;
    player_p1_y = this.p_y_coord;
    player_p2_x = this.p_x_coord + this.player.width;
    player_p2_y = this.p_y_coord;
    player_p3_x = this.p_x_coord;
    player_p3_y = this.p_y_coord + this.player.height;
    player_p4_x = this.p_x_coord + this.player.width;
    player_p4_y = this.p_y_coord + this.player.height;

    player_x_score = this.p_x_coord;

    if ((magic_number < 100) && (!cooloffCheck)) {
      magic_number += 0.021875f;
    }
    if (cooloffCheck) {
      magic_number += 0.04375f;
      if (magic_number >= 10) {
        cooloffCheck = false;
        stall = false;
      }  
    }
  }

  public void setTransform() {
    if (magic_number != 0) {
      this.transforming = true;
    } else {
      setTransformOff();
    }
  }

  public void setTransformOff() {
    this.transforming = false;
  }

  public void transform() {
    if ((magic_number >= 1) && (!stall)) {
      magic_number -= 1;
      this.player = this.player_transform;
    } if (magic_number <= 1) {
      setTransformOff();
      cooloffCheck = true;
      stall = true;
    }
  }

  private void cooloff() {
    magic_number += 1;
    if (magic_number > 10) {
      cooloffCheck = false;
    }
  }

  public void player_alternate() {
    if (!this.alternated) {
      this.time_1 = second();
      this.alternated = true;
    }
    if (time_2 - time_1 >= 1) {
      if (this.player == this.player_left) {
        this.player = this.player_right;  
        this.alternated = false;
      } else {
        this.player = this.player_left;
        this.alternated = false;
      }
    }
    this.time_2 = second();
  }

  private void stopHover() {
    this.goHover = false;
  }

  public void startHover() {
    this.goHover = true;
  }

  /* Simply changes the y coordinates of the character to
   simulate the character hovering */
  public void hover(float y_coordinate) {
    if ((y_coordinate >= y_core_coordinate+25) || (y_coordinate <= y_core_coordinate-25)) {
      p_hover_direction *= -1;
    }
    p_y_coord += p_hover_direction;
  }

  // Update the Y coordinates for this class so that 
  // the hover knows how far to hover up and down
  private void updateY(float y_coordinate) {
    this.y_core_coordinate = y_coordinate;
  }

  // Move up
  public void y_up() {
    this.facing_right = false;
    updateY(this.p_y_coord);
    this.p_y_coord -= 10;
    this.goHover = false;
  }

  // Move down
  public void y_down() {
    this.facing_right = false;
    updateY(this.p_y_coord);
    this.p_y_coord += 10;
    this.goHover = false;
  }

  // Check the character is within the screen boundaries
  private boolean checkScreen() {
    println(this.y_core_coordinate);
    if (this.y_core_coordinate <= 0) {
      return false;
    } else {
      return true;
    }
  }
}

public class scoreDisplay {
  PFont font;
  int box_x, box_y;
  String[] manaDisplay = {"MANA: ", "STALL: "};
  
  public scoreDisplay() {
    this.font = createFont("ds.ttf", 72);
    textFont(this.font);
    this.box_x = window_width/2;
    this.box_y = window_width/2-100;
  }  
  
  public void draw() {
    time = millis() - startTime;
    fill(0, 255, 0, 127);
    rect(box_x, box_y, window_width/3, window_height/3-30);
    fill(200, 0, 0);
    if (stall) {
      text(manaDisplay[1] + str(magic_number), box_x+10, box_y+130); 
    } else {
      text(manaDisplay[0] + str(magic_number), box_x+10, box_y+130); 
    }
    fill(200, 0, 0);
    text("SCORE: " + str(score), box_x+10, box_y+65);
    fill(200, 0, 0);
    text("TIME: " + time/1000, box_x+10, box_y+190);
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_7766756e67" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
