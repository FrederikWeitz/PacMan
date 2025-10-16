package org.pacman;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static org.pacman.Properties.getGridConfig;

public class PacMan {

  StackPane root;

  public PacMan() {
    root = new StackPane();
    setNewGridConfiguration();
  }

  public StackPane getRoot() {
    return root;
  }

  public void setNewGridConfiguration() {
    root.setPrefSize(getGridConfig().boardWidth(), getGridConfig().boardHeight());
    setBackground();
  }

  private void setBackground() {
    root.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0.0), new Insets(0.0))));
  }

}
