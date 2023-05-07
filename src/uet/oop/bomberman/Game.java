package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemies.*;
import uet.oop.bomberman.entities.items.BombItem;
import uet.oop.bomberman.entities.items.SparkItem;
import uet.oop.bomberman.entities.items.SpeedItem;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.SparkSegment;
import uet.oop.bomberman.graphics.Draw;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.Map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game extends Application {

  private static final String staticType = "static";
  private static final String dynamicType = "dynamic";
  
  private static int lengthBom = 1;
  private static int numOfBomb = 1;
  public static int SPEED = 2;
  public static int time = 60;
  private static int timeNextlevel = 120;
  private static int saveLengthBomb = 1;
  private static int currentLevel = 1;
  public static int state = 1; // 0 ingame, 1 start, 2 next level. 3 endgame, 4 win game, 5 tutorial
  public static int countTime = 0;
  public static int scoreOfPlayer = 0;
  
  public static Entity[][] elementStorage;
  private static List<Entity> entities = new ArrayList<>();
  private static List<Entity> stillObjects = new ArrayList<>();
  private static List<Entity> brickAndWall = new ArrayList<>();
  private static List<Bomb> bombs = new ArrayList<>();
  private static List<Enemy> enemies = new ArrayList<>();
  
  private GraphicsContext gc;
  private Canvas canvas;
  private static Group root = new Group();
  
  private static Map level1;
  private static Map level2;
  private static Map level3;
  private static List<Map> listMap = new ArrayList<>();
  
  private static boolean finished = false;
  private static boolean nextLevel = false;
  private boolean goUp, goDown, goLeft, goRight;
  
  private static GameSound soundOfGame = new GameSound();
  
  public static FXMLLoader endGame =
          new FXMLLoader(Game.class.getClassLoader().getResource("EndGame.fxml"));
  public static FXMLLoader startGame =
          new FXMLLoader(Game.class.getClassLoader().getResource("PlayGame.fxml"));
  public static FXMLLoader tutorialGame =
          new FXMLLoader(Game.class.getClassLoader().getResource("Tutorial.fxml"));
  public static FXMLLoader winGame =
          new FXMLLoader(Game.class.getClassLoader().getResource("WinGame.fxml"));
  
  public static AnchorPane endPane;
  public static AnchorPane startPane;
  public static AnchorPane tutorialPane;
  public static AnchorPane winGamePane;

  static {
    try {
      endPane = endGame.load();
    } catch (IOException e) {
      System.out.println("Loi load endGame pane");
    }
  }
  static {
    try {
      startPane = startGame.load();
    } catch (IOException e) {
      System.out.println("Loi load startGame pane");
    }
  }
  static {
    try {
      tutorialPane = tutorialGame.load();
    } catch (IOException e) {
      System.out.println("Loi load tutorialGame pane");
    }
  }
  static {
    try {
      winGamePane = winGame.load();
    } catch (IOException e) {
      System.out.println("Loi load winGame pane");
    }
  }
  
  
  public static Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), dynamicType);
  
  
  @Override
  public void start(Stage stage) {
    // Load map
    level1 = Map.level1;
    listMap.add(level1);
    level2 = Map.level2;
    listMap.add(level2);
    level3 = Map.level3;
    listMap.add(level3);
    
    elementStorage = new Entity[level1.height][level1.width];
    
    // Tao Canvas
    canvas = new Canvas(Sprite.SCALED_SIZE * level1.width, Sprite.SCALED_SIZE * level1.height);
    gc = canvas.getGraphicsContext2D();
    
    endPane.setPrefWidth(Sprite.SCALED_SIZE * 31);
    endPane.setPrefHeight(Sprite.SCALED_SIZE * 13);
    
    // Tao root container
    
    root.getChildren().add(canvas);
    
    // Tao scene
    Scene scene = new Scene(root);
    stage.setResizable(false);
    stage
            .getIcons()
            .add(
                    new Image(
                            "https://th.bing.com/th/id/R.ef792b4a0518c7c6cc91fe8c2e524448?rik=%2fm52y41WHM%2fdGA&riu=http%3a%2f%2fimg4.wikia.nocookie.net%2f__cb20131024214446%2frap-battle-nation%2fimages%2fc%2fc3%2fBomberman2.png&ehk=XMkO%2fUv6N4yn0yLXGzNiIQjJ%2bjR1yG6ghl1ZPlKMcN8%3d&risl=&pid=ImgRaw&r=0"));
    
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        switch (event.getCode()) {
          case UP:
            goUp = true;
            break;
          case DOWN:
            goDown = true;
            break;
          case LEFT:
            goLeft = true;
            break;
          case RIGHT:
            goRight = true;
            break;
          case SPACE:
            soundOfGame.plantBomb();
            break;
        }
      }
    });
    
    scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        switch (event.getCode()) {
          case UP:
            if (goUp) {
              goUp = false;
              bomberman.stopUp();
            }
            break;
          case DOWN:
            if (goDown) {
              goDown = false;
              bomberman.stopDown();
            }
            break;
          case LEFT:
            if (goLeft) {
              goLeft = false;
              bomberman.stopLeft();
            }
            break;
          case RIGHT:
            if (goRight) {
              goRight = false;
              bomberman.stopRight();
            }
            break;
          case SPACE:
            bomberman.putBomb();
            break;
        }
      }
    });
    
    stage.setScene(scene);
    stage.show();
    playGame();
    
    /** It is going to be called in every frame while the AnimationTimer is active. */
    AnimationTimer timer =new AnimationTimer() {
      @Override
      public void handle(long l) {
        if (!bomberman.isAlive()) {
          if (time > 0) {
            render();
            update();
            time--;
          } else {
            Draw.drawEndGame(gc, level1);
            if (state == 3) {
              root.getChildren().add(endPane);
              state = 0;
            }
          }
          return;
        }
        
        if (state == 1) {
          if (!root.getChildren().contains(startPane)) root.getChildren().add(startPane);
          Draw.drawPlayGame(gc, level1);
          return;
        }
        else if (state == 2) {
          if (timeNextlevel > 0 && currentLevel != listMap.size()) {
            timeNextlevel--;
            Draw.drawNextLevel(gc, level1);
          } else {
            state = 0;
            nextLevel();
            timeNextlevel = 120;
          }
          return;
        }
        else if (state == 4) {
          Draw.drawWinGame(gc, level1);
          return;
        }
        else if (state == 5) {
          Draw.drawTutorialGame(gc, level1);
          if (!root.getChildren().contains(tutorialPane)) {
            root.getChildren().add(tutorialPane);
          }
          return;
        }
        
        if (goUp) {
          bomberman.goUp(gc);
        }
        if (goDown) {
          bomberman.goDown(gc);
        }
        if (goLeft) {
          bomberman.goLeft(gc);
        }
        if (goRight) {
          bomberman.goRight(gc);
        }
        
        render();
        update();
        Entity.animate();
      }
    };
    timer.start();
  }
  
  
  
  /****/
  public static GameSound getSoundOfGame() {
    return soundOfGame;
  }
  
  public static int getSaveLengthBomb() {
    return saveLengthBomb;
  }
  
  public static void setSaveLengthBomb(int saveLengthBomb) {
    Game.saveLengthBomb = saveLengthBomb;
  }
  
  public static boolean isFinished() {
    return finished;
  }
  
  public static void setFinished(boolean finished) {
    Game.finished = finished;
  }
  
  public static List<Map> getListMap() {
    return listMap;
  }
  
  public static void setNextLevel(boolean nextLevel) {
    Game.nextLevel = nextLevel;
  }
  
  public static boolean getNextLevel() {
    return nextLevel;
  }
  
  public static Group getRoot() {
    return root;
  }
  
  public static int getCurrentLevel() {
    return currentLevel;
  }
  
  public static int getNumOfBomb() {
    return numOfBomb;
  }
  
  public static void setNumOfBomb(int numOfBomb) {
    Game.numOfBomb = numOfBomb;
  }
  
  public static int getLengthBom() {
    return lengthBom;
  }
  
  public static void setLengthBom(int lengthBom) {
    Game.lengthBom = lengthBom;
  }
  
  public static List<Enemy> getEnemies() {
    return enemies;
  }
  
  public static void setEnemies(List<Enemy> enemies) {
    Game.enemies = enemies;
  }
  
  public static List<Bomb> getBombs() {
    return bombs;
  }
  
  public static List<Entity> getStillObjects() {
    return stillObjects;
  }
  
  public static List<Entity> getBrickAndWall() {
    return brickAndWall;
  }
  
  public static int getSPEED() {
    return SPEED;
  }
  
  public static void setSPEED(int SPEED) {
    Game.SPEED = SPEED;
  }
  
  public List<Entity> getEntities() {
    return entities;
  }

  
  
  /****/
  public static void createMap(Map level) {
    for (int i = 0; i < level.height; i++) {
      for (int j = 0; j < level.width; j++) {
        Entity object;
        String charAt = new String(String.valueOf(level.map_info[i][j]));

        if (charAt.equals("#")) {
          object = new Wall(j, i, Sprite.wall.getFxImage(), staticType);
        } else if (charAt.equals("*")) {
          object =
              new BrickAndGrass(
                  j,
                  i,
                  new Grass(j, i, Sprite.grass.getFxImage(), staticType),
                  new Brick(j, i, Sprite.brick.getFxImage(), staticType));
        } else if (charAt.equals("f")) {
          object =
              new BrickAndGrass(
                  j,
                  i,
                  new Grass(j, i, Sprite.grass.getFxImage(), staticType),
                  new SparkItem(j, i, Sprite.powerup_flames.getFxImage(), staticType),
                  new Brick(j, i, Sprite.brick.getFxImage(), staticType));
        } else if (charAt.equals("b")) {
          object =
              new BrickAndGrass(
                  j,
                  i,
                  new Grass(j, i, Sprite.grass.getFxImage(), staticType),
                  new BombItem(j, i, Sprite.powerup_bombs.getFxImage(), staticType),
                  new Brick(j, i, Sprite.brick.getFxImage(), staticType));
        } else if (charAt.equals("s")) {
          object =
              new BrickAndGrass(
                  j,
                  i,
                  new Grass(j, i, Sprite.grass.getFxImage(), staticType),
                  new SpeedItem(j, i, Sprite.powerup_speed.getFxImage(), staticType),
                  new Brick(j, i, Sprite.brick.getFxImage(), staticType));
        } else if (charAt.equals("x")) {
          object =
              new BrickAndGrass(
                  j,
                  i,
                  new Grass(j, i, Sprite.grass.getFxImage(), staticType),
                  new Portal(j, i, Sprite.portal.getFxImage(), staticType),
                  new Brick(j, i, Sprite.brick.getFxImage(), staticType));
        } else {
          object = new Grass(j, i, Sprite.grass.getFxImage(), staticType);
        }
        if (charAt.equals("1")) {
          enemies.add(new Balloon(j, i, Sprite.balloom_right1.getFxImage(), dynamicType));

        } else if (charAt.equals("2")) {
          enemies.add(new Kondoria(j, i, Sprite.kondoria_right1.getFxImage(), dynamicType));

        } else if (charAt.equals("3")) {
          enemies.add(new Doll(j, i, Sprite.doll_left1.getFxImage(), dynamicType));
        } else if (charAt.equals("4")) {
          enemies.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage(), dynamicType));
        }
        stillObjects.add(object);
        elementStorage[i][j] = object;

        if (object instanceof BrickAndGrass || object instanceof Wall) {
          brickAndWall.add(object);
        }
      }
    }
  }

  public void update() {
    countTime++;
    Text textLose = new Text("Level: " + Game.getCurrentLevel() + "\tSCORE: " + scoreOfPlayer);
    double width = textLose.getLayoutBounds().getWidth();
    gc.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 26));
    gc.setFill(Color.BROWN);
    gc.setTextAlign(TextAlignment.CENTER);
    gc.fillText(
        "Level: " + Game.getCurrentLevel() + "\tSCORE: " + scoreOfPlayer,
        Draw.screenWidth / 2 - width / 2,
        32);
    for (int i = 0; i < entities.size(); i++) {
      entities.get(i).update(gc);
    }

    for (int i = 0; i < brickAndWall.size(); i++) {
      if (brickAndWall.get(i) instanceof BrickAndGrass) {
        if (((BrickAndGrass) brickAndWall.get(i)).getTop() instanceof Grass) {
          brickAndWall.remove(i);
          continue;
        }
      }
      brickAndWall.get(i).update(gc);
    }

    for (int i = 0; i < bombs.size(); i++) {
      bombs.get(i).update(gc);
    }

    for (int i = 0; i < enemies.size(); i++) {
      if (enemies.get(i).removed) {
        enemies.remove(i);
      } else {
        enemies.get(i).update(gc);
      }
    }
    bomberman.update(gc);
  }

  public void render() {
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    stillObjects.forEach(g -> g.render(gc));
    entities.forEach(g -> g.render(gc));
    bombs.forEach(g -> g.render(gc));
    enemies.forEach(g -> g.render(gc));
    bomberman.render(gc);
  }
  
  
  
  /****/
  public static void playGame() {
    bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), dynamicType);
    createMap(listMap.get(0));
  }
  
  public static void playGameAgain() {
    currentLevel = 1;
    resetGame();
    if (root.getChildren().contains(endPane)) {
      root.getChildren().remove(endPane);
    }
    if (root.getChildren().contains(winGamePane)) {
      root.getChildren().remove(winGamePane);
    }
    createMap(listMap.get(0));
  }
  
  public static void nextLevel() {
    if (currentLevel == listMap.size()) {
      state = 4;
      root.getChildren().add(winGamePane);
      return;
    }
    resetGame();
    bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), dynamicType);
    currentLevel++;
    createMap(listMap.get(currentLevel - 1));
  }
  
  public static void resetGame() {
    state = 0;
    time = 60;
    bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), dynamicType);
    elementStorage = new Entity[level1.height][level1.width];
    enemies.clear();
    brickAndWall.clear();
    bombs.clear();
    stillObjects.clear();
    lengthBom = 1;
    bomberman.setNumberOfBombs(1);
    numOfBomb = 1;
    SPEED = 2;
  }
  
  public static Entity getEntityAt(double x, double y) {
    Entity res = null;
    
    res = getSparkSegmentAtPos((int) x, (int) y);
    if (res != null) {
      return res;
    }
    
    res = getBombAtPos((int) x, (int) y);
    if (res != null) {
      return res;
    }
    
    res = getEnemyAt((int) x, (int) y);
    if (res != null) {
      return res;
    }
    
    int x_ = (int) x / Sprite.SCALED_SIZE;
    int y_ = (int) y / Sprite.SCALED_SIZE;
    
    return stillObjects.get(x_ + y_ * 31);
  }
  
  public static SparkSegment getSparkSegmentAtPos(int x, int y) {
    for (int i = 0; i < bombs.size(); i++) {
      SparkSegment sparkSegment = bombs.get(i).sparkAt(x, y);
      if (sparkSegment != null) {
        return sparkSegment;
      }
    }
    return null;
  }
  
  public static Enemy getEnemyAt(int x, int y) {
    for (int i = 0; i < enemies.size(); i++) {
      int x_Enemies = enemies.get(i).getX();
      int y_Enemies = enemies.get(i).getY();
      Rectangle2D a = new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
      Rectangle2D b = new Rectangle2D(x_Enemies, y_Enemies, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
      
      if (a.intersects(b)) {
        return enemies.get(i);
      }
    }
    return null;
  }
  
  public static Bomb getBombAtPos(int x, int y) {
    int x_ = (int) x / Sprite.SCALED_SIZE;
    int y_ = (int) y / Sprite.SCALED_SIZE;
    
    for (int i = 0; i < bombs.size(); i++) {
      int x_Bomb = ((int) (bombs.get(i).getX() / Sprite.SCALED_SIZE));
      int y_Bomb = ((int) (bombs.get(i).getY() / Sprite.SCALED_SIZE));
      if (x_ == x_Bomb && y_ == y_Bomb) {
        return bombs.get(i);
      }
    }
    return null;
  }
  
  public static boolean dectectNoEnemy() {
    if (enemies.size() == 0) {
      return true;
    }
    return false;
  }

  
  
  /****/
  public static void removeStartPane() {
    if (root.getChildren().contains(startPane)) {
      root.getChildren().remove(startPane);
    }
  }

  public static void removeTutorialPane() {
    if (root.getChildren().contains(tutorialPane)) {
      root.getChildren().remove(tutorialPane);
    }
  }
}
