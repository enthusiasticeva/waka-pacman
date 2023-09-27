package ghost;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AmbusherTest {
  @Test
  public void testFindTarget() {
    Map map = new Map("map.txt");
    Waka waka = new Waka(0, 0, map, 2, 2);
    Ambusher ambusher = new Ambusher(0, 0, map, 2, waka);

    assertEquals(Direction.right, waka.getDirection());

    ambusher.findTarget();
    assertEquals(waka.getX()+(5*16), ambusher.targetX);
    assertEquals(waka.getY(), ambusher.targetY);

    waka.setDirection(Direction.left);
    ambusher.findTarget();
    assertEquals(waka.getX()-(4*16), ambusher.targetX);
    assertEquals(waka.getY(), ambusher.targetY);

    waka.setDirection(Direction.up);
    ambusher.findTarget();
    assertEquals(waka.getX(), ambusher.targetX);
    assertEquals(waka.getY()-(4*16), ambusher.targetY);

    waka.setDirection(Direction.down);
    ambusher.findTarget();
    assertEquals(waka.getX(), ambusher.targetX);
    assertEquals(waka.getY()+(5*16), ambusher.targetY);
  }

  @Test
  public void testTick() {
    App app = new App("map.txt");

    Waka waka = app.getWaka();

    Ghost ambusher = app.getGhosts().get(0);

    //Moves the waka right to one wall
    for (int i = 0; i < 100; i ++) {
      waka.moveForwards();
    }

    assertFalse(ambusher.frightenedMode);
    assertFalse(ambusher.scatterMode);

    int originalX = ambusher.x;
    int originalY = ambusher.y;

    ambusher.direction = Direction.right;

    ambusher.tick(app);

    assertEquals(originalX + 2, ambusher.x);
    assertEquals(originalY, ambusher.y);

    originalX = ambusher.x;
    originalY = ambusher.y;

    ambusher.toggleScatter(app);
    ambusher.tick(app);
    assertEquals(originalX + 2, ambusher.x);
    assertEquals(originalY, ambusher.y);

    originalX = ambusher.x;
    originalY = ambusher.y;
    ambusher.triggerFrightened(app);
    ambusher.tick(app);

    assertNotEquals(originalX, ambusher.x);
    
  }
}