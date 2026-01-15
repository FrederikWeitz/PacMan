package org.pacman.blocks;

import org.pacman.Sprites;

public class Food extends Fixed implements Eatable{

  public Food(Sprites sprite, int row, int column) {
    super(sprite, row, column);
  }

  @Override
  public boolean isEaten() {
    if (getImageView() != null) {
      getImageView().setVisible(false);
      return true;
    }
    return false;
  }
}
