package ghost;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {
  private Map map;
  private Entity entity;

  @Test
  public void testConstructor() {
    this.map = new Map("map1.txt");
    this.entity = new Waka(map.getWakaStartCoords()[0]*16, map.getWakaStartCoords()[1]*16, map, 2, 1);
    assertNotNull(this.entity);
    assertEquals(Direction.right, this.entity.getDirection());
    assertEquals(Direction.right, this.entity.getNextDirection());
    assertEquals(this.entity.getX(), this.entity.getOriginalX());
    assertEquals(this.entity.getY(), this.entity.getOriginalY());
    assertEquals(16, this.entity.getX());
    assertEquals(416, this.entity.getY());
    assertEquals(2, this.entity.getSpeed());
    assertNotNull(this.entity.getMap());
  }

  @Test
  public void testGetDir() {
    this.map = new Map("map1.txt");
    this.entity = new Waka(map.getWakaStartCoords()[0], map.getWakaStartCoords()[1], map, 2, 1);
    assertEquals(Direction.left, Entity.getDirection(37));
    assertEquals(Direction.up, Entity.getDirection(38));
    assertEquals(Direction.right, Entity.getDirection(39));
    assertEquals(Direction.down, Entity.getDirection(40));
    assertNull(Entity.getDirection(0));
  }

  @Test
  public void testMoveForward() {
    this.map = new Map("map1.txt");
    this.entity = new Waka(this.map.getWakaStartCoords()[0]*16, this.map.getWakaStartCoords()[1]*16, map, 2, 1);
    assertEquals(Direction.right, this.entity.getDirection());
    assertEquals(16, this.entity.getX());
    assertEquals(416, this.entity.getY());


    this.entity.moveForwards();
    assertEquals(16+entity.getSpeed(), this.entity.getX());
    assertEquals(416, this.entity.getY());

    this.entity.setDirection(Direction.left);
    this.entity.moveForwards();
    assertEquals(16, this.entity.getX());
    assertEquals(416, this.entity.getY());

    this.entity.setDirection(Direction.up);
    this.entity.moveForwards();
    assertEquals(16, this.entity.getX());
    assertEquals(416-this.entity.getSpeed(), this.entity.getY());

    this.entity.setDirection(Direction.down);
    this.entity.moveForwards();
    assertEquals(16, this.entity.getX());
    assertEquals(416, this.entity.getY());

  }

  @Test
  public void testReturnToOrigin() {
    this.map = new Map("map1.txt");
    this.entity = new Waka(this.map.getWakaStartCoords()[0]*16, this.map.getWakaStartCoords()[1]*16, map, 2, 1);
    this.entity.setDirection(Direction.right);
    for (int i = 0; i < 5; i ++) {
      this.entity.moveForwards();
    }

    this.entity.setDirection(Direction.up);
    for (int i = 0; i < 5; i ++) {
      this.entity.moveForwards();
    }

    assertNotEquals(16, this.entity.getX());
    assertNotEquals(416, this.entity.getY());

    this.entity.returnToOrigin();

    assertEquals(16, this.entity.getX());
    assertEquals(416, this.entity.getY());
    
  }

  @Test
  public void testCheckCollision() {
    this.map = new Map("map1.txt");
    this.entity = new Waka(this.map.getWakaStartCoords()[0]*16, this.map.getWakaStartCoords()[1]*16, map, 2, 1);

    assertFalse(this.entity.checkCollision(Direction.right));
    assertTrue(this.entity.checkCollision(Direction.up));
    assertTrue(this.entity.checkCollision(Direction.down));
    assertTrue(this.entity.checkCollision(Direction.left));
  }

  @Test
  public void testChangeDirection() {
    this.map = new Map("map1.txt");
    this.entity = new Waka(this.map.getWakaStartCoords()[0]*16, this.map.getWakaStartCoords()[1]*16, map, 2, 1);

    assertEquals(Direction.right, this.entity.getDirection());
    assertEquals(Direction.right, this.entity.getNextDirection());

    // Note that the map I am testing on is only a 1 wide tunnel, with the entity starting at the far left.

    //won't change bc x % 16 is not 0
    this.entity.moveForwards();
    this.entity.changeDirection(Direction.down);
    assertEquals(Direction.right, this.entity.getDirection());
    assertEquals(Direction.down, this.entity.getNextDirection());


    for (int i = 0; i < 15; i ++) {
      this.entity.moveForwards();
    }

    // Will change as x % 16 is 0
    this.entity.changeDirection(Direction.down);
    assertEquals(Direction.down, this.entity.getDirection());
    assertEquals(Direction.down, this.entity.getNextDirection());

    this.entity.moveForwards();
    this.entity.changeDirection(Direction.left);
    assertEquals(Direction.down, this.entity.getDirection());
    assertEquals(Direction.left, this.entity.getNextDirection());

    for (int i = 0; i < 15; i ++) {
      this.entity.moveForwards();
    }

    this.entity.changeDirection(Direction.left);
    assertEquals(Direction.left, this.entity.getDirection());
    assertEquals(Direction.left, this.entity.getNextDirection());


  }
  
}