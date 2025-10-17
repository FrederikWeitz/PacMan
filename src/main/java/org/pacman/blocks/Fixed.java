package org.pacman.blocks;

import org.pacman.Sprites;

public class Fixed extends Block{

  public Fixed(Sprites sprite, int row, int column) {
    super(sprite, row, column);
    setPosition();
  }
}
