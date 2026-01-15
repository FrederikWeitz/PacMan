package org.pacman.blocks;

import org.pacman.Sprites;

import static org.pacman.data.Field.getWalls;
import static org.pacman.data.Properties.getGridConfig;

public class Foe extends Moving {

  int forceDirectionChange = 30;

  public Foe(Sprites sprite, int row, int column) {
    super(sprite, row, column);
    direction = Direction.UP;
  }

  public void move() {
    if (willCollide(getWalls())) {
      direction = direction.getRandomDirectionExclusively();
    } else {
      switch (direction) {
        case LEFT: {
          column -= getGridConfig().quarterTileSize();
          if (column < getGridConfig().tileSize() / 2) {
            column = getGridConfig().boardWidth() - getGridConfig().tileSize() / 2;
          }
          forceDirectionChange--;
          break;
        }
        case RIGHT: {
          column += getGridConfig().quarterTileSize();
          if (column >= getGridConfig().boardWidth() - getGridConfig().tileSize() / 2) {
            column = -getGridConfig().tileSize() / 2;
          }
          forceDirectionChange--;
          break;
        }
        case UP: {
          row -= getGridConfig().quarterTileSize();
          if (row < getGridConfig().tileSize() / 2) {
            row = getGridConfig().boardHeight() - getGridConfig().tileSize() / 2;
          }
          forceDirectionChange--;
          break;
        }
        case DOWN: {
          row += getGridConfig().quarterTileSize();
          if (row >= getGridConfig().boardHeight() - getGridConfig().tileSize() / 2) {
            row = -getGridConfig().tileSize() / 2;
          }
          forceDirectionChange--;
          break;
        }
        default:
      }
    }

    // steht am Ende, weil dann beim nächsten
    if (forceDirectionChange <= 0) {
      direction = direction.getRandomDirectionExclusively();
      forceDirectionChange = (forceDirectionChange <= -10) ? 20 : forceDirectionChange;
    }
    setPosition();
  }
}
