package org.pacman.blocks;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import org.pacman.Sprites;

import java.util.List;

import static org.pacman.data.Properties.getGridConfig;

public abstract class Moving extends Block {

  public Moving(Sprites sprite, int row, int column) {
    super(sprite, row, column);
    setPosition();
  }

  public Moving(Sprites sprite, BoundingBox boundingBox) {
    super(sprite); //, (((int) boundingBox.getMinY() / getGridConfig().tileSize())), (((int) boundingBox.getMinX() / getGridConfig().tileSize())));

    row = (int) boundingBox.getMinY();
    column = (int) boundingBox.getMinX();

    imageView = sprite.getImageView(getGridConfig().tileSize());
  }

  abstract public void move(Direction direction);

  public boolean isColliding(Block block) {
    return (intersectsWithTolerance(block, 1.0));
  }

  public boolean isColliding(List<? extends Block> blocks) {
    for (Block block : blocks) {
      if (intersectsWithTolerance(block, 1.0)) {
        return true;
      }
    }
    return false;
  }

  public Block isCollisionBlock(List<? extends Block> blocks) {
    for (Block block : blocks) {
      if (intersectsWithTolerance(block, 1.0)) {
        return block;
      }
    }
    return null;
  }

  private boolean intersectsWithTolerance(Block block, double tolerance) {
    Bounds p = imageView.localToScene(imageView.getBoundsInLocal());
    Bounds b = block.getImageView().localToScene(block.getImageView().getBoundsInLocal());
    double dx = Math.min(p.getMaxX(), b.getMaxX()) - Math.max(p.getMinX(), b.getMinX());
    double dy = Math.min(p.getMaxY(), b.getMaxY()) - Math.max(p.getMinY(), b.getMinY());
    return (dx > tolerance && dy > tolerance);
  }



}
