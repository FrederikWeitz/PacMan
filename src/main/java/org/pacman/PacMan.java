package org.pacman;

import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.pacman.blocks.*;

import java.util.ArrayList;
import java.util.List;

import static org.pacman.data.Field.*;
import static org.pacman.data.Properties.*;

public class PacMan {

  Pane root;

  //X = wall, O = skip, P = pac man, ' ' = food
  //Ghosts: b = blue, o = orange, p = pink, r = red
  private final String[] tileMap = {
      "XXXXXXXXXXXXXXXXXXX",
      "X        X        X",
      "X XX XXX X XXX XX X",
      "X                 X",
      "X XX X XXXXX X XX X",
      "X    X       X    X",
      "XXXX XXXX XXXX XXXX",
      "OOOX X       X XOOO",
      "XXXX X XXrXX X XXXX",
      "O       bpo       O",
      "XXXX X XXXXX X XXXX",
      "OOOX X       X XOOO",
      "XXXX X XXXXX X XXXX",
      "X        X        X",
      "X XX XXX X XXX XX X",
      "X  X     P     X  X",
      "XX X X XXXXX X X XX",
      "X    X   X   X    X",
      "X XXXXXX X XXXXXX X",
      "X                 X",
      "XXXXXXXXXXXXXXXXXXX"
  };

  public PacMan() {
    root = new Pane();

    loadMap(tileMap);
    setNewGridConfiguration();

    root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
      KeyCode keyCode = e.getCode();
      switch (keyCode) {
        case UP: {
          getPacman().move(Direction.UP);
          break;
        }
        case DOWN: {
          getPacman().move(Direction.DOWN);
          break;
        }
        case LEFT: {
          getPacman().move(Direction.LEFT);
          break;
        }
        case RIGHT: {
          getPacman().move(Direction.RIGHT);
          break;
        }
        default:
      }
      e.consume(); // Event nicht weiterreichen
    });

    startTicker();
  }

  public Pane getRoot() {
    return root;
  }

  public void setNewGridConfiguration() {
    root.setPrefSize(getGridConfig().boardWidth(), getGridConfig().boardHeight());
    setBackground();
    root.setFocusTraversable(true);
    root.requestFocus();
    root.setOnMouseClicked(ev -> root.requestFocus());
  }

  private void setBackground() {
    root.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0.0), new Insets(0.0))));
  }

  private void loadMap(String[] tempTileMap) {
    setGridConfig(tempTileMap.length, tempTileMap[0].length(), 32);

    for (int row = 0; row < getGridConfig().rowCount(); row++) {
      for (int column = 0; column < getGridConfig().columnCount(); column++) {
        char c = tileMap[row].charAt(column);

        //X = wall, O = skip, P = pac man, ' ' = food
        //Ghosts: b = blue, o = orange, p = pink, r = red

        switch (c) {
          case 'X': {
            Fixed wall = new Fixed(Sprites.WALL, row, column);
            getWalls().add(wall);
            root.getChildren().add(wall.getImageView());
            break;
          }
          case ' ': {
            Fixed food = new Fixed(Sprites.FOOD, row, column);
            getFoods().add(food);
            root.getChildren().add(food.getImageView());
            break;
          }
          case 'P': {
            setPacman(new Player(Sprites.PACMAN_RIGHT, row, column));
            root.getChildren().add(getPacman().getImageView());
            break;
          }
          case 'b': {
            Foe ghost_blue = new Foe(Sprites.BLUE_GHOST, row, column);
            getGhosts().add(ghost_blue);
            root.getChildren().add(ghost_blue.getImageView());
            break;
          }
          case 'o': {
            Foe ghost_orange = new Foe(Sprites.ORANGE_GHOST, row, column);
            getGhosts().add(ghost_orange);
            root.getChildren().add(ghost_orange.getImageView());
            break;
          }
          case 'p': {
            Foe ghost_pink = new Foe(Sprites.PINK_GHOST, row, column);
            getGhosts().add(ghost_pink);
            root.getChildren().add(ghost_pink.getImageView());
            break;
          }
          case 'r': {
            Foe ghost_red = new Foe(Sprites.RED_GHOST, row, column);
            getGhosts().add(ghost_red);
            root.getChildren().add(ghost_red.getImageView());
            break;
          }
          case 'O':
          default:
        }
      }
    }
  }
}
