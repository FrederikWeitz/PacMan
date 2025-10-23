package org.pacman.data;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.pacman.blocks.Foe;

import static org.pacman.data.Field.*;

public class Properties {

  private static GridConfig gridConfig = new GridConfig(21, 19, 32);

  public static GridConfig getGridConfig() { return gridConfig; }
  public static void setGridConfig(int rowCount, int columnCount, int tileSize) {
    gridConfig = new GridConfig(rowCount, columnCount, tileSize);
  }

  private static final Timeline ticker = new Timeline(
      new KeyFrame(Duration.millis(80), _ -> {
        getGhosts().forEach(Foe::move);
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
