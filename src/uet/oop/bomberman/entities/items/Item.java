package uet.oop.bomberman.entities.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class Item extends Entity {

  protected boolean active = false;

  public Item(int xUnit, int yUnit, Image img, String type) {
    super(xUnit, yUnit, img, type);
  }

  @Override
  public void update(GraphicsContext gc) {}

  @Override
  public boolean collisionHanle(Entity e) {
    return false;
  }

  @Override
  public void chooseSprite() {}
}
