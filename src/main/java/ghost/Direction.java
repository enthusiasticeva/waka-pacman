package ghost;

import processing.core.PImage;

/**
 * An enum that holds the four possible directions for entities.
 * These directions also contain the image path and sprite for the 
 * corresponding direction of Waka, as well as a String of the opposite direction.
 */
public enum Direction {
  up("playerUp.png", "down"), 
  down("playerDown.png", "up"), 
  left("playerLeft.png", "right"),
  right("playerRight.png", "left");

  private String wakaImagePath;
  private PImage sprite;
  private String oppDirString;

  Direction(String imagePath, String oppDirString) {
    this.wakaImagePath = imagePath;
    this.oppDirString = oppDirString;
  }

  /**
   * A getter for the opposite direction as a string.
   * @return String this.oppDirString
   */
  public String getOppDirString() {
    return this.oppDirString;
  }

  /**
   * A setter for the sprite (PImage) for each direction. 
   * This is created based of the included image file name.
   * @param sprite
   */
  public void setSprite(PImage sprite) {
    this.sprite = sprite;
  }

  /**
   * A getter for the PImage sprite
   * @return PImage this.sprite
   */
  public PImage getSprite() {
    return this.sprite;
  }

  /**
   * A getter for each directions corresponding Waka image path.
   * @return String this.wakaImagePath
   */
  public String getWakaImagePath() {
    return this.wakaImagePath;
  }
}