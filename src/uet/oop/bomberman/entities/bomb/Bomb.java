package uet.oop.bomberman.entities.bomb;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
  
  private int timeToExplosion = 20; // thoi gian de no
  private boolean canMove = true;
  private double explodeAfter2s = 120;
  private Spark[] sparks;
  private boolean exploded = false;
  private String[] directionArr = {"N", "S", "W", "E"};
  private int lengthBomb;

  public Bomb(int xUnit, int yUnit, Image img, String type,int lengthBomb) {
    super(xUnit, yUnit, img, type);
    this.lengthBomb=lengthBomb;
  }

  public Bomb(int x, int y) {
    super(x, y);
  }
  
  public boolean isExploded() {
    return exploded;
  }

  public void renderSpark(GraphicsContext gc) {
    for (int i = 0; i < sparks.length; i++) {
      sparks[i].render(gc);
    }
  }

  public void setExplodeAfter2s(double explodeAfter2s) {
    this.explodeAfter2s = explodeAfter2s;
  }

  public void updateSparks(GraphicsContext gc) {
    for (int i = 0; i < sparks.length; i++) {
      sparks[i].update(gc);
    }
  }

  public void explode() {
    exploded = true;

    sparks = new Spark[4];
    for (int i = 0; i < 4; i++) {
      sparks[i] = new Spark(this.x, this.y, directionArr[i], this.lengthBomb);
    }
  }

  /** Ham nay dung de xu ly va cham voi bomb spark, enemy */
  public void checkState() {
    Entity e = Game.getEntityAt(this.getX(), this.getY());
    this.collisionHanle(e);
  }
  
  public boolean allowGoThrough() {
    Rectangle2D bomber =
            new Rectangle2D(
                    Game.bomberman.getX(), Game.bomberman.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    Rectangle2D bomb = new Rectangle2D(this.x, this.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    
    if (canMove && !bomber.intersects(bomb)) {
      canMove = false;
    }
    return canMove;
  }
  
  public SparkSegment sparkAt(int x, int y) {
    if (!exploded) {
      return null;
    }
    
    for (int i = 0; i < sparks.length; i++) {
      if (sparks[i] == null) {
        return null;
      }
      SparkSegment sparkSegment = sparks[i].getSparkSegmentAt(x, y);
      if (sparkSegment != null) {
        return sparkSegment;
      }
    }
    return null;
  }

  @Override
  public void update(GraphicsContext gc) {
    if (explodeAfter2s > 0) {
      Game.elementStorage[(int) (this.y / Sprite.SCALED_SIZE)][
              (int) (this.x / Sprite.SCALED_SIZE)] =
          this;
      explodeAfter2s--;

    } else {
      if (!exploded) {
        explode();
      } else {
        updateSparks(gc);
      }
      if (timeToExplosion > 0) {
        timeToExplosion--;
      } else {
        Game.getSoundOfGame().bombBang();
        sparks = new Spark[0];
        Game.elementStorage[(int) (this.y / Sprite.SCALED_SIZE)][
                (int) (this.x / Sprite.SCALED_SIZE)] =
            new Grass(
                this.x / Sprite.SCALED_SIZE,
                this.y / Sprite.SCALED_SIZE,
                Sprite.grass.getFxImage(),
                null);
        Bomber.removeBomb(this);
      }
    }
    allowGoThrough();
    animate();
    checkState();
  }

  @Override
  public boolean collisionHanle(Entity e) {
    if (e instanceof Bomber && exploded) {
      ((Bomber) e).setDieState();
    }
    if (e instanceof Spark || e instanceof SparkSegment) {
      explodeAfter2s = 0;
    }
    return canMove;
  }

  @Override
  public void render(GraphicsContext gc) {
    if (exploded) {
      this.sprite = Sprite.bomb_exploded2;
      this.img = this.sprite.getFxImage();
      renderSpark(gc);
    } else {
      sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb, animate, 60);
      this.img = this.sprite.getFxImage();
    }
    super.render(gc);
  }

  @Override
  public void chooseSprite() {}
}
