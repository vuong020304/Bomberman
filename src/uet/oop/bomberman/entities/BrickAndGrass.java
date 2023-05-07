package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class BrickAndGrass extends Entity {

  protected List<Entity> entities = new ArrayList<>();

  public BrickAndGrass(int x, int y, Entity... entities) {
    super(x, y);
    for (int i = 0; i < entities.length; i++) {
      this.entities.add(entities[i]);
    }
  }

  public List<Entity> getEntities() {
    return entities;
  }

  public Entity getTop() {
    return entities.get(entities.size() - 1);
  }

  private void remove() {
    Entity top = getTop();
    if (top.delete) {
      entities.remove(top);
    }
  }

  @Override
  public void update(GraphicsContext gc) {
    remove();
    getTop().update(gc);
  }

  @Override
  public boolean collisionHanle(Entity entity) {
    return getTop().collisionHanle(entity);
  }

  @Override
  public void render(GraphicsContext gc) {
    getTop().render(gc);
  }

  @Override
  public void chooseSprite() {}
}
