package ghost;

import processing.core.PApplet;
import processing.core.PImage;

public class waka_working_before_inheritance {
  protected int x;
  protected int y;

  private boolean mouthOpen;

  protected PImage sprite;
  protected PImage closedSprite;

  protected Direction direction;
  public Direction nextDirection;

  protected Map map;

  public waka_working_before_inheritance(int x, int y, Map map) {
    this.x = x;
    this.y = y;
    this.direction = Direction.left;
    this.nextDirection = Direction.right;
    this.map = map;
    this.mouthOpen = false;
  }

  public void setSprite(PImage sprite) {
    this.sprite = sprite;
  }

  public void setClosedSprite(PImage sprite) {
    this.closedSprite = sprite;
  }

  public void setDirection(Direction d) {
    this.direction = d;
    this.nextDirection = d;
  }

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

  public void moveForwards() {

    if (this.direction == Direction.right) {
      this.x++;

    }
    if (this.direction == Direction.left) {
      this.x--;

    }
    if (this.direction == Direction.up) {
      this.y--;

    }
    if (this.direction == Direction.down) {
      this.y++;

    }
  }

  public boolean checkCollision() {
    int xIndex = -1;
    int yIndex = -1;
    
    if (this.direction == Direction.right) {
      xIndex = (this.x/16)+1;
      yIndex = (this.y/16);
    }
    if (this.direction == Direction.left) {
      xIndex = (this.x/16)-1;
      yIndex = (this.y/16);
    }
    if (this.direction == Direction.up) {
      xIndex = (this.x/16);
      yIndex = (this.y/16)-1;
    }
    if (this.direction == Direction.down) {
      xIndex = (this.x/16);
      yIndex = (this.y/16)+1;
    }

    cellType cellAhead = this.map.cellMap.get(yIndex).get(xIndex).getType();
    if (cellAhead == cellType.Horizontal || cellAhead == cellType.Vertical|| cellAhead == cellType.CornerDownLeft|| cellAhead == cellType.CornerDownRight || cellAhead == cellType.CornerUpLeft|| cellAhead == cellType.CornerUpRight) {
      return true;
    }
    return false;
  }
  
  public void tick(PApplet app) {

    if (app.frameCount % 8 == 0) {

      if (this.mouthOpen) {
        this.sprite = this.closedSprite;

        this.mouthOpen = false;

      } else {
        this.sprite = this.direction.getSprite();

        this.mouthOpen = true;
      }
    }

    this.changeDirection(this.nextDirection);

    if (this.x%16 == 0 && this.y%16 == 0){
      if (this.checkCollision()) {
        return;
      }
    }

    this.moveForwards();

    
  }

  public void draw(PApplet app) {
    // Handles graphics
    app.image(this.sprite,x-4,y-4);
  }

}
