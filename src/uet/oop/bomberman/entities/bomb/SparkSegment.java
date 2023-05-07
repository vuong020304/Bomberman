package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class SparkSegment extends Entity {

  private boolean lastSpark;

  public SparkSegment(int x, int y, String direction, boolean last, int index) {

    this.x = x;
    this.y = y;
    this.lastSpark = last;
    switch (direction) {
      case "N":
        this.x = x;
        this.y = y - index * Sprite.SCALED_SIZE;
        if (last) {
          this.sprite = Sprite.explosion_vertical_top_last2;
        } else {
          this.sprite = Sprite.explosion_vertical2;
        }
        break;
      case "S":
        this.x = x;
        this.y = y + index * Sprite.SCALED_SIZE;
        if (last) {
          this.sprite = Sprite.explosion_vertical_down_last2;
        } else {
          this.sprite = Sprite.explosion_vertical2;
        }
        break;
      case "W":
        this.x = x - index * Sprite.SCALED_SIZE;
        this.y = y;
        if (last) {
          this.sprite = Sprite.explosion_horizontal_left_last2;
        } else {
          this.sprite = Sprite.explosion_horizontal2;
        }
        break;
      case "E":
        this.x = x + index * Sprite.SCALED_SIZE;
        this.y = y;
        if (last) {
          this.sprite = Sprite.explosion_horizontal_right_last2;
        } else {
          this.sprite = Sprite.explosion_horizontal2;
        }
        break;
    }
    this.img = this.sprite.getFxImage();
  }

  @Override
  public void update(GraphicsContext gc) {}

  @Override
  public boolean collisionHanle(Entity entity) {
    if (entity instanceof Bomber) {
      ((Bomber) entity).setDieState();
    }
    if (entity instanceof Enemy) {}
    return true;
  }

  @Override
  public void chooseSprite() {}
}
