package ghost;

import java.util.Random;

/**
 * A specific type of ghost that uses the chaser and waka's position to calculate its target.
 */
public class Whim extends Ghost {

  private Chaser chaser;


  public Whim(int x, int y, Map map, int speed, Waka waka, Chaser chaser) {
    super(x, y, map, speed, waka,ghostType.Whim);
    this.chaser = chaser;
  }

  /**
   * The tick method is run once every frame, it calculates the target location depending 
   * on the ghost's mode, then chooses a direction to move in and moves forwards. 
   * In the whim's case, this is double the vector from Chaser to 2 grid spaces ahead of Waka in chase mode, 
   * and the top left corner in scatter mode.
   * In frightened mode, the target location is randomised.
   */
  public void tick(App app) { 
    this.checkFrightenedEnd(app);
    this.checkScatterMode(app);

    if (!this.frightenedMode) {
      if (this.scatterMode) {
        this.targetX = app.width;
        this.targetY = app.height;
      } else {
        if (this.chaser.alive) {
          int[] target = this.calculateTarget(app);
          this.targetX = target[0];
          this.targetY = target[1];
        } else {
          this.scatterMode = true;
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

  /**
   * Calculates the target location of the Whim based on the waka and chaser. 
   * This target is double the vector from Chaser to 2 grid spaces ahead of Waka.
   * @param app
   * @return int[] coordinates
   */
  public int[] calculateTarget(App app) {
    int xDist = app.getWaka().getX()-this.chaser.getX();
    int yDist = app.getWaka().getY()-this.chaser.getY();

    int xVal = this.chaser.getX() + 2 * xDist;
    int yVal = this.chaser.getY() + 2 * yDist;

    return new int[] {xVal,yVal};
  }
  
}