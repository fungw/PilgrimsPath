// Images
private player_character player;
private background_continuous bg1;
private obstacles obstacles, obstacles1, obstacles2, obstacles3, obstacles4;
private gameOver gg;
private scoreDisplay scoreDisplay;

void setup() {
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

void draw() {
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

void keyPressed() {
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

void keyReleased() {
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

boolean sketchFullScreen() {
  return true;
}

