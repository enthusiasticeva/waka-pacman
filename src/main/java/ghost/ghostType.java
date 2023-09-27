package ghost;

import processing.core.PImage;

/**
 * Provides image filenames and Sprites for the different ghost types.
 */
public enum ghostType {
  Ambusher("ambusher.png"), 
  Chaser("chaser.png"), 
  Ignorant("ignorant.png"), 
  Whim("whim.png");

  private String imagePath;
  private PImage sprite;

  ghostType(String imagePath) {
    this.imagePath = imagePath;

  }

  /**
   * A getter for the image associated with each ghostType.
   * @return this.sprite
   */
  public PImage getSprite() {
    return this.sprite;
  }

  /**
   * A setter for the image associated with each ghostType.
   * @param sprite
   */
  public void setSprite(PImage sprite) {
    this.sprite = sprite;
  }

  /**
   * A getter for the path to the image associated with each ghostType.
   * @return this.sprite
   */
  public String getImagePath() {
    return this.imagePath;
  }

}