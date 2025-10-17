package org.pacman;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public enum Sprites {
  BLUE_GHOST("/sprites/blueGhost.png"),
  ORANGE_GHOST("/sprites/orangeGhost.png"),
  PINK_GHOST("/sprites/pinkGhost.png"),
  RED_GHOST("/sprites/redGhost.png"),
  SCARED_GHOST("/sprites/scaredGhost.png"),
  CHERRY("/sprites/cherry.png"),
  CHERRY2("/sprites/cherry2.png"),
  POWER_FOOD("/sprites/powerFood.png"),
  FOOD("/sprites/food.png"),
  PACMAN_DOWN("/sprites/pacmanDown.png"),
  PACMAN_LEFT("/sprites/pacmanLeft.png"),
  PACMAN_RIGHT("/sprites/pacmanRight.png"),
  PACMAN_UP("/sprites/pacmanUp.png"),
  WALL("/sprites/wall.png");

  private final String filePath; // Feld zum Speichern des Dateipfads

  // Konstruktor der Enum
  Sprites(String filePath) {
    this.filePath = filePath;
  }

  /**
   * Lädt das Icon als JavaFX Image-Objekt.
   * Wenn das Bild nicht geladen werden kann (z.B. Pfad falsch),
   * wird ein Default-Icon zurückgegeben, um Fehler zu vermeiden.
   *
   * @return Ein JavaFX Image-Objekt des Icons.
   */
  public Image getImage() {
    try {
      // getClass().getResourceAsStream() ist der bevorzugte Weg, Ressourcen zu laden,
      // da es plattformunabhängig ist und auch innerhalb von JAR-Dateien funktioniert.
      Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(filePath)));
      if (image.isError()) {
        System.err.println("Fehler beim Laden des Icons: " + filePath + ". Lade Default-Icon.");
        return null;
      }
      return image;
    } catch (Exception e) {
      System.err.println("Ausnahme beim Laden des Icons: " + filePath + " - " + e.getMessage());
      return null;
    }
  }

  /**
   * Erstellt ein JavaFX ImageView-Objekt für das Icon.
   * Die Größe des ImageView kann optional festgelegt werden.
   *
   * @param size Die gewünschte Breite und Höhe des Icons in Pixeln.
   * Wenn 0 oder negativ, wird die Originalgröße verwendet.
   * @return Ein JavaFX ImageView-Objekt des Icons.
   */
  public ImageView getImageView(double size) {
    ImageView imageView = new ImageView(getImage());
    if (size > 0) {
      imageView.setFitWidth(size);
      imageView.setFitHeight(size);
      imageView.setPreserveRatio(true); // Seitenverhältnis beibehalten
    }
    return imageView;
  }

  /**
   * Alternativer Parameter als Ganzzahl
   *
   * @param size Die gewünschte Breite und Höhe des Icons in Pixeln.
   * @return Ein JavaFX ImageView-Objekt des Icons.
   */
  public ImageView getImageView(int size) {
    return getImageView((double) size); // Ruft die Methode mit Größe 0 auf
  }

  /**
   * Erstellt ein JavaFX ImageView-Objekt in Originalgröße für das Icon.
   * @return Ein JavaFX ImageView-Objekt des Icons in Originalgröße.
   */
  public ImageView getImageView() {
    return getImageView(0.0); // Ruft die Methode mit Größe 0 auf
  }
}
