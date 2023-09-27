package ghost;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.*;

public class GhostTest {
  @Test
  public void testConstructor() {
    Map map = new Map("map.txt");
    Waka waka = new Waka(0, 0, map, 2, 2);
    // As Ghost is abstract, the easiest way to test it is with a chaser, but declared as a ghost.
    Ghost ghost = new Chaser(0, 0, map, 2, waka);
    
    assertNotNull(ghost);
    assertNotNull(ghost.getWaka());

    assertFalse(ghost.frightenedMode);
    assertFalse(ghost.scatterMode);
    assertFalse(ghost.debugMode);

    assertTrue(ghost.alive);

    assertNotNull(ghost.type);

    assertEquals(-1, ghost.getFrameFrightenedStart());
    assertEquals(-1, ghost.getFrameScatterStart());

  }

  @Test
  public void testToggleScatter() {
    App app = new App("map.txt");

    for (Ghost ghost: app.getGhosts()) {
      assertFalse(ghost.scatterMode);
      assertEquals(-1, ghost.getFrameScatterStart());
    }
    
    app.frameCount = 30;
    Ghost.toggleScatterMode(app.getGhosts(),app);

    for (Ghost ghost: app.getGhosts()) {
      assertTrue(ghost.scatterMode);
      assertEquals(30, ghost.getFrameScatterStart());
    }
  }

  @Test
  public void testTriggerFrightened() {
    App app = new App("map.txt");

    for (Ghost ghost: app.getGhosts()) {
      assertFalse(ghost.frightenedMode);
      assertEquals(-1, ghost.getFrameFrightenedStart());
    }
    
    app.frameCount = 30;

    for (Ghost ghost: app.getGhosts()) {
      ghost.triggerFrightened(app);

      assertTrue(ghost.frightenedMode);
      assertEquals(30, ghost.getFrameFrightenedStart());
    }
  }

  @Test
  public void testEndFrightened() {
    App app = new App("map.txt");

    app.frameCount = 30;
    Ghost.triggerFrightenedMode(app.getGhosts(), app);

    for (Ghost ghost: app.getGhosts()) {
      assertTrue(ghost.frightenedMode);
      assertEquals(30, ghost.getFrameFrightenedStart());
    }
    
    Ghost.endFrightenedMode(app.getGhosts(), app);

    for (Ghost ghost: app.getGhosts()) {
      assertFalse(ghost.frightenedMode);
      assertEquals(-1, ghost.getFrameFrightenedStart());
    }
  }

  @Test
  public void testCheckFrightenedEnd() {
    App app = new App("map.txt");

    app.frameCount = 0;
    // checking the case where nothing needs to happen
    for (Ghost ghost: app.getGhosts()) {
      assertFalse(ghost.frightenedMode);
      assertEquals(-1, ghost.getFrameFrightenedStart());
      ghost.checkFrightenedEnd(app);
      assertFalse(ghost.frightenedMode);
      assertEquals(-1, ghost.getFrameFrightenedStart());
    }

    assertEquals(5, app.getConfig().getFrightenedLength());

    Ghost.triggerFrightenedMode(app.getGhosts(), app);

    app.frameCount = 5 * 60;

    for (Ghost ghost: app.getGhosts()) {
      ghost.checkFrightenedEnd(app);
      assertTrue(ghost.frightenedMode);
      assertEquals(0, ghost.getFrameFrightenedStart());
    }

    app.frameCount ++ ;

    for (Ghost ghost: app.getGhosts()) {
      ghost.checkFrightenedEnd(app);
      assertFalse(ghost.frightenedMode);
      assertEquals(-1, ghost.getFrameFrightenedStart());
    }

  }

  @Test
  public void testChooseDirection() {
    App app = new App("map3.txt");

    Ghost ghost = app.getGhosts().get(0);

    ghost.atIntersection();
    assertTrue(ghost.options.get("right"));
    assertTrue(ghost.options.get("up"));
    assertTrue(ghost.options.get("down"));
    // Will be false as ghost cannot turn back on itself
    assertFalse(ghost.options.get("left"));

    int dir = ghost.chooseDirection(app.getWaka().getX(), app.getWaka().getY());
    assertEquals(40, dir);

    dir = ghost.chooseDirection(app.width/2, 0);
    assertEquals(38, dir);

    // This prevents the ghost from choosing down
    ghost.setDirection(Direction.up);

    dir = ghost.chooseDirection(600, 600);
    assertEquals(39, dir);

    dir = ghost.chooseDirection(0, 300);
    assertEquals(37, dir);

    // Checks the sitution where the ghost is not at an intersection.
    app = new App("map4.txt");
    ghost = app.getGhosts().get(0);

    dir = ghost.chooseDirection(app.getWaka().getX(), app.getWaka().getY());
    assertEquals(-1, dir);
  }

  @Test
  public void testDraw() {
    App app = new App("map.txt");
    PApplet.runSketch(new String[] {"testing"}, app);
    app.setup();

    app.getGhosts().get(0).draw(app);

    app.getGhosts().get(0).triggerFrightened(app);

    app.getGhosts().get(0).draw(app);

    app.getGhosts().get(0).debugMode = true;

    app.getGhosts().get(0).draw(app);
  }

  @Test
  public void testCheckScatterMode() {
    App app = new App("map.txt");
    app.frameCount = 0;

    
    Ghost ghost = app.getGhosts().get(0);

    app.frameCount = 7*60-1;

    ghost.checkScatterMode(app);

    assertTrue(ghost.scatterMode);
  }
}