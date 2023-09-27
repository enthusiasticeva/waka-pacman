package ghost;

import java.util.List;

import processing.core.PImage;

/**
 * An extension of entity that stores all information and mechanics of the waka.
 */
public class Waka extends Entity{

  private boolean mouthOpen;

  private static PImage closedSprite;
  private int lives;


  public Waka(int x, int y, Map map, int speed, int lives) {
    super(x, y, map, speed);
    this.mouthOpen = true;
    this.lives = lives;
    
  }

  /**
   * A setter for the image of the waka with a closed mouth.
   * @param sprite
   */
  public static void setClosedSprite(PImage sprite) {
    closedSprite = sprite;
  } 

  /**
   * A getter for the image of the waka with a closed mouth.
   * @return closedSprite (PImage)
   */
  public static PImage getClosedSprite() {
    return closedSprite;
  }

  /**
   * A getter for the Waka's lives count
   * @return this.lives (int)
   */
  public int getLives() {
    return this.lives;
  }

  /**
   * A setter for the Waka's lives count (Used for testing)
   * @param l
   */
  public void setLives(int l) {
    this.lives = l;
  }

  /**
   * A getter for whether the Waka's mouth is currently open.
   * @return this.mouthOpen (boolean)
   */
  public boolean isMouthOpen() {
    return this.mouthOpen;
  }

  /**
   * A setter for whether the Waka's mouth is currently open.
   * @param b
   */
  public void setMouthOpen(boolean b) {
    this.mouthOpen = b;
  }

  /**
   * A setter for the Waka's direction.
   * @param d
   */
  public void setDirection(Direction d) {
    this.direction = d;
    this.nextDirection = d;
  }

  /**
   * Gets the Waka's current location (in terms of the List of List of cells) 
   * and calls collectfruit with these coordinates.
   */
  public void collectFruit(App app) {
    int xVal = this.x/16;
    int yVal = this.y/16;

    this.map.collectFruit(xVal, yVal, app);
  }

  /**
   * Checks if the waka is currently colliding with a ghost. 
   * If a collision is occuring, decrements the waka's lives and sends all entities back to their original position.
   * @param app
   * @return boolean, whether there has been a collision.
   */
  public boolean checkWakaGhostCollision(App app) {
    List<Ghost> ghosts = app.getGhosts();

    boolean collision = false;
    Ghost collidingGhost = null;
    for (Ghost ghost: ghosts) {
      if (ghost.onWaka() && ghost.alive) {
        collision = true;
        collidingGhost = ghost;

      }
    }

    if (collision) {
      if (collidingGhost.frightenedMode) {
        collidingGhost.alive = false;
      } else {
        this.lives --;
        this.returnToOrigin();

        boolean addedBack = false;

        for (Ghost ghost: ghosts) {
          ghost.returnToOrigin();
          if (!ghost.alive && !addedBack) {
            ghost.alive = true;
            addedBack = true;
            Ghost.endFrightenedMode(app.getGhosts(), app);
          }

        }
      }
    }
    return collision;
  }

  /**
   * Toggles whether the mouth is open every 8 frames, checks if moving forward in the current direciton would 
   * collide with a wall, if the waka can turn to it's next direciton, 
   * checks for collisions with ghosts, collects any fruit and moves the waka forward.
   * @param app
   */
  public void tick(App app) {

    if (app.frameCount % 8 == 0) {

      if (this.mouthOpen) {
        this.sprite = closedSprite;

        this.mouthOpen = false;

      } else {
        this.sprite = this.direction.getSprite();

        this.mouthOpen = true;
      }
    }

    if (!this.checkCollision(this.nextDirection)) {
      this.changeDirection(this.nextDirection);
    }

    this.checkWakaGhostCollision(app);
    

    if (this.x%16 == 0 && this.y%16 == 0){
      this.collectFruit(app);
      if (this.checkCollision(this.direction)) {
        return;
      }
    }

    this.moveForwards();


  }

  public void draw(App app) {
    app.image(this.sprite,x-4,y-4);
  }

}
