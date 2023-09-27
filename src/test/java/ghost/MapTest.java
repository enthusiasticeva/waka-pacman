package ghost;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
  @Test
  public void testCollectFruit() {
    App app = new App("map5.txt");

    Map map = app.getMap();

    int xPos = map.getWakaStartCoords()[0];
    int yPos = map.getWakaStartCoords()[1];

    //Empty cell
    assertFalse(map.collectFruit(xPos, yPos, app));

    //Fruit cell
    assertTrue(map.collectFruit(xPos+1, yPos, app));

    //Super fruit cell
    assertTrue(map.collectFruit(xPos+2, yPos, app));



  }
}