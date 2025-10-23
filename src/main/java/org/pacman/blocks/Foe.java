package org.pacman.blocks;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import org.pacman.Sprites;

import static org.pacman.data.Field.getWalls;
import static org.pacman.data.Properties.getGridConfig;

public class Foe extends Moving {

  Direction direction;
  int forceDirectionChange = 30;

  public Foe(Sprites sprite, int row, int column) {
    super(sprite, row, column);
    direction = Direction.UP;
  }

  public Foe(Sprites sprite, BoundingBox boundingBox) {
    super(sprite, boundingBox);
    setPosition();
    direction = Direction.UP;
  }

  public void move() {
    switch (direction) {
      case LEFT: {
        if (getMovedBoundingBox(0, -8).isColliding(getWalls())) {
          direction = direction.getRandomDirectionExclusively();
          break;
        }
        column -= getGridConfig().quarterTileSize();
        if (column < getGridConfig().tileSize() / 2) {
          column = getGridConfig().boardWidth() - getGridConfig().tileSize() / 2;
        }
        forceDirectionChange--;
        break;
      }
      case RIGHT: {
        if (getMovedBoundingBox(0, 8).isColliding(getWalls())) {
          direction = direction.getRandomDirectionExclusively();
          break;
        }
        column += getGridConfig().quarterTileSize();
        if (column >= getGridConfig().boardWidth() - getGridConfig().tileSize() / 2) {
          column = -getGridConfig().tileSize() / 2;
        }
        forceDirectionChange--;
        break;
      }
      case UP: {
        if (getMovedBoundingBox(-8, 0).isColliding(getWalls())) {
          direction = direction.getRandomDirectionExclusively();
          break;
        }
        row -= getGridConfig().quarterTileSize();
        if (row < getGridConfig().tileSize() / 2) {
          row = getGridConfig().boardHeight() - getGridConfig().tileSize() / 2;
        }
        forceDirectionChange--;
        break;
      }
      case DOWN: {
        if (getMovedBoundingBox(8, 0).isColliding(getWalls())) {
          direction = direction.getRandomDirectionExclusively();
          break;
        }
        row += getGridConfig().quarterTileSize();
        if (row >= getGridConfig().boardHeight() - getGridConfig().tileSize() / 2) {
          row = -getGridConfig().tileSize() / 2;
        }
        forceDirectionChange--;
        break;
      }
      default:
    }

    if (forceDirectionChange <= 0) {
      direction = direction.getRandomDirectionExclusively();
      forceDirectionChange = 20;
    }
    setPosition();
  }

  private Foe getMovedBoundingBox(int columnDir, int rowDir) {
    Bounds b = imageView.getLayoutBounds();
    BoundingBox boundingBox = new BoundingBox(imageView.getLayoutX() + rowDir,
        imageView.getLayoutY() + columnDir,
        b.getWidth(), b.getHeight());
    return new Foe(sprite, boundingBox);
  }
}
