package org.pacman.blocks;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.pacman.Sprites;

import static org.pacman.data.Properties.getGridConfig;

public class Block {
  ImageView imageView;

  int row;
  int column;

  public Block(Sprites sprite, int row, int column) {
    imageView = sprite.getImageView(getGridConfig().tileSize());
    this.row = getGridConfig().getRowPosition(row);
    this.column = getGridConfig().getColumnPosition(column);
  }

  Block(Sprites sprite) {
    imageView = sprite.getImageView(getGridConfig().tileSize());
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
