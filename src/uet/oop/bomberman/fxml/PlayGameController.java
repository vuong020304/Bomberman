package uet.oop.bomberman.fxml;

import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import uet.oop.bomberman.Game;

public class PlayGameController {
  public void play(MouseEvent mouseEvent) {
    Game.state = 0;
    Game.getSoundOfGame().itemSound();
    Game.removeStartPane();
    Game.getSoundOfGame().pauseMenu();
    Game.getSoundOfGame().playGame();
  }

  public void quit(MouseEvent mouseEvent) {
    Game.getSoundOfGame().itemSound();
    Platform.exit();
  }

  public void tutorial(MouseEvent mouseEvent) {
    Game.getSoundOfGame().itemSound();
    Game.state = 5;
    Game.removeStartPane();
  }
}
