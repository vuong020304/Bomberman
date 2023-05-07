package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;

public class SpeedItem extends Item {

  public SpeedItem(int xUnit, int yUnit, Image img, String type) {
    super(xUnit, yUnit, img, type);
  }

  @Override
  public boolean collisionHanle(Entity e) {
    if (e instanceof Bomber) {
      Game.getSoundOfGame().itemSound();
      Game.setSPEED(Game.getSPEED() + 1);
      delete = true;
    }
    return false;
  }
}
