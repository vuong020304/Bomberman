package uet.oop.bomberman.entities.enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Objects;

public class Kondoria extends Enemy {
  
  public Kondoria(int xUnit, int yUnit, Image img, String type) {
    super(xUnit, yUnit, img, type);
  }
  
  public Kondoria(Entity ob) {
    super(ob);
  }
  
  public void goLeft(Entity kondoria) {
    kondoria.setX(kondoria.getX() - 2);
  }
  
  public void goRight(Entity kondoria) {
    kondoria.setX(kondoria.getX() + 2);
  }
  
  public void goUp(Entity kondoria) {
    kondoria.setY(kondoria.getY() - 2);
  }
  
  public void goDown(Entity kondoria) {
    kondoria.setY(kondoria.getY() + 2);
  }
  
  @Override
  public void update(GraphicsContext gc) {
    if (this.isAlive) {
      checkCollision();
      chooseSprite();
      if (Game.bomberman.isAlive()) {
        if (Objects.equals(direction, "E")) {
          goRight(this);
        }
        if (Objects.equals(direction, "W")) {
          goLeft(this);
        }
        if (Objects.equals(direction, "N")) {
          goUp(this);
        }
        if (Objects.equals(direction, "S")) {
          goDown(this);
        }
      }
    } else {
//quen r nghi da
      handleAfterDie();
      if (!this.getReplication() && timeToRemove == 0) {
        Enemy subLeft = new Oneal(this.x / 48, this.y / 48, Sprite.oneal_left1.getFxImage(), "dynamic");
        Enemy subRight = new Oneal(this.x / 48, this.y / 48, Sprite.oneal_right1.getFxImage(), "dynamic");
        subLeft.setReplication(true);
        subRight.setReplication(true);
        if (this.direction == "E" || this.direction == "W") {
          subLeft.setDirection("W");
          subRight.setDirection("E");
        }else{
          subLeft.setDirection("N");
          subRight.setDirection("S");
        }
        Game.getEnemies().add(subLeft);
        Game.getEnemies().add(subRight);
      }
    }
    setStateAfterCollision();
  }
  
