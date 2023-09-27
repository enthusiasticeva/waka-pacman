package ghost;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Stores a list of list of cells that makes up the map, as well as starting positions for entities and the fruit count.
 */
public class Map {
  private String filename;
  private int fruitTotal;
  private int fruitCurrent;
  private List<List<Cell>> cellMap;
  private int[] wakaStartCoords;
  private List<int[]> ghostStartCoords;
  private List<int[]> ambusherStartCoords; 
  private List<int[]> chaserStartCoords; 
  private List<int[]> ignorantStartCoords; 
  private List<int[]> whimStartCoords; 

  public Map(String filename) {
    this.filename = filename;
    this.wakaStartCoords = new int[] {0,0};
    this.ghostStartCoords = new ArrayList<int[]>();
    this.ambusherStartCoords = new ArrayList<int[]>();
    this.chaserStartCoords = new ArrayList<int[]>();
    this.ignorantStartCoords = new ArrayList<int[]>();
    this.whimStartCoords = new ArrayList<int[]>();
    this.fruitTotal = 0;
    this.fruitCurrent = 0;
    this.cellMap = new ArrayList<List<Cell>>();
    this.parseMap();
  }

  /**
  * Takes in a filename, ensures the file exists and returns a Scanner linked to the file.
  * If the file is not found, null is returned
  * @param filename The name of the file to be read
  * @return Scanner or null
  */
  public static Scanner openFileRead (String filename) {
    try {
      File myFile = new File(filename);
      Scanner scan = new Scanner(myFile); 
      return scan;

    } catch (FileNotFoundException e) {
      return null;
    }
  }

  /**
  * Takes the map file called this.filename, and parses it into 
  * a two-dimensional List of cell objects, which is stored as this.cellMap.
  */
  public void parseMap() {
    List<List<String>> lettersMap = new ArrayList<List<String>>();

    Scanner file = openFileRead(this.filename);

    while (file.hasNext()) {
      String line = file.next().trim();
      String[] letters = line.split("");
      lettersMap.add(Arrays.asList(letters));
    }

    int rowNum = 0;
    int colNum = 0;

    for (List<String> row: lettersMap) {
      List<Cell> cellRow = new ArrayList<Cell>();
      for (String col: row) {
        Cell c;
        int num;
        if (col.equals("p")) {
          num = 0;
          this.wakaStartCoords[0] = colNum;
          this.wakaStartCoords[1] = rowNum;
          // num = 0;

        } else if (col.equals("a")) {
          num = 9;
          int[] thisGhost = new int[] {colNum,rowNum};
          this.ambusherStartCoords.add(thisGhost);
          // num = 0;

        } else if (col.equals("c")) {
          num = 9;
          int[] thisGhost = new int[] {colNum,rowNum};
          this.chaserStartCoords.add(thisGhost);
          // num = 0;

        } else if (col.equals("i")) {
          num = 9;
          int[] thisGhost = new int[] {colNum,rowNum};
          this.ignorantStartCoords.add(thisGhost);
          // num = 0;

        } else if (col.equals("w")) {
          num = 9;
          int[] thisGhost = new int[] {colNum,rowNum};
          this.whimStartCoords.add(thisGhost);
          // num = 0;

        } else {
          if (col.equals("7") || col.equals("8")) {
            this.fruitTotal += 1;
          }
          num = Integer.valueOf(col);
        }
        // System.out.print(col);

        if (num > 0 && num < 7) {
          c = new Wall(num, colNum*16, rowNum*16);
        } else {
          c = new Space(num, colNum*16, rowNum*16);
        }
        // System.out.print(c.getType());
        // System.out.print(" ");
        colNum ++;
        cellRow.add(c);
      }
      // System.out.println();
      this.cellMap.add(cellRow);
      rowNum ++;
      colNum = 0;
    }
    // this.fruitCurrent = this.fruitTotal;
  }


  /**
  * Takes in a filename, ensures the file exists and 
  * returns a Scanner linked to the file.
  * @param app the App on which the lives are being displayed
  */
  public void displayLives(App app) {
    int posY = app.height-32;
    int posX = 8;

    for (int x = 0; x < app.getWaka().getLives(); x++) {
      app.image(Direction.right.getSprite(), posX, posY);
      posX += 32;
    }
    
  }

  

  /**
  * Takes in a filename, ensures the file exists and returns a Scanner linked to the file.
  * If the file is not found, null is returned
  * @param x the x position (in pixels) of the waka
  * @param y the y position (in pixels) of the waka
  * @param app the app that the waka belongs to
  * @return boolean
  */
  public boolean collectFruit(int x, int y, App app) {
    if (this.cellMap.get(y).get(x).getType() == cellType.Fruit) {
      this.fruitCurrent ++;
      this.cellMap.get(y).get(x).empty();
      return true;
    }
    if (this.cellMap.get(y).get(x).getType() == cellType.SuperFruit) {
      Ghost.triggerFrightenedMode(app.getGhosts(), app);
      this.fruitCurrent ++;
      this.cellMap.get(y).get(x).empty();
      return true;
    }
    return false;
  }

  public void setFruitCurrent(int f) {
    this.fruitCurrent = f;
  }

  public int getFruitCurrent() {
    return this.fruitCurrent;
  }

  public int getFruitTotal() {
    return this.fruitTotal;
  }

  public List<List<Cell>> getCellMap() {
    return this.cellMap;
  }

  public List<int[]> getGhostStartCoords() {
    return this.ghostStartCoords;
  }

  public int[] getWakaStartCoords() {
    return this.wakaStartCoords;
  }

  public List<int[]> getGhostTypeStartCoords(ghostType g) {
    if (g == ghostType.Ambusher) {
      return this.ambusherStartCoords;
    }
    if (g == ghostType.Chaser) {
      return this.chaserStartCoords;
    }
    if (g == ghostType.Ignorant) {
      return this.ignorantStartCoords;
    }
    if (g == ghostType.Whim) {
      return this.whimStartCoords;
    }
    return null;
  }
  
}