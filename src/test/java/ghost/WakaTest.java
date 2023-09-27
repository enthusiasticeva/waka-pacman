package ghost;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WakaTest {

  @Test
  public void testConstrutor() {
    Map map = new Map("map1.txt");
    Waka waka = new Waka(map.getWakaStartCoords()[0]*16, map.getWakaStartCoords()[1]*16, map, 2, 3);

    assertNotNull(waka);
    assertTrue(waka.isMouthOpen());
    assertEquals(3, waka.getLives());

  }

  @Test
  public void testCollectFruit() {
    App app = new App();
    Map map = new Map("map1.txt");
    Waka waka = new Waka(map.getWakaStartCoords()[0]*16, map.getWakaStartCoords()[1]*16, map, 2, 3);


    for (int i = 0; i < 16; i ++) {
      waka.moveForwards();
    }

    waka.collectFruit(app);
    assertEquals(1, map.getFruitCurrent());

    assertEquals(cellType.Empty, map.getCellMap().get(waka.y/16).get(waka.x/16).getType());
  }

  @Test
  public void testCheckWakaGhostCollision() {
    App app = new App("map.txt");

    int wakaOriginX = 208;
    int wakaOriginY = 320;

    assertEquals(208, app.getWaka().getX());
    assertEquals(320, app.getWaka().getY());
    app.getGhosts().get(0).setX(wakaOriginX+16);
    app.getGhosts().get(0).setY(wakaOriginY);

    app.getGhosts().get(0).frightenedMode = true;

    // No ghosts should be where the waka is
    assertFalse(app.getWaka().checkWakaGhostCollision(app));

    app.getWaka().setDirection(Direction.right);

    //As speed is set to 2, the waka only needs to move 8 times
    for (int i = 0; i < 8; i ++) {
      app.getWaka().moveForwards();
    }

    //Double checking that the waka and ghost are now on top of each other
    assertEquals(0, app.getWaka().getX()-app.getGhosts().get(0).getX());
    assertEquals(0, app.getWaka().getY()-app.getGhosts().get(0).getY());

    assertTrue(app.getWaka().checkWakaGhostCollision(app));
    assertFalse(app.getGhosts().get(0).alive);


    //
    app.getGhosts().get(1).setX(wakaOriginX+16+16);
    app.getGhosts().get(1).setY(wakaOriginY);

    for (int i = 0; i < 8; i ++) {
      app.getWaka().moveForwards();
    }

    assertTrue(app.getWaka().checkWakaGhostCollision(app));
    assertEquals(wakaOriginX, app.getWaka().getX());
    assertEquals(wakaOriginY, app.getWaka().getY());

    // Checking with a non alive ghost
    app.getGhosts().get(1).setX(wakaOriginX+32+16);
    app.getGhosts().get(1).setY(wakaOriginY);

    app.getGhosts().get(1).setAlive(false);

    for (int i = 0; i < 8; i ++) {
      app.getWaka().moveForwards();
    }

    assertFalse(app.getWaka().checkWakaGhostCollision(app));

  }

  @Test
  public void testTick() {
    App app = new App("map1.txt");

    app.getWaka().setMouthOpen(true);
    app.frameCount = 16;
    app.getWaka().tick(app);
    assertFalse(app.getWaka().isMouthOpen());

    for (int i = 0; i < 7; i++) {
      app.getWaka().moveForwards();
    }

    int originalX = app.getWaka().getX();
    int originalY = app.getWaka().getY();

    app.getWaka().setDirection(Direction.up);

    app.getWaka().tick(app);

    assertEquals(originalX, app.getWaka().getX());
    assertEquals(originalY, app.getWaka().getY());

  }

}