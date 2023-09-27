package ghost;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Provides generic functionality for a ghost, which is then used to create the specific behaviour of the ghost types.
 * Extended by Ambusher, Chaser, Ignorant and Whim.
 */
public abstract class Ghost extends Entity{
  public HashMap<String,Boolean> options;
  protected Waka waka;

  static protected PImage frightenedSprite;

  protected boolean scatterMode;
  protected boolean frightenedMode;
  protected boolean alive;
  protected boolean debugMode;

  protected int targetX;
  protected int targetY;

  private int frameFrightenedStart;
  private int frameScatterStart;

  private List<Integer> scatterLengths;
  private int scatterLengthsTotal;

  protected ghostType type;

  public Ghost(int x, int y, Map map, int speed, Waka waka, ghostType type) {
    super(x,y,map,speed);
    this.waka = waka;
    this.scatterMode = false;
    this.frightenedMode = false;
    this.type = type;
    this.alive = true;
    this.frameFrightenedStart = -1;
    this.frameScatterStart = -1;
    this.debugMode = false;
  }

  /**
   * A setter for the Ghosts x coordinate (used for testing)
   * @param x
   */
  public void setX(int x) {
    this.x = x;
  }
  
  /**
   * A setter for the Ghosts y coordinate (used for testing)
   * @param y
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * A setter for the whether the ghost is currently aive
   * @param v
   */
  public void setAlive(boolean v) {
    this.alive = v;
  }

  /**
   * A getter for the Ghosts associated Waka
   * @return Waka this.waka
   */
  public Waka getWaka() {
    return this.waka;
  }

  /**
   * A getter for the frame on which frightened mode began.
   * If frightened mode is not currently in progress, this value will be -1.
   * @return int this.frameFrightenedStart
   */
  public int getFrameFrightenedStart() {
    return this.frameFrightenedStart;
  }

  /**
   * A getter for the frame on which scatter mode began.
   * If scatter mode is not currently in progress, this value will be -1.
   * @return int this.frameScatterStart
   */
  public int getFrameScatterStart() {
    return this.frameScatterStart;
  }

  /**
   * Toggles the scatter mode. If the scatter mode is being toggled on, 
   * the frame on which this happened is stored in this.frameScatterStart.
   * @param app
   * @return boolean this.scatterMode
   */
  public boolean toggleScatter(App app) {
    this.scatterMode = !this.scatterMode;
    this.frameScatterStart = app.frameCount;

    return this.scatterMode;
  }

  /**
   * Begins frightened mode, sets frightenedMode to true, and saves the frame on which frightened mode started.
   * @param app
   * @return
   */
  public boolean triggerFrightened(App app) {
    this.frameFrightenedStart = app.frameCount;
    this.frightenedMode = true;

    return this.frightenedMode;
  }

  /**
   * Ends frightened more, sets frightenedMode to false, and sets frameFrightenedStart back to -1.
   * @param app
   * @return boolean this.frightenedMode
   */
  public boolean endFrightened(App app) {
    this.frightenedMode = false;
    this.frameFrightenedStart = -1;
    
    return this.frightenedMode;
  }

  /**
   * A setter for the Ghost's sprite (image)
   * @param sprite
   */
  public void setSprite(PImage sprite) {
    this.sprite = sprite;
  }

  /**
   * A setter for the Ghost's frightened sprite (image)
   * @param sprite
   */
  public static void setFrightenedSprite(PImage s) {
    frightenedSprite = s;
  }

  /**
   * A getter for the Ghosts frightened sprite
   * @return PImage frightenedSprite
   */
  public static PImage getFrightenedSprite() {
    return frightenedSprite;
  }

  /**
   * Checks if the ghost currently has the same coordinates as the Waka
   * @return boolean, whether or not the ghost is on the waka
   */
  public boolean onWaka() {
    if (this.x == this.waka.x && this.y == this.waka.y) {
      return true;
    }
    return false;
  }

  /**
   * Calculates the straight line distance between two sets of coordinates
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   * @return double distance between (x1,y1) and (x2,y2)
   */
  public double calcDistance(int x1, int y1, int x2, int y2) {
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
  }

  /**
   * Calculates the straight line distance between the target and 
   * each possible direction, and finds the direction with the minimum length.
   * i.e. if UP is possible (found with the options hashmap), then the distance 
   * between the cell 1 up from the ghost and the target will be calculated.
   * @param targetX
   * @param targetY
   * @return int keyCode - the keycode corresponding to the direciton chosen.
   */
  public int chooseDirection(int targetX, int targetY) {
    if (!atIntersection()) {
      return -1;
    }
    double min = 1000000000;
    int minDireciton = 0;

    double upDistance = -1;
    double downDistance = -1;
    double leftDistance = -1;
    double rightDistance = -1;

    if (this.options.get("up")) {
      upDistance = calcDistance(this.x, this.y-16, targetX, targetY);

      if (upDistance < min) {
        minDireciton = 38;
        min = upDistance;
      }
    }

    if (this.options.get("down")) {
      downDistance = calcDistance(this.x, this.y+16, targetX, targetY);

      if (downDistance < min) {
        minDireciton = 40;
        min = downDistance;
      }
    }

    if (this.options.get("left")) {
      leftDistance = calcDistance(this.x-16, this.y, targetX, targetY);

      if (leftDistance < min) {
        minDireciton = 37;
        min = leftDistance;
      }
    }

    if (this.options.get("right")) {
      rightDistance = calcDistance(this.x+16, this.y, targetX, targetY);

      if (rightDistance < min) {
        minDireciton = 39;
        min = rightDistance;
      }
    }

    return minDireciton;
  }

