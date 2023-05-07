package uet.oop.bomberman.fxml;

import javafx.scene.input.MouseEvent;
import uet.oop.bomberman.Game;

public class TutorialController {
  public void back(MouseEvent mouseEvent) {
    Game.getSoundOfGame().itemSound();
    Game.removeTutorialPane();
    Game.state = 1;
  }


}
