package org.pacman.blocks;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.pacman.Sprites;

import static org.pacman.Properties.getGridConfig;

public class Block {
  ImageView imageView;

  int row;
  int column;

  public Block(Sprites sprite, int row, int column) {
    imageView = sprite.getImageView(32);
    this.row = getGridConfig().getRowPosition(row);
    this.column = getGridConfig().getColumnPosition(column);
  }

  public ImageView getImageView() {
    return imageView;
  }

  public void setNewImage(Image image) {
    imageView.setImage(image);
  }

  public void setPosition() {
    imageView.setLayoutX(this.column);
    imageView.setLayoutY(this.row);
  }
}
