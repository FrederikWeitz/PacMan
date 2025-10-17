package org.pacman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Properties {

  private static GridConfig gridConfig = new GridConfig(21, 19, 32);

  public static GridConfig getGridConfig() { return gridConfig; }
  public static void setGridConfig(int rowCount, int columnCount, int tileSize) {
    gridConfig = new GridConfig(rowCount, columnCount, tileSize);
  }

  private static Timeline ticker = new Timeline(
      new KeyFrame(Duration.millis(50), e -> {
        // -> alle 50 ms ausführen
        // doTick();
      })
  );

  public static void startTicker() {
    ticker.setCycleCount(Timeline.INDEFINITE);
    ticker.play();
  }

  public static void stopTicker() {
    ticker.stop();
  }
}
