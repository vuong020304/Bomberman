package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Portal extends Entity {
  public Portal(int xUnit, int yUnit, Image img, String type) {
    super(xUnit, yUnit, img, type);
  }

  @Override
  public void update(GraphicsContext gc) {}

  @Override
  public boolean collisionHanle(Entity e) {
    if (e instanceof Bomber && Game.dectectNoEnemy()) {
      Game.getSoundOfGame().itemSound();
      Game.scoreOfPlayer += 500 * Game.getCurrentLevel();
      if (Game.getCurrentLevel() == Game.getListMap().size()) {
        try {
          File myObj = new File("res/highscore/HighestScore");
          Scanner myReader = new Scanner(myObj);
          while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (Game.scoreOfPlayer > Integer.parseInt(data)) {
              FileWriter myWriter = new FileWriter("res/highscore/HighestScore");
              System.out.println("Your Score: " + Game.scoreOfPlayer);
              System.out.println("High Score: " + Game.scoreOfPlayer);
              myWriter.write(Integer.toString(Game.scoreOfPlayer));
              myWriter.close();
            } else {
              System.out.println("Your Score: " + Game.scoreOfPlayer);
              System.out.println("High Score: " + data);
            }
          }
          myReader.close();
        } catch (FileNotFoundException error) {
          System.out.println("An error occurred.");
          error.printStackTrace();
        } catch (IOException exception) {
          exception.printStackTrace();
        }
        Game.getSoundOfGame().winGame();
        Game.getSoundOfGame().pausePlayGame();
      }
      Game.state = 2;
    }
    return true;
  }

  @Override
  public void chooseSprite() {}
}
