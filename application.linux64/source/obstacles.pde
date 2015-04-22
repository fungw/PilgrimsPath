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

  void draw() {
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

