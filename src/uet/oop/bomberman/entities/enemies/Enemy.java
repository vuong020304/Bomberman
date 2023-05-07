package uet.oop.bomberman.entities.enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Character;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Spark;
import uet.oop.bomberman.entities.bomb.SparkSegment;

import java.util.Random;

public abstract class Enemy extends Character {
  public boolean removed = false;
  protected int timeToDie = 40;
  protected int timeToRemove = 60;
  private Boolean isReplication = false;
  
  public Boolean getReplication() {
    return isReplication;
  }
  
  public void setReplication(Boolean replication) {
    isReplication = replication;
  }
  
  public Enemy(int xUnit, int yUnit, Image img, String type) {
    super(xUnit, yUnit, img, type);
  }
  
  public Enemy(Entity ob) {
    super(ob);
  }
  
  public static int getRandomNumberInRange(int min, int max) {
    
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    
    Random r = new Random();
    return r.nextInt((max - min) + 1) + min;
  }
  
  @Override
  public void killed() {
  }
  
  @Override
  public void afterKilled() {
  }
  
  public void setStateAfterCollision() {
    Entity u = Game.getEntityAt(this.x, this.y);
    if (u != null) {
      this.collisionHanle(u);
    }
  }
  
  @Override
  public abstract void update(GraphicsContext gc);
  
  @Override
  public boolean collisionHanle(Entity e) {
    if (e instanceof Bomber && this.isAlive) {
      ((Bomber) e).setDieState();
    }
    if (e instanceof Bomb && ((Bomb) e).isExploded()) {
      System.out.println("QUAI CHET");
      this.setDieState();
      return false;
    }
    
    if (e instanceof Spark || e instanceof SparkSegment) {
      this.setDieState();
    }
    
    return true;
  }
  
  @Override
  public abstract void chooseSprite();
  
  public abstract void directMoving();
  
  public abstract void checkCollision();
  
  protected void handleAfterDie() {
    if (timeToDie > 0) {
      timeToDie--;
      if (timeToDie == 1) {
        Game.scoreOfPlayer += 100 * Game.getCurrentLevel();
        Game.getSoundOfGame().enemyDie();
        
      }
    } else {
      if (timeToDie == 0) {
      }
      if (timeToRemove > 0) {
        timeToRemove--;
      } else {
        removed = true;
      }
    }
  }
  
  public abstract void enemySkill();
}
