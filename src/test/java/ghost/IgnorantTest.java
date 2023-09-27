package ghost;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class IgnorantTest {
  @Test
  public void testTick() {
    App app = new App("map.txt");

    Ghost ignorant = app.getGhosts().get(2);

    ignorant.x = (ignorant.waka.getX()+16);
    ignorant.y = (ignorant.waka.getY());

    double dist = Math.sqrt((ignorant.x - ignorant.waka.x) * (ignorant.x - ignorant.waka.x) + (ignorant.y - ignorant.waka.y) * (ignorant.y - ignorant.waka.y));
    assertEquals(16, dist);

    ignorant.tick(app);
    ignorant.tick(app);
    assertEquals(0, ignorant.targetX);
    assertEquals(app.height, ignorant.targetY);

    ignorant.setX(ignorant.waka.getX()+(8*16));
    ignorant.setY(ignorant.waka.getY()+(8*16));

    ignorant.tick(app);
    assertEquals(ignorant.waka.getX(), ignorant.targetX);
    assertEquals(ignorant.waka.getY(), ignorant.targetY);

    int originalX = ignorant.x;
    int originalY = ignorant.y;

    ignorant.triggerFrightened(app);
    ignorant.tick(app);

    boolean notEqX = originalX == ignorant.x;
    boolean notEqY = originalY == ignorant.y;

    //i.e. the ghost has moved from the original position
    assertFalse(notEqX && notEqY);
  }
}