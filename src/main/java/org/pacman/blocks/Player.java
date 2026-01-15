package org.pacman.blocks;

import org.pacman.Sprites;

import static org.pacman.data.Field.getFoods;
import static org.pacman.data.Field.getWalls;
import static org.pacman.data.Properties.getGridConfig;

public class Player extends Moving {

  public Player(Sprites sprite, int row, int column) {
    super(sprite, row, column);
  }

  public void move(Direction dir) {
    direction = dir;
    if (!willCollide(getWalls())) {
      switch (direction) {
        case LEFT: {
          setNewImage(Sprites.PACMAN_LEFT);
          column -= getGridConfig().quarterTileSize();
          if (column < getGridConfig().tileSize() / 2) {
            column = getGridConfig().boardWidth() - getGridConfig().tileSize() / 2;
          }
          break;
        }
        case RIGHT: {
          setNewImage(Sprites.PACMAN_RIGHT);
          column += getGridConfig().quarterTileSize();
          if (column >= getGridConfig().boardWidth() - getGridConfig().tileSize() / 2) {
            column = -getGridConfig().tileSize() / 2;
          }
          break;
        }
        case UP: {
          setNewImage(Sprites.PACMAN_UP);
          row -= getGridConfig().quarterTileSize();
          if (row < getGridConfig().tileSize() / 2) {
            row = getGridConfig().boardHeight() - getGridConfig().tileSize() / 2;
          }
          break;
        }
        case DOWN: {
          setNewImage(Sprites.PACMAN_DOWN);
          row += getGridConfig().quarterTileSize();
          if (row >= getGridConfig().boardHeight() - getGridConfig().tileSize() / 2) {
            row = -getGridConfig().tileSize() / 2;
          }
          break;
        }
        default:
      }

      // Kollision mit Food
      Food food = direction.intersectsFood(this, getFoods());
      if (food != null) {
        System.out.println("Food eaten!");
        getFoods().remove(food);
        food.isEaten();
      }
      setPosition();
    }
  }
}
