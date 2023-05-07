package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
  // Tọa độ X tính từ góc trái trên trong Canvas
  protected int x;

  // Tọa độ Y tính từ góc trái trên trong Canvas
  protected int y;
  protected boolean delete = false;

  protected String direction = "E";
  protected Image img;
  protected String type;
  protected Boolean isMoving = false;
  protected Sprite sprite;
  private int timeUp;

  public static int animate = 0;
  public static final int MAX_ANIMATE = 7500;

  public Entity() {}

  public Entity(int x, int y) {
    this.x = x * Sprite.SCALED_SIZE;
    this.y = y * Sprite.SCALED_SIZE;
  }

  // Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
  public Entity(int xUnit, int yUnit, Image img, String type) {
    this.x = xUnit * Sprite.SCALED_SIZE;
    this.y = yUnit * Sprite.SCALED_SIZE;
    this.img = img;
    this.type = type;
  }

  public Entity(Entity ob) {
    this.x = ob.getX() * Sprite.SCALED_SIZE;
    this.y = ob.getY() * Sprite.SCALED_SIZE;
    this.img = ob.getImg();
    this.type = ob.getType();
  }

  public static void animate() {
    if (animate < MAX_ANIMATE) {
      animate++;
    } else {
      // reset animation
      animate = 0;
    }
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public Image getImg() {
    return img;
  }

  public void setImg(Image img) {
    this.img = img;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public Boolean getMovingStage() {
    return isMoving;
  }

  public void setMovingState(Boolean moving) {
    isMoving = moving;
  }

  public Boolean getMoving() {
    return isMoving;
  }

  public void setMoving(Boolean moving) {
    isMoving = moving;
  }

  public Sprite getSprite() {
    return sprite;
  }

  public void setSprite(Sprite sprite) {
    this.sprite = sprite;
  }

  public int getTimeUp() {
    return timeUp;
  }

  public void setTimeUp(int timeUp) {
    this.timeUp = timeUp;
  }

  /** */
  public void render(GraphicsContext gc) {
    gc.drawImage(img, x, y);
  }

  public abstract void update(GraphicsContext gc);

  public abstract boolean collisionHanle(Entity e);

  public abstract void chooseSprite();
}
