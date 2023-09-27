package ghost;

import java.util.Random;
/**
 * A specific type of ghost that targets 4 cells in front of the waka's position.
 */
public class Ambusher extends Ghost {
  public Ambusher(int x, int y, Map map, int speed, Waka waka) {
    super(x, y, map, speed, waka, ghostType.Ambusher);
  }

  /**
   * The tick method is run once every frame, it calculates the target location depending 
   * on the ghost's mode, then chooses a direction to move in and moves forwards. 
   * In the ambusher's case, this is 4 in front of the waka in chase mode, and the top right corner in scatter mode.
   * In frightened mode, the target location is randomised.
   */
  public void tick(App app) {

    this.checkFrightenedEnd(app);
    this.checkScatterMode(app);

    if (!this.frightenedMode) {
      if (this.scatterMode) {
        this.targetX = app.width;
        this.targetY = 0;
      } else {
        this.findTarget();
      }
    } else {
      Random random = new Random();
      this.targetX = random.nextInt(app.width);
      this.targetY = random.nextInt(app.height);
    }
    if (this.y % 16 == 0 && this.x % 16 == 0) {
      int dirCode = this.chooseDirection(this.targetX,this.targetY);
      this.changeDirection(getDirection(dirCode));
    }

    app.getWaka().checkWakaGhostCollision(app);

    this.moveForwards();
  }

  /**
   * Calculates the Ambushers target coordinates, which are 4 cells ahead of 
   * the waka in whatever direciton the waka is currently going.
   */
  public void findTarget() {
    Direction d = this.waka.direction;
    if (d == Direction.right) {
      this.targetX = this.waka.x + 5*16;
      this.targetY = this.waka.y;
    }
    if (d == Direction.left) {
      this.targetX = this.waka.x - 4*16;
      this.targetY = this.waka.y;
    }
    if (d == Direction.up) {
      this.targetX = this.waka.x;
      this.targetY = this.waka.y - 4*16;
    }
    if (d == Direction.down) {
      this.targetX = this.waka.x;
      this.targetY = this.waka.y+ 5*16;
    }
  }

  
  
}