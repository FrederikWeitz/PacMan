package org.pacman.blocks;

import java.util.Random;

public enum Direction {
  UP,
  DOWN,
  LEFT,
  RIGHT;

  public Direction getRandomDirectionExclusively() {
    Random rand = new Random();
    Direction direction = Direction.values()[rand.nextInt(values().length)];
    while (direction == this) {
      direction = Direction.values()[rand.nextInt(values().length)];
    }
    return direction;
  }
}
