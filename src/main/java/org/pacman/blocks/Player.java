package org.pacman.blocks;

import org.pacman.Sprites;
import static org.pacman.Properties.getGridConfig;

public class Player extends Moving {

  public Player(Sprites sprite, int row, int column) {
    super(sprite, row, column);
  }

  @Override
  public void move(Direction direction) {
    switch (direction) {
      case LEFT: {
        column -= getGridConfig().tileSize()/4;
        if (column < getGridConfig().tileSize()/2) { column = getGridConfig().boardWidth() - getGridConfig().tileSize()/2; }
        setNewImage(Sprites.PACMAN_LEFT.getImage());
        break;
      }
      case RIGHT: {
        column += getGridConfig().tileSize()/4;
        if (column >= getGridConfig().boardWidth() - getGridConfig().tileSize()/2) { column = -getGridConfig().tileSize()/2; }
        setNewImage(Sprites.PACMAN_RIGHT.getImage());
        break;
      }
      case UP: {
        row -= getGridConfig().tileSize()/4;
        if (row < getGridConfig().tileSize()/2) { row = getGridConfig().boardHeight() - getGridConfig().tileSize()/2; }
        setNewImage(Sprites.PACMAN_UP.getImage());
        break;
      }
      case DOWN: {
        row += getGridConfig().tileSize()/4;
        if (row >= getGridConfig().boardHeight() - getGridConfig().tileSize()/2) { row = -getGridConfig().tileSize()/2; }
        setNewImage(Sprites.PACMAN_DOWN.getImage());
        break;
      }
      default:
    }
    setPosition();
  }
}
