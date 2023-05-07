package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.bomb.Spark;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
  protected boolean existing = true;
  protected int timeToDisappear = 20;

  public Brick(int xUnit, int yUnit, Image img, String type) {
    super(xUnit, yUnit, img, type);
  }

  @Override
  public void update(GraphicsContext gc) {
    if (!existing) {
      if (animate < MAX_ANIMATE) {
        animate++;
      } else animate = 0;
      if (timeToDisappear > 0) {
        timeToDisappear--;
      } else {
        delete = true;
      }
    }
  }

  @Override
  public boolean collisionHanle(Entity entity) {
    if (entity instanceof Spark) {
      removeBrick();
    }
    return false;
  }

  protected Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2) {
    int calc = animate % 30;

    if (calc < 10) {
      return normal;
    }

    if (calc < 20) {
      return x1;
    }
    return x2;
  }

  public void removeBrick() {
    this.existing = false;
  }

  @Override
  public void render(GraphicsContext gc) {
    if (!existing) {
      if (timeToDisappear > 0) {
        this.setImg(
            Sprite.movingSprite(
                    Sprite.brick_exploded,
                    Sprite.brick_exploded1,
                    Sprite.brick_exploded2,
                    animate,
                    60)
                .getFxImage());

        super.render(gc);
      } else {
        this.setImg(Sprite.grass.getFxImage());

        super.render(gc);
      }
    } else super.render(gc);
  }

  public Grass convertBrickToGrass(Brick brick) {
    Grass grass = new Grass();
    grass.setX(brick.getX());
    grass.setY(brick.getY());
    grass.setImg(brick.getImg());
    grass.setType(brick.getType());
    return grass;
  }

  @Override
  public void chooseSprite() {}
}