  /**
   * Checks if the Ghost is currently at an intersection, and stores all possible directions in a hashmap.
   * The opposite of the current direction is always set to false to prevent the ghost from turning back on itself.
   * @return boolean if the ghost is currently at an intersection
   */
  public boolean atIntersection() {
    int currentCellIndexY = this.y/16;
    int currentCellIndexX = this.x/16;

    Cell up = this.map.getCellMap().get(currentCellIndexY - 1).get(currentCellIndexX);
    Cell down = this.map.getCellMap().get(currentCellIndexY + 1).get(currentCellIndexX);
    Cell left = this.map.getCellMap().get(currentCellIndexY).get(currentCellIndexX - 1);
    Cell right = this.map.getCellMap().get(currentCellIndexY).get(currentCellIndexX + 1);

    HashMap<String,Boolean> opt = new HashMap<String,Boolean>();

    opt.put("up", up.isClear());
    opt.put("down", down.isClear());
    opt.put("left", left.isClear());
    opt.put("right", right.isClear());


    opt.put(this.direction.getOppDirString(), false); // Prevents the ghost from turning back on itself
    

    this.options = opt;

    return (left.isClear()||right.isClear()||up.isClear()||down.isClear());
  }
  
  /**
   * Checks if enough time has passed (set by firghtenedLength in the config file) for frightened mode to end.
   * @param app
   */
  public void checkFrightenedEnd(App app) {
    if (this.frightenedMode == false) {
      return;
    }
    int len = app.getConfig().getFrightenedLength().intValue();
    if (app.frameCount > this.frameFrightenedStart + len * 60) {
      Ghost.endFrightenedMode(app.getGhosts(), app);
    }
  }

  /**
   * Calls the toggleScatter method on a list of ghosts
   * @param ghosts
   * @param app
   */
  public static void toggleScatterMode(List<Ghost> ghosts, App app) {
    for (Ghost ghost: ghosts) {
      ghost.toggleScatter(app);
    }
  } 

  /**
   * Calls the triggerFrightened method on a list of ghosts
   * @param ghosts
   * @param app
   */
  public static void triggerFrightenedMode(List<Ghost> ghosts, App app) {
    for (Ghost ghost: ghosts) {
      ghost.triggerFrightened(app);
    }
  }

  /**
   * Calls the endFrightened method on a list of ghosts
   * @param ghosts
   * @param app
   */
  public static void endFrightenedMode(List<Ghost> ghosts, App app) {
    for (Ghost ghost: ghosts) {
      ghost.endFrightened(app);
      ghost.frameFrightenedStart = -1;
    }
  }

  /**
   * Takes in a list of times (passed from the config file), and converts 
   * it to a list of Integers, as well as calculating the total duration of these times.
   * @param longLengths the scatter lengths as a list of Longs (this is the format they come in from Config)
   */
  public void setScatterLengths(Long[] longLengths) {
    List<Integer> lengths = new ArrayList<Integer>();
    int total = 0;
    for (Long l : longLengths) {
      Integer n = l.intValue()*60;
      total += n;
      lengths.add(total);
    }
    this.scatterLengths = lengths;
    this.scatterLengthsTotal = total;
  }

  /**
   * Checks if enough time has passed for scatter mode to be toggled (based on the scatterLengths)
   * @param app the ghost's associated app
   */
  public void checkScatterMode(App app) {
    if (this.scatterLengths.contains(app.frameCount % this.scatterLengthsTotal+1)) {
      this.scatterMode = !this.scatterMode;
    }
  }

  /**
   * Toggles debug mode for all ghosts in the app.
   * @param app
   */
  public static void toggleDebugMode(App app) {
    for (Ghost ghost: app.getGhosts()) {
      ghost.debugMode = !ghost.debugMode;
    }
  }

  public abstract void tick(App app);


  public void draw(PApplet app) {
    // Handles graphics
    if (this.alive){
      if (!this.frightenedMode) {
        app.image(this.type.getSprite(),x-4,y-5);
      } else {
        app.image(frightenedSprite,x-4,y-5);
      }
    }
    
    // If debug mode is enables, a line should be drawn between the ghost and the target.
    if (this.alive && this.debugMode){
      app.stroke(255,255,255);
      app.line(this.x+8, this.y+8, this.targetX+8, this.targetY+8);
    }
    
    

  }

  
}