import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ParseMap {
  public static Scanner openFileRead (String filename) {
    try {
        File myFile = new File(filename);
        Scanner scan = new Scanner(myFile); 
        return scan;

    } catch (FileNotFoundException e) {
      return null;
    }
  }

  
  // public static List<List<Cell>> parseMap(String filename) {
  //   List<List<Cell>> cellMap = new ArrayList<List<Cell>>();
  //   List<List<String>> lettersMap = new ArrayList<List<String>>();

  //   Scanner file = openFileRead(filename);

  //   while (file.hasNext()) {
  //     String line = file.next().trim();
  //     String[] letters = line.split("");
  //     lettersMap.add(Arrays.asList(letters));
  //   }

  //   int rowNum = 0;
  //   int colNum = 0;

  //   for (List<String> row: lettersMap) {
  //     List<Cell> cellRow = new ArrayList<Cell>();
  //     for (String col: row) {
  //       Cell c;
  //       int num;
  //       if (col.equals("p")) {
  //         num = 8;
  //       } else if (col.equals("g")) {
  //         num = 9;
  //       } else {
  //         num = Integer.valueOf(col);
  //       }
  //       if (num > 0 && num < 7) {
  //         c = new Wall(num, colNum*16, rowNum*16);
  //       } else {
  //         c = new Space(num, colNum*16, rowNum*16);
  //       }
  //       colNum ++;
  //       cellRow.add(c);
  //     }
  //     cellMap.add(cellRow);
  //     rowNum ++;
  //     colNum = 0;
  //   }

  //   return cellMap;
  // }
}