package org.pacman.blocks;

import org.pacman.Sprites;

import java.util.List;

public abstract class Moving extends Block {

  Direction direction;

  public Moving(Sprites sprite, int row, int column) {
    super(sprite, row, column);
    setPosition();
    direction = Direction.RIGHT;
  }

  public boolean willCollide(List<? extends Block> blocks) {
    return direction.willCollide(this, blocks);
  }

  public boolean willCollide(Block block) {
    return direction.willCollide(this, block);
  }
}
