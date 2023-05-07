package uet.oop.bomberman.fxml;

import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Bomber;

public class WinGameController {
  public void playagain(MouseEvent mouseEvent) {
    Bomber.momentScore=1;
    Game.scoreOfPlayer=0;
    Game.getSoundOfGame().itemSound();
    Game.getSoundOfGame().playGame();
    Game.getSoundOfGame().pauseWinGame();
    Game.playGameAgain();
  }

  public void quit(MouseEvent mouseEvent) {
    Game.getSoundOfGame().itemSound();
    Platform.exit();
  }
}
