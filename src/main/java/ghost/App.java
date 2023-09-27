package ghost;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PFont;

/**
 * The App controls the flow of the game, loading images, drawing them on the screen, 
 * and storing all the game objects to be called at the correct times
 */
public class App extends PApplet {

    private static final int WIDTH = 448;
    private static final int HEIGHT = 576;
    private Map map;
    private Config config;
    private Waka waka;
    private List<Ghost> ghosts;
    private Chaser chaserForWhim;
    private PFont myFont;
    private int endScreenFrameStart;

    public App() {
        //Set up your objects
        config = new Config();
        config.parseConfig("config.json");
        this.ghosts = new ArrayList<Ghost>();
        
        this.restart();
    }

    // Used for testing.
    public App(String filename) {
      this.config = new Config();
      config.parseConfig("config.json");

      this.config.setMapPath(filename);

      this.ghosts = new ArrayList<Ghost>();
      
      this.restart();
    }

    /**
    * Loads all images from filenames to PImages, and assigns them as sprites to the appropriate objects.
    * <em>"The setup() function is called once when the program starts. It's used to define initial enviroment 
    * properties such as screen size and background color and to load media such as images and fonts as the 
    * program starts."</em>
    * - excerpt taken from the PApplet Documentation.
    */
    public void setup() {
      frameRate(60);

      // Load images

      for (cellType type: cellType.values()) {
        if (type.getImagePath() == null) {
          type.setSprite(null);
        } else {
          type.setSprite(this.loadImage(type.getImagePath()));
        }
      }

      for (Direction dir: Direction.values()) {
        dir.setSprite(this.loadImage(dir.getWakaImagePath()));
      }

      for (ghostType g: ghostType.values()) {
        g.setSprite(this.loadImage(g.getImagePath()));
      }
      Ghost.setFrightenedSprite(this.loadImage("frightened.png"));

      Waka.setClosedSprite(this.loadImage("playerClosed.png"));
      this.waka.setSprite(this.loadImage("playerClosed.png"));

      myFont = this.createFont("PressStart2P-Regular.ttf", 16);
    }

    /**
     * Defines the dimensions of the screen
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * A function called every time a key is pressed. 
     * The key code of the key pressed is stored in this.keyCode, and can then be accessed
     */
    public void keyPressed() {
      // 32 is spacebar
      if (this.keyCode == 32) {
        Ghost.toggleDebugMode(this);
      }
      // 37,38,39,40 are the arrowkeys
      if (this.keyCode < 37 || this.keyCode > 40) {
        return;
      }
      
      this.waka.setNextDirection(Waka.getDirection(keyCode));
    }

    /**
     * Calls tick and draw for each element to draw on the screen.
     */
    public void draw() { 
        if (endScreenFrameStart == -1) {
          background(0, 0, 0);
          if (this.waka.getLives() > -1) {
            if (this.map.getFruitCurrent() < this.map.getFruitTotal()){  
              Cell.drawAll(this, this.map.getCellMap());
              this.map.displayLives(this);
              this.waka.tick(this);
              this.waka.draw(this);
              for (Ghost g: ghosts) {
                g.tick(this);
                g.draw(this);
              }
            } else {
              textSize(16);
              textFont(this.myFont);
              textAlign(App.CENTER, App.CENTER);
              text("YOU WIN",this.width/2,this.height/2);
              this.endScreenFrameStart = this.frameCount;
            }
          } else {
            textSize(16);
            textAlign(App.CENTER, App.CENTER);
            textFont(this.myFont);
            text("GAME OVER",this.width/2,this.height/2);
            this.endScreenFrameStart = this.frameCount;
          }
        } else {
          if (this.frameCount == this.endScreenFrameStart + 10 * 60) {
            this.restart();
          }
        }

    }

    /**
     * Instantiates all necessary components of the game (waka, map, ghosts etc.) 
     * and sets all relevant variables and flags to their starting values.
     */
    public void restart() {
      this.endScreenFrameStart = -1;
      this.map = new Map(config.getMapPath());
      int x = this.map.getWakaStartCoords()[0]*16;
      int y = this.map.getWakaStartCoords()[1]*16;
      this.waka = new Waka(x, y, this.map, config.getSpeed(), config.getLives().intValue());
      this.waka.setSprite(Waka.getClosedSprite());
      this.waka.setMouthOpen(false);

      this.ghosts = new ArrayList<Ghost>();
      for (int[] g: this.map.getGhostTypeStartCoords(ghostType.Ambusher)) {
        this.ghosts.add(new Ambusher(g[0]*16, g[1]*16, this.map, this.config.getSpeed(), this.waka));
      }
      for (int[] g: this.map.getGhostTypeStartCoords(ghostType.Chaser)) {
        this.chaserForWhim = new Chaser(g[0]*16, g[1]*16, this.map, this.config.getSpeed(), this.waka);
        this.ghosts.add(this.chaserForWhim);
      }
      for (int[] g: this.map.getGhostTypeStartCoords(ghostType.Ignorant)) {
        this.ghosts.add(new Ignorant(g[0]*16, g[1]*16, this.map, this.config.getSpeed(), this.waka));
      }
      for (int[] g: this.map.getGhostTypeStartCoords(ghostType.Whim)) {
        this.ghosts.add(new Whim(g[0]*16, g[1]*16, this.map, this.config.getSpeed(), this.waka, this.chaserForWhim));
      }

      for (Ghost ghost: this.ghosts) {
        ghost.setScatterLengths(this.config.getModeLengths());
      }
    }

    public static void main(String[] args) {
      PApplet.main("ghost.App");
    }

    /**
     * Getter for the App's associated config object
     * @return this.config
     */
    public Config getConfig() {
      return this.config;
    }

    /**
     * Getter for the App's associated waka object
     * @return this.waka
     */
    public Waka getWaka() {
      return this.waka;
    }
    
    /**
     * Getter for the App's associated ghost objects
     * @return this.ghosts
     */
    public List<Ghost> getGhosts() {
      return this.ghosts;
    }

    /**
     * Getter for the frame on which the end screen of the game began.
     * If the game is not currently in the end screen, this value will be -1.
     * @return this.endScreenFrameStart
     */
    public int getEndScreenStartFrame() {
      return this.endScreenFrameStart;
    }

    /**
     * Getter for the font used on the end screens
     * @return this.myFont
     */
    public PFont getFont() {
      return this.myFont;
    }

    /**
     * Getter for the App's associated Map object
     * @return this.map
     */
    public Map getMap() {
      return this.map;
    }
}
