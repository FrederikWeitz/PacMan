package org.pacman;

public class Properties {

  private static GridConfig gridConfig = new GridConfig(21, 19, 32);

  public static GridConfig getGridConfig() { return gridConfig; }
  public static void setGridConfig(int rowCount, int columnCount, int tileSize) {
    gridConfig = new GridConfig(rowCount, columnCount, tileSize);
  }


}
