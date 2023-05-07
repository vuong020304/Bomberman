package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Spark;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bomber extends Character {
    protected static List<Bomb> bombs = new ArrayList<>();
    public static int numberOfBombs = 1;
    public static int saveNumOfBombs = 1;
    public static int momentScore = 1;

    public Bomber(int xUnit, int yUnit, Image img, String type) {
        super(xUnit, yUnit, img, type);
    }

    public Bomber(int x, int y) {
        super(x, y);
    }

    public static int getNumberOfBombs() {
        return numberOfBombs;
    }

    @Override
    public void killed() {
        if (!isAlive) {
            if (momentScore-- == 1) {
                System.out.println("TIME: " + Game.countTime);
                try {
                    File myObj = new File("res/highscore/HighestScore");
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        if (Game.scoreOfPlayer > Integer.parseInt(data)) {
                            FileWriter myWriter = new FileWriter("res/highscore/HighestScore");
                            System.out.println("Your Score: " + Game.scoreOfPlayer);
                            System.out.println("highscore: " + Game.scoreOfPlayer);
                            myWriter.write(Integer.toString(Game.scoreOfPlayer));
                            myWriter.close();
                        } else {
                            System.out.println("Your Score: " + Game.scoreOfPlayer);
                            System.out.println("highscore: " + data);
                        }

                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                Game.getSoundOfGame().bombermanDie();
                Game.getSoundOfGame().loseGame();
                Game.getSoundOfGame().pausePlayGame();
            }
            this.setImg(
                    Sprite.movingSprite(
                                    Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, 60)
                            .getFxImage());
            Game.state = 3;
        } else {
            return;
        }
    }

    @Override
    public void afterKilled() {
        if (timeAfter > 0) {
            timeAfter--;
        } else {
        }
    }

    @Override
    public void update(GraphicsContext gc) {
        chooseSprite();
        killed();
        checkState();
    }

    /**
     * Ham nay dung de xu ly va cham voi bomb spark, enemy
     */
    public void checkState() {
        Entity e = Game.getEntityAt(this.getX(), this.getY());
        e.collisionHanle(this);
    }

    @Override
    public boolean collisionHanle(Entity entity) {
        if (entity instanceof Spark) {
            isAlive = false;
        }

        if (entity instanceof Enemy) {
            isAlive = false;
        }
        return true;
    }

    public void putBomb() {
        if (!(Game.getEntityAt(this.x, this.y) instanceof Bomb)) {
            if (numberOfBombs > 0) {
                Bomb bomb = new Bomb(this.x / 48, this.y / 48, Sprite.bomb.getFxImage(), "static", Game.getLengthBom());
                bombs.add(bomb);
                Game.getBombs().add(bomb);
                numberOfBombs--;
            }
        }
    }

    public static void removeBomb(Bomb bomb) {
        Game.getBombs().remove(bomb);
        numberOfBombs++;
        bombs.remove(bomb);
    }

    public void setNumberOfBombs(int numberOfBombs) {
        Bomber.numberOfBombs = numberOfBombs;
    }

    private boolean isFree(int nextX, int nextY) {
        int size = Sprite.SCALED_SIZE;

        int nextX_1 = nextX / size;
        int nextY_1 = nextY / size;
    
        int nextX_2 = (nextX + size - 14) / size;
        int nextY_2 = nextY / size;
    
        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 2) / size;
    
        int nextX_4 = (nextX + size - 14) / size;
        int nextY_4 = (nextY + size - 2) / size;

        return (Game.elementStorage[nextY_1][nextX_1].collisionHanle(this)
                && Game.elementStorage[nextY_2][nextX_2].collisionHanle(this)
                && Game.elementStorage[nextY_3][nextX_3].collisionHanle(this)
                && Game.elementStorage[nextY_4][nextX_4].collisionHanle(this));
    }

    /**
     * create animation by using sprites
     */
    public void chooseSprite() {
        switch (this.getDirection()) {
            case "N":
                this.setSprite(Sprite.player_up);
                if (this.getMovingStage()) {
                    this.setImg(
                            Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20)
                                    .getFxImage());
                }
                break;
            case "E":
            default:
                this.setSprite(Sprite.player_right);
                if (this.getMovingStage()) {
                    this.setImg(
                            Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20)
                                    .getFxImage());
                }
                break;
            case "S":
                this.setSprite(Sprite.player_down);
                if (this.getMovingStage()) {
                    this.setImg(
                            Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20)
                                    .getFxImage());
                }
                break;
            case "W":
                this.setSprite(Sprite.player_left);
                if (this.getMovingStage()) {
                    this.setImg(
                            Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20)
                                    .getFxImage());
                }
                break;
        }
    }

    /**
     * set moving directions
     */
    public void goUp(GraphicsContext gc) {
        this.setDirection("N");
        this.setMovingState(true);
        this.update(gc);

        if (isFree(this.getX(), this.getY() - Game.getSPEED())) {
            this.setY(this.getY() - Game.getSPEED());
            Entity e = Game.getEntityAt(this.getX(), this.getY());
            e.collisionHanle(this);
        }
    }

    public void goDown(GraphicsContext gc) {
        this.setDirection("S");
        this.setMovingState(true);
        this.update(gc);

        if (isFree(this.getX(), this.getY() + Game.getSPEED())) {
            this.setY(this.getY() + Game.getSPEED());
        }
    }

    public void goLeft(GraphicsContext gc) {
        this.setDirection("W");
        this.setMovingState(true);
        this.update(gc);

        if (isFree(this.getX() - Game.getSPEED(), this.getY())) {
            this.setX(this.getX() - Game.getSPEED());
        }
    }

    public void goRight(GraphicsContext gc) {
        this.setDirection("E");
        this.setMovingState(true);
        this.update(gc);

        if (isFree(this.getX() + Game.getSPEED(), this.getY())) {
            this.setX(this.getX() + Game.getSPEED());
        }
    }

    /**
     * stop animation by set default image sprite
     */
    public void stopUp() {
        this.setMovingState(false);
        this.setImg(Sprite.player_up.getFxImage());
    }

    public void stopDown() {
        this.setMovingState(false);
        this.setImg(Sprite.player_down.getFxImage());
    }

    public void stopLeft() {
        this.setMovingState(false);
        this.setImg(Sprite.player_left.getFxImage());
    }

    public void stopRight() {
        this.setMovingState(false);
        this.setImg(Sprite.player_right.getFxImage());
    }

    public boolean isAlive() {
        return isAlive;
    }
}
