package ghost;

import processing.core.PImage;

import java.util.List;

import processing.core.PApplet;

/**
 * Cell is an abstract class that provides a blueprint for each cell of the map. 
 * This class stores a cellType enum instance, and is implemented by Space and Wall
 */
public abstract class Cell {
  private int x;
  private int y;

  private PImage sprite;

  private cellType type;

  public Cell(int num, int x, int y) {
    this.x = x;
    this.y = y;
    
    this.setType(getCellType(num));
  }

  public abstract boolean isClear();

  /**
     * Getter for the Cells's associated cellType enum instance
     * @return this.type
     */
  public cellType getType() {
	  return this.type;
  } 

  /**
   * A method that takes the number assigned to the cell (from the map), 
   * and returns the type of cell, from the cellType enum.
   * @param n the number read from the map file that corresponds to a certian type of cell
   * @return cellType
   */
  public cellType getCellType(int n) {
    cellType[] types = cellType.values();
    return types[n];
  }

  /**
   * Setter for this.type
   * @param w a cellType enum instance.
   */
  public void setType(cellType w) {
	  this.type = w;
  }

  /**
   * A setter for this.sprite (PImage instance)
   * @param sprite
   */
  public void setSprite(PImage sprite) {
      this.sprite = sprite;
  }

  /**
   * Clears a cell (used once a piece of fruit has been collected).
   */
  public void empty() {
    this.sprite = null;
    this.type = cellType.Empty;
  }

  /**
   * Draws the cell onto the screen.
   * If the sprite is null, then the cell is meant to be empty, and can be skipped.
   * @param app the app on which the cell is being drawn
   */
  public void draw(PApplet app) {
    // Handles graphics
    if (this.type.getSprite() == null) {
      return;
    }
    app.image(this.type.getSprite(),x,y);
  }

  /**
   * A static method to call the draw method for all cells in a two dimesional list of cells
   * @param app the app on which the cell is being drawn
   * @param cellMap the List of List of Cells that draw needs to be called through
   */
  public static void drawAll(App app, List<List<Cell>> cellMap) {
    for (List<Cell> row: cellMap) {
      for (Cell cell: row) {
        cell.draw(app);
      }
    }
  }

}