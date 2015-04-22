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
