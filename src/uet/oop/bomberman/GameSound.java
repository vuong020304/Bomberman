package uet.oop.bomberman;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.applet.AudioClip;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameSound {
  public static GameSound instance;
  public static final String MENU = "/sound/menu.wav";
  public static final String PLAYGAME = "/sound/playgame.wav";
  public static final String BOMB = "/sound/newbomb.wav";
  public static final String BOMBER_DIE = "/sound/bomber_die.wav";
  public static final String MONSTER_DIE = "/sound/monster_die.wav";
  public static final String BOMB_BANG = "/sound/bomb_bang.wav";
  public static final String ITEM = "/sound/item.wav";
  public static final String WIN = "/sound/win.wav";
  public static final String LOSE = "/sound/lose.wav";
  private HashMap<String, MediaPlayer> listAudio = new HashMap<>();
  
  public GameSound() {
    menuSound();
  }
  
  public void menuSound() {
    try {
      Media menuMedia = new Media(getClass().getResource(MENU).toURI().toString());
      MediaPlayer menuMediaPlayer = new MediaPlayer(menuMedia);
      listAudio.put(MENU, menuMediaPlayer);
      menuMediaPlayer.setOnReady(new Runnable() {
        @Override
        public void run() {
          menuMediaPlayer.setCycleCount(20);
          menuMediaPlayer.setAutoPlay(true);
        }
      });
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void pauseMenu() {
    listAudio.get(MENU).pause();
  }
  
  public void itemSound() {
    try {
      Media itemMedia = new Media(getClass().getResource(ITEM).toURI().toString());
      MediaPlayer itemMediaPlayer = new MediaPlayer(itemMedia);
      listAudio.put(ITEM, itemMediaPlayer);
      itemMediaPlayer.setOnReady(new Runnable() {
        @Override
        public void run() {
          itemMediaPlayer.play();
        }
      });
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }
  
  public void plantBomb() {
    try {
      Media plantBombMedia = new Media(getClass().getResource(BOMB).toURI().toString());
      MediaPlayer plantBombMediaPlayer = new MediaPlayer(plantBombMedia);
      listAudio.put(BOMB, plantBombMediaPlayer);
      plantBombMediaPlayer.setOnReady(new Runnable() {
        @Override
        public void run() {
          plantBombMediaPlayer.play();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void bombBang() {
    try {
      Media bombBangMedia = new Media(getClass().getResource(BOMB_BANG).toURI().toString());
      MediaPlayer bombBangMediaPlayer = new MediaPlayer(bombBangMedia);
      listAudio.put(BOMB_BANG, bombBangMediaPlayer);
      bombBangMediaPlayer.setOnReady(new Runnable() {
        @Override
        public void run() {
          bombBangMediaPlayer.play();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void bombermanDie() {
    try {
      Media bombermanDieMedia = new Media(getClass().getResource(BOMBER_DIE).toURI().toString());
      MediaPlayer bombermanDieMediaPlayer = new MediaPlayer(bombermanDieMedia);
      listAudio.put(BOMBER_DIE, bombermanDieMediaPlayer);
      bombermanDieMediaPlayer.setOnReady(new Runnable() {
        @Override
        public void run() {
          bombermanDieMediaPlayer.play();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void enemyDie() {
    try {
      Media enemyDieMedia = new Media(getClass().getResource(MONSTER_DIE).toURI().toString());
      MediaPlayer enemyDieMediaPlayer = new MediaPlayer(enemyDieMedia);
      listAudio.put(MONSTER_DIE, enemyDieMediaPlayer);
      enemyDieMediaPlayer.setOnReady(new Runnable() {
        @Override
        public void run() {
          enemyDieMediaPlayer.play();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void loseGame() {
    try {
      Media loseGameMedia = new Media(getClass().getResource(LOSE).toURI().toString());
      MediaPlayer loseGameMediaPlayer = new MediaPlayer(loseGameMedia);
      listAudio.put(LOSE, loseGameMediaPlayer);
      loseGameMediaPlayer.setOnReady(new Runnable() {
        @Override
        public void run() {
          loseGameMediaPlayer.setCycleCount(1);
          loseGameMediaPlayer.play();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void pauseLoseGame() {
    listAudio.get(LOSE).stop();
  }
  
  
  public void playGame() {
    try {
      Media playGameMedia = new Media(getClass().getResource(PLAYGAME).toURI().toString());
      MediaPlayer playGameMediaPlayer = new MediaPlayer(playGameMedia);
      listAudio.put(PLAYGAME, playGameMediaPlayer);
      playGameMediaPlayer.setOnReady(new Runnable() {
        @Override
        public void run() {
          playGameMediaPlayer.setCycleCount(100);
          playGameMediaPlayer.play();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void pausePlayGame() {
    listAudio.get(PLAYGAME).pause();
  }
  
  public void winGame() {
    try {
      Media winGameMedia = new Media(getClass().getResource(WIN).toURI().toString());
      MediaPlayer winGameMediaPlayer = new MediaPlayer(winGameMedia);
      listAudio.put(WIN, winGameMediaPlayer);
      winGameMediaPlayer.setOnReady(new Runnable() {
        @Override
        public void run() {
          winGameMediaPlayer.setCycleCount(1);
          winGameMediaPlayer.play();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void pauseWinGame() {
    listAudio.get(WIN).stop();
  }


//    public static GameSound getIstance() {
//        if (instance == null) {
//            instance = new GameSound();
//        }
//
//        return instance;
//    }
//
//    public void loadAllAudio() {
//        putAudio(MENU);
//        putAudio(PLAYGAME);
//        putAudio(BOMB);
//        putAudio(MONSTER_DIE);
//        putAudio(BOMBER_DIE);
//        putAudio(BONG_BANG);
//        putAudio(ITEM);
//        putAudio(WIN);
//        putAudio(LOSE);
//    }
//
//    public void stop() {
//        getAudio(MENU).stop();;
//        getAudio(PLAYGAME).stop();
//        getAudio(BOMB).stop();
//        getAudio(BONG_BANG).stop();
//        getAudio(WIN).stop();
//        getAudio(LOSE).stop();
//    }
//
//    public void putAudio(String name) {
//        AudioClip auClip = Applet.newAudioClip(GameSound.class.getResource(name));
//        audioMap.put(name, auClip);
//    }
//
//    public java.applet.AudioClip getAudio(String name) {
//        return audioMap.get(name);
//    }
}
