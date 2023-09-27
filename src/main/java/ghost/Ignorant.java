package ghost;

import java.util.Random;
/**
 * A specific type of ghost that targets the waka's exact position when more than 8 cells away, then targets a corner.
 */
public class Ignorant extends Ghost {
  public Ignorant(int x, int y, Map map, int speed, Waka waka) {
    super(x, y, map, speed, waka, ghostType.Ignorant);
  }

  public double getWakaDist() {
    double dist = Math.sqrt((this.x - this.waka.x) * (this.x - this.waka.x) + (this.y - this.waka.y) * (this.y - this.waka.y));
    return dist;
  }

  /**
   * The tick method is run once every frame, it calculates the target location depending 
   * on the ghost's mode, then chooses a direction to move in and moves forwards. 
   * In the ignorant's case, this is the waka (if more than 8 cells away) or the bottom left corner (if withing 8 of the waka) in chase mode, 
   * and the bottom left corner in scatter mode.
   * In frightened mode, the target location is randomised.
   */
  public void tick(App app) {

    this.checkFrightenedEnd(app);
    this.checkScatterMode(app);

    if (!this.frightenedMode) {
      if (this.scatterMode) {
        this.targetX = 0;
        this.targetY = app.height;
      } else {
        if (getWakaDist() > 8*16) {
          this.targetX = this.waka.x;
          this.targetY = this.waka.y;
        } else {
          this.targetX = 0;
          this.targetY = app.height;
        }
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