package org.pacman;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static org.pacman.data.Properties.stopTicker;

public class HelloApplication extends Application {
  @Override
  public void start(Stage stage) {

    PacMan pacMan = new PacMan();

    Scene scene = new Scene(pacMan.getRoot());
    stage.setTitle("PacMan");
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();
    stage.setResizable(false);
    stage.setOnCloseRequest(_ -> {
      stopTicker();
      Platform.exit();
    });
  }

  public static void main(String[] args) {
    launch(args);
  }
}
