package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.map.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Draw {
  
  public static int screenWidth = Sprite.SCALED_SIZE * 31;
  public static int screenHeight = Sprite.SCALED_SIZE * 13;
  
  public static void drawTutorialGame(GraphicsContext g, Map level1) {
    g.fillRect(0, 0, Sprite.SCALED_SIZE * level1.width, Sprite.SCALED_SIZE * level1.height);
  
    File file = new File("res/Tutorial.png");
  
    String localUrl = null;
    try {
      localUrl = file.toURI().toURL().toString();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    Image i = new Image(localUrl);
    g.drawImage(i, 0, 0, screenWidth, screenHeight);
  }
  
  public static void drawPlayGame(GraphicsContext g, Map level1) {
    g.fillRect(0, 0, Sprite.SCALED_SIZE * level1.width, Sprite.SCALED_SIZE * level1.height);
    
    File file = new File("res/EndGame.png");
    
    String localUrl = null;
    try {
      localUrl = file.toURI().toURL().toString();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    Image i = new Image(localUrl);
    g.drawImage(i, 0, 0, screenWidth, screenHeight);
  }
  
  public static void drawEndGame(GraphicsContext g, Map level1) {
    g.fillRect(0, 0, Sprite.SCALED_SIZE * level1.width, Sprite.SCALED_SIZE * level1.height);
    File file = new File("res/lose.png");
    String localUrl = null;
    try {
      localUrl = file.toURI().toURL().toString();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    assert localUrl != null;
    Image i = new Image(localUrl);
    g.drawImage(i, 0, 0, screenWidth, screenHeight);
    g.setFont(Font.font("Arial", FontWeight.BOLD, 76));
    g.setFill(Color.WHITE);
    g.setStroke(Color.BLACK);
    g.stroke();
    try {
      File myObj = new File("res/highscore/HighestScore");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        //        drawText(g,"HIGH SCORE: " + data + "\n" +"YOUR SCORE: " + scoreOfPlayer);
        Text textLose = new Text("HIGH SCORE: " + data + "\n" + "YOUR SCORE: " + Game.scoreOfPlayer);
        double width = textLose.getLayoutBounds().getWidth();
        g.setFill(Color.WHITE);
        g.setTextAlign(TextAlignment.CENTER);
        g.fillText("HIGH SCORE: " + data + "\n" + "YOUR SCORE: " + Game.scoreOfPlayer, 1090, 240);
        //        Text text1 = new Text(text);
        //        double width = text1.getLayoutBounds().getWidth();
        //        gc.setFill(Color.WHITE);
        //        gc.setTextAlign(TextAlignment.CENTER);
        //        gc.fillText(text, screenWidth / 2 - width / 2, 100);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  
  public static void drawWinGame(GraphicsContext g, Map level1) {
    g.fillRect(0, 0, Sprite.SCALED_SIZE * level1.width, Sprite.SCALED_SIZE * level1.height);
    File file = new File("res/Victory.png");
    String localUrl = null;
    try {
      localUrl = file.toURI().toURL().toString();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    assert localUrl != null;
    Image i = new Image(localUrl);
    g.drawImage(i, 0, 0, screenWidth, screenHeight);
  }
  
  public static void drawNextLevel(GraphicsContext g, Map level1) {
    g.setFill(javafx.scene.paint.Color.WHITE);
    g.fillRect(0, 0, Sprite.SCALED_SIZE * level1.width, Sprite.SCALED_SIZE * level1.height);
  
    Font font = new Font("Arial", 50);
    g.setFont(font);
  
    File file = new File("res/NextLevel.png");
  
    String localUrl = null;
    try {
      localUrl = file.toURI().toURL().toString();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    Image i = new Image(localUrl);
    g.drawImage(i, 0, 0, screenWidth, screenHeight);
  }
  
  public static void drawText(GraphicsContext gc, String text) {
    Text text1 = new Text(text);
    double width = text1.getLayoutBounds().getWidth();
    gc.setFill(Color.WHITE);
    gc.setTextAlign(TextAlignment.CENTER);
    
    gc.fillText(text, screenWidth / 2 - width / 2, 100);
  }
}
