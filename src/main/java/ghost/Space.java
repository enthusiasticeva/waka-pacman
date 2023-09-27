package ghost;

/**
 * A type of Cell that is any cell type you can move through, this includes fruit, empty spaces and superfruit.
 */
public class Space extends Cell{
  public Space(int num, int x, int y) {
    super(num,x,y);
  }

  /**
   * Returns whether the cell can be moved through by an entity
   * @return true
   */
  public boolean isClear() {
    return true;
  }
}