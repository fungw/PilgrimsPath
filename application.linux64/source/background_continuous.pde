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
