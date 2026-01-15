package org.pacman.blocks;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public record DirectionVector(int vertical, int horizontal) {

  public int getVertical(int multiplier) {
    return vertical * multiplier;
  }
  public int getHorizontal(int multiplier) {
    return horizontal * multiplier;
  }

  public Bounds getMovedBounds(ImageView imageView, int multiplier) {
    Bounds b = imageView.getLayoutBounds();
    double x = imageView.getLayoutX() + vertical * multiplier;
    double y = imageView.getLayoutY() + horizontal * multiplier;
    return new BoundingBox(x, y, b.getWidth(), b.getHeight());
  }

}

