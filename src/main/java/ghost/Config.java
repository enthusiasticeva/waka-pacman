package ghost;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The Config class takes a config file and parses it, storing the information in instance variables.
 */
public class Config {
  private String mapPath;
  private Long lives;
  private Long longSpeed;
  private int speed;
  private Long[] modeLengths;
  private Long frightenedLength;

  /**
   * A setter for the map path (Used for testing)
   * @param s
   */
  public void setMapPath(String s) {
    this.mapPath = s;
  }

  /**
   * Getter for the map Path
   * @return String this.mapPath
   */
  public String getMapPath() {
    return this.mapPath;
  }

  /**
   * Getter for the amount of lives
   * @return Long this.lives
   */
  public Long getLives() {
    return this.lives;
  }

  /**
   * Getter for the speed
   * @return int this.speed
   */
  public int getSpeed() {
    return this.speed;
  }

  /**
   * Getter for the scatter mode lengths
   * @return Long[] this.modelengths
   */
  public Long[] getModeLengths() {
    return this.modeLengths;
  }

  /**
   * Getter for the frightened length
   * @return Long this.frightenedLength
   */
  public Long getFrightenedLength() {
    return this.frightenedLength;
  }

  /**
   * Takes a configuration file and separates the fields, extracting the values for the map path, 
   * amount of lives, speed, scatter mmode lenghts and frightened length.
   * These values are then stored as instance variables to be accessed by the Map and App.
   * @param filename the filename of the configuraiton file
   */
  public void parseConfig(String filename) {

    JSONParser parser = new JSONParser();

    try {
      Object object = parser.parse(new FileReader(filename));
      JSONObject j = (JSONObject) object;

      this.mapPath = (String) j.get("map");
      this.lives = (Long) j.get("lives");
      this.longSpeed = (Long) j.get("speed");
      this.speed = this.longSpeed.intValue();

      this.frightenedLength = (Long) j.get("frightenedLength");

      JSONArray modesArr = (JSONArray) j.get("modeLengths");

       modeLengths = new Long[modesArr.size()];

      int i = 0;
      while (i < modesArr.size()) {
        modeLengths[i] = (Long) modesArr.get(i);
        i ++;
      }

    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  
}