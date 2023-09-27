package ghost;

import java.util.Random;

/**
 * A specific type of ghost that targets the waka's exact position.
 */
public class Chaser extends Ghost {
  public Chaser(int x, int y, Map map, int speed, Waka waka) {
    super(x, y, map, speed, waka, ghostType.Chaser);
  }
  /**
   * The tick method is run once every frame, it calculates the target location depending 
   * on the ghost's mode, then chooses a direction to move in and moves forwards. 
   * In the chaser's case, this is the waka in chase mode, and the top left corner in scatter mode.
   * In frightened mode, the target location is randomised.
   */
  public void tick(App app) { 

    this.checkFrightenedEnd(app);
    this.checkScatterMode(app);

    if (!this.frightenedMode) {
      if (this.scatterMode) {
        this.targetX = 0;
        this.targetY = 0;
      } else {
        this.targetX = this.waka.x;
        this.targetY = this.waka.y;
      }
    } else {
      Random random = new Random();
      this.targetX = random.nextInt(app.width);
      this.targetY = random.nextInt(app.height);
    }
    if (this.y % 16 == 0 && this.x % 16 == 0) {
      int dirCode = this.chooseDirection(this.targetX, this.targetY);
      this.changeDirection(getDirection(dirCode));
    }

    app.getWaka().checkWakaGhostCollision(app);

    this.moveForwards();
  }

  
}