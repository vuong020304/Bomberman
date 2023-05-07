package uet.oop.bomberman.entities.bomb;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Spark extends Entity {

  private int maxLength;

  protected SparkSegment[] sparkSegments;

  public Spark(int x, int y, String direction, int maxLength) {
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.maxLength = maxLength;
    createSparkSegments();
  }

  public void createSparkSegments() {
    int lengthSpark = calcLength();
    sparkSegments = new SparkSegment[lengthSpark];
    boolean lastSegment = false;
    for (int i = 0; i < lengthSpark; i++) {

      if (i == maxLength - 1) {
        lastSegment = true;
      }
      sparkSegments[i] = new SparkSegment(this.x, this.y, direction, lastSegment, i + 1);
    }
  }

  public int calcLength() {
    int length = 0;
    int x_ = this.getX();
    int y_ = this.getY();
    while (length < maxLength) {
      switch (direction) {
        case "N":
          {
            y_ -= Sprite.SCALED_SIZE;
            break;
          }
        case "S":
          {
            y_ += Sprite.SCALED_SIZE;
            break;
          }
        case "W":
          {
            x_ -= Sprite.SCALED_SIZE;
            break;
          }
        case "E":
          {
            x_ += Sprite.SCALED_SIZE;
            break;
          }
      }
      Entity entity = Game.getEntityAt(x_, y_);
      if (entity instanceof Bomb) {
        length++;
      }

      if (!entity.collisionHanle(this)) {
        break;
      }
      length++;
    }
    return length;
  }
  
  public SparkSegment getSparkSegmentAt(int x, int y) {
    for (int i = 0; i < sparkSegments.length; i++) {
      Rectangle2D a = new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
      Rectangle2D b =
              new Rectangle2D(
                      sparkSegments[i].getX(),
                      sparkSegments[i].getY(),
                      Sprite.SCALED_SIZE,
                      Sprite.SCALED_SIZE);
      if (a.intersects(b)) {
        return sparkSegments[i];
      }
    }
    return null;
  }

  @Override
  public void update(GraphicsContext gc) {}

  @Override
  public boolean collisionHanle(Entity entity) {
    if (entity instanceof Bomber) {
      ((Bomber) entity).killed();
    }
    if (entity instanceof Enemy) {}

    return true;
  }

  @Override
  public void render(GraphicsContext gc) {
    for (int i = 0; i < sparkSegments.length; i++) {
      sparkSegments[i].render(gc); // phan nghien cuu lai neu sai
    }
  }

  @Override
  public void chooseSprite() {}
}
