package uet.oop.bomberman.entities.enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Objects;

public class Doll extends Enemy {

  public Doll(int xUnit, int yUnit, Image img, String type) {
    super(xUnit, yUnit, img, type);
  }

  public Doll(Entity ob) {
    super(ob);
  }
  
  public void goLeft(Entity doll) {
    doll.setX(doll.getX() - 1);
  }
  
  public void goRight(Entity doll) {
    doll.setX(doll.getX() + 1);
  }
  
  public void goUp(Entity doll) {
    doll.setY(doll.getY() - 1);
  }
  
  public void goDown(Entity doll) {
    doll.setY(doll.getY() + 1);
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
      handleAfterDie();
    }
    setStateAfterCollision();
  }

  @Override
  public void chooseSprite() {

    switch (direction) {
      case "N":
        this.setImg(
            Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_right3, animate, 60).getFxImage());
        break;
      case "S":
        this.setImg(
            Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_left3, animate, 60).getFxImage());
        break;
      case "E":
        this.setImg(
            Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, animate, 60).getFxImage());
        break;

      case "W":
        this.setImg(
            Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, animate, 60).getFxImage());
        break;
    }
  }

  @Override
  public void render(GraphicsContext gc) {
    if (!isAlive) {
      if (timeToDie > 0) {
        sprite = Sprite.doll_dead;
        this.img = sprite.getFxImage();
        animate = 0;
        enemySkill();
      } else {
        this.sprite =
                Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 1);
        this.img = this.sprite.getFxImage();
        Bomber.numberOfBombs=Bomber.saveNumOfBombs;
      }
    }

    super.render(gc);
  }

  @Override
  public void directMoving() {}

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
          this.x+=1;
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
          }else{
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
          this.y += 1;
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
    if(timeToDie==2){
      Bomb dollExplore = new Bomb(this.x/48,this.y/48,Sprite.bomb_exploded2.getFxImage(),"static",32);

      dollExplore.setExplodeAfter2s(0);
      Game.getBombs().add(dollExplore);
    }
  }
}
