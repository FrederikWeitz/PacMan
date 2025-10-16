package org.pacman;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    int rowCount = 21;
    int columnCount = 19;
    int tileSize = 32;
    int boardWidth = tileSize * columnCount;
    int boardHeight = tileSize * rowCount;

    PacMan pacMan = new PacMan();

    Scene scene = new Scene(pacMan.getRoot(), boardWidth, boardHeight);
    stage.setTitle("PacMan");
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();
    stage.setResizable(false);
    stage.setOnCloseRequest(e -> Platform.exit());
  }

  public static void main(String[] args) {
    launch(args);
  }
}
