package ghost;

import processing.core.PImage;

/**
 * Defined basic functionality for a entity (any object that moves on the screen).
 * Extended by Waka and Ghost
 * 
 */
public abstract class Entity {
  protected int x;
  protected int y;

  private int originalX;
  private int originalY;

  private int speed;

  protected PImage sprite;

  protected Direction direction;
  protected Direction nextDirection;
  

  protected Map map;

  public Entity(int x, int y, Map map, int speed) {
    this.x = x;
    this.y = y;
    this.originalX = x;
    this.originalY = y;
    this.direction = Direction.right;
    this.nextDirection = Direction.right;
    this.map = map;
    this.speed = speed;
  }
 
  /**
   * A setter for the sprite (PImage) for each direction. 
   * @param sprite
   */
  public void setSprite(PImage sprite) {
    this.sprite = sprite;
  }

  /**
   * A getter for the sprite (PImage) for each direction. 
   * This is created based of the included image file name.
   * @return PImage this.sprite
   */
  public PImage getSprite() {
    return this.sprite;
  }

  /**
   * A getter for the entity speed (Used for testing)
   * @param int this.speed
   */
  public int getSpeed() {
    return this.speed;
  }

  /**
   * A getter for the entity's associated Map object
   * @return Map this.map
   */
  public Map getMap() {
    return this.map;
  }

  /**
   * Sets the entity's coordinates back to their starting coordinates
   */
  public void returnToOrigin() {
    this.x = this.originalX;
    this.y = this.originalY;
  }

  /**
   * A setter for the entity's direction
   * @param d
   */
  public void setDirection(Direction d) {
    this.direction = d;
    this.nextDirection = d;
  }

  /**
   * A setter for the entity's next direction
   * @param d
   */
  public void setNextDirection(Direction d) {
    this.nextDirection = d;
  }

  /**
   * A getter for the entity's next direction
   * @return Direction d
   */
  public Direction getNextDirection() {
    return this.nextDirection;
  }

  /**
   * A getter for the entity's  direction
   * @return Direction d
   */
  public Direction getDirection() {
    return this.direction;
  }

  /**
   * A getter for the entity's x coordinate.
   * @return int this.x
   */
  public int getX() {
    return this.x;
  }

  /**
   * A getter for the entity's y coordinate.
   * @return int this.y
   */
  public int getY() {
    return this.y;
  }

  /**
   * A getter for the entity's starting x coordinate.
   * @return int this.originalX
   */
  public int getOriginalX() {
    return this.originalX;
  }

  /**
   * A getter for the entity's starting y coordinate.
   * @return int this.originalY
   */
  public int getOriginalY() {
    return this.originalY;
  }

  /**
   * Gets the corresponding Direction enum instance based on the keycode it is passed.
   * @param keyCode
   * @return Direction
   */
  public static Direction getDirection(int keyCode) {
    if (keyCode == 37) {
      return Direction.left;
    }
    if (keyCode == 38) {
      return Direction.up;
    }
    if (keyCode == 39) {
      return Direction.right;
    }
    if (keyCode == 40) {
      return Direction.down;
    }
    return null;
  }

  /**
   * Attempts to change the waka's direction. If the waka is properly aligned (fully contained in one cell), 
   * then the direction is changes. Otherwise, the request is saved in the nextDirection attribute.
   * @param d
   */
  public void changeDirection(Direction d) {
    if (d.equals(Direction.right)||d.equals(Direction.left)) {
      if ((this.y)%16 == 0) {
        this.setDirection(d);
      } else {
        this.nextDirection = d;
      }

    } else {
      if ((this.x)%16 == 0) {
        this.setDirection(d);
      } else {
        this.nextDirection = d;
      }
    }
    
  }

  /**
   * Moves the entity forward by one (speed) unit, int the correct direction based on the wakas current direction.
   */
  public void moveForwards() {

    if (this.direction == Direction.right) {
      this.x += this.speed;

    }
    if (this.direction == Direction.left) {
      this.x -= this.speed;

    }
    if (this.direction == Direction.up) {
      this.y -= this.speed;

    }
    if (this.direction == Direction.down) {
      this.y += this.speed;

    }
  }

  /**
   * Retrieves the cell in front of the entity (the next cell it would enter), 
   * and checks if the entity is able to move through it i.e. whether is is a wall.
   * @param d
   * @return boolean 
   */
  public boolean checkCollision(Direction d) {
    int xIndex = -1;
    int yIndex = -1;
    
    if (d == Direction.right) {
      xIndex = (this.x/16)+1;
      yIndex = (this.y/16);
    }
    if (d == Direction.left) {
      xIndex = (this.x/16)-1;
      yIndex = (this.y/16);
    }
    if (d == Direction.up) {
      xIndex = (this.x/16);
      yIndex = (this.y/16)-1;
    }
    if (d == Direction.down) {
      xIndex = (this.x/16);
      yIndex = (this.y/16)+1;
    }

    Cell c = this.map.getCellMap().get(yIndex).get(xIndex);
    return !c.isClear();

  }
  
}