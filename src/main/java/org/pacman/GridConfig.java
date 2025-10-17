package org.pacman;

public record GridConfig(int rowCount, int columnCount, int tileSize) {

  public GridConfig {
    if (rowCount <= 0 || columnCount <= 0 || tileSize <= 0) {
      throw new IllegalArgumentException("rowCount, columnCount und tileSize müssen > 0 sein.");
    }
  }

  public int boardWidth()  { return tileSize * columnCount; }
  public int boardHeight() { return tileSize * rowCount; }
  public int tileSize() { return tileSize; }
  public int rowCount() { return rowCount; }
  public int columnCount() { return columnCount; }

  public int getRowPosition(int row) {
    if (row < 0 || row >= rowCount) { throw new IndexOutOfBoundsException("row out of bounds: " + row); }
    return row * tileSize;
  }
  public int getColumnPosition(int column) {
    if (column < 0 || column >= columnCount) { throw new IndexOutOfBoundsException("column out of bounds: " + column); }
    return column * tileSize;
  }
}
