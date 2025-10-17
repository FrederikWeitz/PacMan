package org.pacman.blocks;

import org.pacman.Sprites;

public abstract class Moving extends Block {

  public Moving(Sprites sprite, int row, int column) {
    super(sprite, row, column);
    setPosition();
  }

  abstract public void move(Direction direction);
}
