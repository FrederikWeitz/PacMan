package org.pacman.blocks;

import javafx.scene.image.ImageView;
import org.pacman.Sprites;

import static org.pacman.data.Properties.getGridConfig;

public class Block {
  Sprites sprite;
  ImageView imageView;

  int row;
  int column;

  public Block(Sprites sprite, int row, int column) {
    this.sprite = sprite;
    imageView = sprite.getImageView(getGridConfig().tileSize());
    this.row = getGridConfig().getRowPosition(row);
    this.column = getGridConfig().getColumnPosition(column);
  }

  public ImageView getImageView() {
    return imageView;
  }

  void setNewImage(Sprites sprite) {
    this.sprite = sprite;
    imageView.setImage(sprite.getImage());
  }

  void setPosition() {
    imageView.setLayoutX(column);
    imageView.setLayoutY(row);
  }
}
