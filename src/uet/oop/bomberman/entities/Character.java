package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Character extends Entity {
  
  protected int timeAfter = 40;
  protected Boolean isMoving = false;
  protected boolean isAlive = true;

  public Character(int xUnit, int yUnit, Image img, String type) {
    super(xUnit, yUnit, img, type);
  }

  public Character(Entity ob) {
    super(ob);
  }

  public Character(int x, int y) {
    super(x, y);
  }

  public abstract void killed();

  public abstract void afterKilled();

  @Override
  public void update(GraphicsContext gc) {}

  @Override
  public void chooseSprite() {}

  public Boolean getMovingStage() {
    return isMoving;
  }

  public void setMovingState(Boolean moving) {
    isMoving = moving;
  }

  public Boolean getMoving() {
    return isMoving;
  }

  public void setMoving(Boolean moving) {
    isMoving = moving;
  }

  public void setDieState() {
    this.isAlive = false;
  }
}
