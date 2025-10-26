package org.pacman.blocks;

import javafx.geometry.Bounds;

import java.util.List;
import java.util.Random;

import static org.pacman.data.Properties.getGridConfig;

public enum Direction {
  UP(new DirectionVector(0, -1)),
  DOWN(new DirectionVector(0, 1)),
  LEFT(new DirectionVector(-1, 0)),
  RIGHT(new DirectionVector(1, 0));

  private final DirectionVector directionVector;

  Direction(DirectionVector directionVector) {
    this.directionVector = directionVector;
  }

  public Direction getRandomDirectionExclusively() {
    Random rand = new Random();
    Direction direction = Direction.values()[rand.nextInt(values().length)];
    while (direction == this) {
      direction = Direction.values()[rand.nextInt(values().length)];
    }
    return direction;
  }

  public Bounds getMovedBoundingBox(Block block) {
    return directionVector.getMovedBounds(block.getImageView(), getGridConfig().quarterTileSize());
  }

  public boolean willCollide(Block mainBlock, List<? extends Block> blocks) {
    Bounds p = getMovedBoundingBox(mainBlock);
    for (Block block : blocks) {
      Bounds b = block.getImageView().localToScene(block.getImageView().getBoundsInLocal());
      if (intersectsWithTolerance(p, b, 1.0)) {
        return true;
      }
    }
    return false;
  }

  public boolean willCollide(Block mainBlock, Block otherBlock) {
    Bounds p = getMovedBoundingBox(mainBlock);
    Bounds b = otherBlock.getImageView().localToScene(otherBlock.getImageView().getBoundsInLocal());
    return intersectsWithTolerance(p, b, 1.0);
  }

  private boolean intersectsWithTolerance(Bounds p, Bounds b, double tolerance) {
    double dx = Math.min(p.getMaxX(), b.getMaxX()) - Math.max(p.getMinX(), b.getMinX());
    double dy = Math.min(p.getMaxY(), b.getMaxY()) - Math.max(p.getMinY(), b.getMinY());
    return (dx > tolerance && dy > tolerance);
  }
}
