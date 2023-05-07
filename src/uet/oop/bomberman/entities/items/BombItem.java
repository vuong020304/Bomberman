package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;

public class BombItem extends Item {
  public BombItem(int xUnit, int yUnit, Image img, String type) {
    super(xUnit, yUnit, img, type);
  }

  @Override
  public boolean collisionHanle(Entity e) {
    if (e instanceof Bomber) {
      Game.getSoundOfGame().itemSound();
      Bomber.numberOfBombs++;
      Bomber.saveNumOfBombs++;
      delete = true;
    }
    return false;
  }
}
