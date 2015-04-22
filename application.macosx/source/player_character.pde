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
      magic_number += 0.021875;
    }
    if (cooloffCheck) {
      magic_number += 0.04375;
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

