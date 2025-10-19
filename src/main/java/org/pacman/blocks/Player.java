package org.pacman.blocks;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import org.pacman.Sprites;

import static org.pacman.data.Field.getWalls;
import static org.pacman.data.Properties.getGridConfig;

public class Player extends Moving {

  public Player(Sprites sprite, int row, int column) {
    super(sprite, row, column);
  }

  public Player(Sprites sprite, BoundingBox boundingBox) {
    super(sprite, boundingBox);
    setPosition();
  }

  @Override
  public void move(Direction direction) {
    switch (direction) {
      case LEFT: {
        if (getMovedBoundingBox(0, -8).isColliding(getWalls())) break;
        column -= getGridConfig().quarterTileSize();
        if (column < getGridConfig().tileSize()/2) { column = getGridConfig().boardWidth() - getGridConfig().tileSize()/2; }
        setNewImage(Sprites.PACMAN_LEFT.getImage());
        break;
      }
      case RIGHT: {
        if (getMovedBoundingBox(0, 8).isColliding(getWalls())) break;
        column += getGridConfig().quarterTileSize();
        if (column >= getGridConfig().boardWidth() - getGridConfig().tileSize()/2) { column = -getGridConfig().tileSize()/2; }
        setNewImage(Sprites.PACMAN_RIGHT.getImage());
        break;
      }
      case UP: {
        if (getMovedBoundingBox(-8, 0).isColliding(getWalls())) break;
        row -= getGridConfig().quarterTileSize();
        if (row < getGridConfig().tileSize()/2) { row = getGridConfig().boardHeight() - getGridConfig().tileSize()/2; }
        setNewImage(Sprites.PACMAN_UP.getImage());
        break;
      }
      case DOWN: {
        if (getMovedBoundingBox(8, 0).isColliding(getWalls())) break;
        row += getGridConfig().quarterTileSize();
        if (row >= getGridConfig().boardHeight() - getGridConfig().tileSize()/2) { row = -getGridConfig().tileSize()/2; }
        setNewImage(Sprites.PACMAN_DOWN.getImage());
        break;
      }
      default:
    }
    setPosition();
  }

  private Player getMovedBoundingBox(int columnDir, int rowDir) {
    Bounds b = imageView.getLayoutBounds();
    BoundingBox boundingBox = new BoundingBox(imageView.getLayoutX() + rowDir,
        imageView.getLayoutY() + columnDir,
        b.getWidth(), b.getHeight());
    return new Player(Sprites.PACMAN_DOWN, boundingBox);
  }
}
