package uet.oop.bomberman.fxml;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Bomber;

public class EndGameController {
  @FXML private AnchorPane endGamePane;
  @FXML private TextFlow textScore;

  public void start() {}

  public void playAgain(MouseEvent mouseEvent) {
    Bomber.momentScore=1;
    Game.scoreOfPlayer=0;
    Game.getSoundOfGame().itemSound();
    Game.getSoundOfGame().playGame();
    Game.playGameAgain();
  }

  public void Quit(MouseEvent mouseEvent) {
    Game.getSoundOfGame().itemSound();
    Platform.exit();
  }
}
