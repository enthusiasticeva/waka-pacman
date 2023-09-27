package ghost;

import processing.core.PImage;
/**
 * An enum that holds all of the different possible types of cells, 
 * their representation on a map, and the image file for them.
 */
public enum cellType {
  Empty("0", null),
  Horizontal("1","horizontal.png"),
  Vertical("2", "vertical.png"),
  CornerUpLeft("3", "upLeft.png"),
  CornerUpRight("4", "upRight.png"),
  CornerDownLeft("5", "downLeft.png"),
  CornerDownRight("6", "downRight.png"),
  Fruit("7","fruit.png"),
  SuperFruit("8","superfruit.png"),
  WakaStart("9", null),
  GhostStart("10", null);

  private String num;

  private String imagePath;

  private PImage sprite;


  cellType(String num, String imagePath) {
    this.num = num;
    this.imagePath = imagePath;
  }

  /**
   * Getter for the associated number of each type of cell.
   * @return String this.num
   */
  public String getNum() {
    return this.num;
  }

  /**
   * Getter for the image path of each type of cell's associated image.
   * @return String this.imagePath
   */
  public String getImagePath() {
    return this.imagePath;
  }

  /**
   * Getter for the sprite of each type of cell's associated image.
   * @return PImage this.sprite()
   */
  public PImage getSprite() {
    return this.sprite;
  }

  /**
   * Setter for each cellType's sprite
   * @param sprite
   */
  public void setSprite(PImage sprite) {
    this.sprite = sprite;
  }
}