package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wall extends Entity {

  public Wall(int xUnit, int yUnit, Image img, String type) {
    super(xUnit, yUnit, img, type);
  }

  @Override
  public void update(GraphicsContext gc) {}
  
  @Override
  public boolean collisionHanle(Entity e) {
    return false;
  }
  
  @Override
  public void chooseSprite() {

  }
}
