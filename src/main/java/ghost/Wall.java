package ghost;

/**
 * All cell types an entity cannot move through, this includes all wall variations
 */
public class Wall extends Cell{

  public Wall(int num, int x, int y) {
    super(num,x,y);
  }

  /**
   * Returns whether the cell can be moved through by an entity
   * @return false
   */
  public boolean isClear() {
    return false;
  }
  
}