  @Override
  public void chooseSprite() {
    
    switch (direction) {
      case "N":
        this.setImg(
                Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_right3, animate, 60)
                        .getFxImage());
        break;
      case "S":
        this.setImg(
                Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_left3, animate, 60)
                        .getFxImage());
        break;
      case "E":
        this.setImg(
                Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, animate, 60)
                        .getFxImage());
        break;
      
      case "W":
        this.setImg(
                Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, animate, 60)
                        .getFxImage());
        break;
    }
  }
  
  @Override
  public void render(GraphicsContext gc) {
    if (!isAlive) {
      if (timeToDie > 0) {
        sprite = Sprite.kondoria_dead;
        this.img = sprite.getFxImage();
        animate = 0;
      } else {
        this.sprite =
                Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead1, Sprite.mob_dead2, animate, 60);
        this.img = this.sprite.getFxImage();
      }
    }
    super.render(gc);
  }
  
  @Override
  public void directMoving() {
  }
  
  @Override
  public void checkCollision() {
    if (Objects.equals(direction, "E")) {
      for (int i = 0; i < Game.getBrickAndWall().size(); i++) {
        if (Game.getBrickAndWall().get(i).getX() / 48 == this.getX() / 48 + 1
                && Game.getBrickAndWall().get(i).getY() / 48 == this.getY() / 48) {
          Boolean turnNorth = false;
          Boolean turnSouth = false;
          for (int j = 0; j < Game.getStillObjects().size(); j++) {
            if (Game.getStillObjects().get(j) instanceof Grass
                    && Game.getStillObjects().get(j).getX() / 48 == this.x / 48
                    && Game.getStillObjects().get(j).getY() / 48 == this.y / 48 - 1) {
              turnNorth = true;
              break;
            }
          }
          for (int j = 0; j < Game.getStillObjects().size(); j++) {
            if (Game.getStillObjects().get(j) instanceof Grass
                    && Game.getStillObjects().get(j).getX() / 48 == this.x / 48
                    && Game.getStillObjects().get(j).getY() / 48 == this.y / 48 + 1) {
              turnSouth = true;
              break;
            }
          }
          if (turnNorth) {
            direction = "N";
          } else if (turnSouth) {
            direction = "S";
          } else {
            direction = "W";
          }
          break;
        }
      }
      for (int i = 0; i < Game.getBombs().size(); i++) {
        if (Game.getBombs().get(i).getX() / 48 == this.getX() / 48 + 1
                && Game.getBombs().get(i).getY() / 48 == this.getY() / 48) {
          direction = "W";
          break;
        }
      }
    }
    if (Objects.equals(direction, "W")) {
      for (int i = 0; i < Game.getBrickAndWall().size(); i++) {
        if (Game.getBrickAndWall().get(i).getX() / 48 == this.getX() / 48
                && Game.getBrickAndWall().get(i).getY() / 48 == this.getY() / 48) {
          Boolean turnNorth = false;
          Boolean turnSouth = false;
          this.x += 2;
          for (int j = 0; j < Game.getStillObjects().size(); j++) {
            if (Game.getStillObjects().get(j) instanceof Grass
                    && Game.getStillObjects().get(j).getX() / 48 == this.x / 48
                    && Game.getStillObjects().get(j).getY() / 48 == this.y / 48 - 1) {
              turnNorth = true;
              break;
            }
          }
          for (int j = 0; j < Game.getStillObjects().size(); j++) {
            if (Game.getStillObjects().get(j) instanceof Grass
                    && Game.getStillObjects().get(j).getX() / 48 == this.x / 48
                    && Game.getStillObjects().get(j).getY() / 48 == this.y / 48 + 1) {
              turnSouth = true;
              break;
            }
          }
          if (turnNorth) {
            direction = "N";
          } else if (turnSouth) {
            direction = "S";
          } else {
            direction = "E";
          }
          break;
        }
      }
      for (int i = 0; i < Game.getBombs().size(); i++) {
        if (Game.getBombs().get(i).getX() / 48 == this.getX() / 48
                && Game.getBombs().get(i).getY() / 48 == this.getY() / 48) {
          direction = "E";
          break;
        }
      }
    }
    if (Objects.equals(direction, "S")) {
      for (int i = 0; i < Game.getBrickAndWall().size(); i++) {
        if (Game.getBrickAndWall().get(i).getX() / 48 == this.getX() / 48
                && Game.getBrickAndWall().get(i).getY() / 48 == this.getY() / 48 + 1) {
          Boolean turnEast = false;
          Boolean turnWest = false;
          for (int j = 0; j < Game.getStillObjects().size(); j++) {
            if (Game.getStillObjects().get(j) instanceof Grass
                    && Game.getStillObjects().get(j).getX() / 48 == this.x / 48 + 1
                    && Game.getStillObjects().get(j).getY() / 48 == this.y / 48) {
              turnEast = true;
              break;
            }
          }
          for (int j = 0; j < Game.getStillObjects().size(); j++) {
            if (Game.getStillObjects().get(j) instanceof Grass
                    && Game.getStillObjects().get(j).getX() / 48 == this.x / 48 - 1
                    && Game.getStillObjects().get(j).getY() / 48 == this.y / 48) {
              turnWest = true;
              break;
            }
          }
          if (turnEast) {
            direction = "E";
          } else if (turnWest) {
            direction = "W";
          } else {
            direction = "N";
          }
          break;
        }
      }
      for (int i = 0; i < Game.getBombs().size(); i++) {
        if (Game.getBombs().get(i).getX() / 48 == this.getX() / 48
                && Game.getBombs().get(i).getY() / 48 == this.getY() / 48 + 1) {
          direction = "N";
          break;
        }
      }
    }
    if (Objects.equals(direction, "N")) {
      for (int i = 0; i < Game.getBrickAndWall().size(); i++) {
        if (Game.getBrickAndWall().get(i).getX() / 48 == this.getX() / 48
                && Game.getBrickAndWall().get(i).getY() / 48 == this.getY() / 48) {
          Boolean turnEast = false;
          Boolean turnWest = false;
          this.y += 2;
          for (int j = 0; j < Game.getStillObjects().size(); j++) {
            if (Game.getStillObjects().get(j) instanceof Grass
                    && Game.getStillObjects().get(j).getX() / 48 == this.x / 48 + 1
                    && Game.getStillObjects().get(j).getY() / 48 == this.y / 48) {
              turnEast = true;
              break;
            }
          }
          for (int j = 0; j < Game.getStillObjects().size(); j++) {
            if (Game.getStillObjects().get(j) instanceof Grass
                    && Game.getStillObjects().get(j).getX() / 48 == this.x / 48 - 1
                    && Game.getStillObjects().get(j).getY() / 48 == this.y / 48) {
              turnWest = true;
              break;
            }
          }
          if (turnEast) {
            direction = "E";
          } else if (turnWest) {
            direction = "W";
          } else {
            direction = "S";
          }
          break;
        }
      }
      
      for (int i = 0; i < Game.getBombs().size(); i++) {
        if (Game.getBombs().get(i).getX() / 48 == this.getX() / 48
                && Game.getBombs().get(i).getY() / 48 == this.getY() / 48) {
          direction = "S";
          break;
        }
      }
    }
  }
  
  @Override
  public void enemySkill() {
  
  }
}
