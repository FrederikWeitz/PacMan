package org.pacman.data;

import org.pacman.blocks.Fixed;
import org.pacman.blocks.Foe;
import org.pacman.blocks.Food;
import org.pacman.blocks.Player;

import java.util.ArrayList;
import java.util.List;

public class Field {

  private static final List<Fixed> walls = new ArrayList<>();
  private static final List<Food> foods = new ArrayList<>();
  private static final List<Foe> ghosts = new ArrayList<>();
  private static Player pacman = null;

  public static List<Fixed> getWalls() {
    return walls;
  }

  public static List<Food> getFoods() {
    return foods;
  }

  public static List<Foe> getGhosts() {
    return ghosts;
  }

  public static Player getPacman() {
    return pacman;
  }

  public static void setPacman(Player pacman) {
    Field.pacman = pacman;
  }

  public static void clear() {
    walls.clear();
    foods.clear();
    ghosts.clear();
    pacman = null;
  }
}
