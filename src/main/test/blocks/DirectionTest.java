package blocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.pacman.blocks.Block;
import org.pacman.blocks.Direction;

public class DirectionTest {

  // -------------------------------------------------------------------------
  // getRandomDirectionExclusively
  // -------------------------------------------------------------------------

  @Test
  @DisplayName("getRandomDirectionExclusively: darf nie dieselbe Richtung zurückgeben")
  void getRandomDirectionExclusively_neverReturnsSameDirection() {
    for (Direction dir : Direction.values()) {
      for (int i = 0; i < 100; i++) { // 100 Versuche pro Richtung
        Direction random = dir.getRandomDirectionExclusively();
        assertNotNull(random, "Random direction must not be null");
        assertNotEquals(dir, random,
            "Random direction must never be the same as 'this'");
      }
    }
  }

  @Test
  @DisplayName("getRandomDirectionExclusively: Ergebnis ist immer eine der anderen Enum-Konstanten")
  void getRandomDirectionExclusively_returnsOnlyOtherDirections() {
    for (Direction dir : Direction.values()) {
      EnumSet<Direction> allowed = EnumSet.allOf(Direction.class);
      allowed.remove(dir); // alle außer this

      for (int i = 0; i < 200; i++) {
        Direction random = dir.getRandomDirectionExclusively();
        assertTrue(allowed.contains(random),
            () -> "Random direction " + random + " must be in " + allowed);
      }
    }
  }

  // -------------------------------------------------------------------------
  // Private Methode intersectsWithTolerance via Reflection testen
  // (Randfälle, Toleranz = 1.0, Überlappung in x- und y-Richtung)
  // -------------------------------------------------------------------------

  private boolean callIntersectsWithTolerance(Bounds p, Bounds b, double tolerance) {
    try {
      Method m = Direction.class.getDeclaredMethod(
          "intersectsWithTolerance", Bounds.class, Bounds.class, double.class);
      m.setAccessible(true);
      return (boolean) m.invoke(Direction.UP, p, b, tolerance);
    } catch (Exception e) {
      throw new RuntimeException("Reflection call failed", e);
    }
  }

  @Test
  @DisplayName("intersectsWithTolerance: deutliche Überlappung -> true")
  void intersectsWithTolerance_largeOverlap_returnsTrue() {
    Bounds p = new BoundingBox(0, 0, 10, 10);       // [0,10] x [0,10]
    Bounds b = new BoundingBox(5, 5, 10, 10);       // [5,15] x [5,15]
    // Überlappung: x = [5,10] => dx = 5; y = [5,10] => dy = 5
    boolean result = callIntersectsWithTolerance(p, b, 1.0);

    assertTrue(result, "Overlap (dx=5, dy=5) > tolerance(1.0) -> true");
  }

  @Test
  @DisplayName("intersectsWithTolerance: Überlappung genau = Toleranz in x -> false")
  void intersectsWithTolerance_overlapExactlyToleranceInX_returnsFalse() {
    Bounds p = new BoundingBox(0, 0, 10, 10);       // x: [0,10]
    Bounds b = new BoundingBox(-9, 0, 10, 10);      // x: [-9,1]
    // Schnittmenge x: [0,1] => dx = 1.0
    // Schnittmenge y: [0,10] => dy = 10
    boolean result = callIntersectsWithTolerance(p, b, 1.0);

    assertFalse(result, "dx == tolerance (1.0) darf nicht als Kollision gelten");
  }

  @Test
  @DisplayName("intersectsWithTolerance: Überlappung genau = Toleranz in y -> false")
  void intersectsWithTolerance_overlapExactlyToleranceInY_returnsFalse() {
    Bounds p = new BoundingBox(0, 0, 10, 10);       // y: [0,10]
    Bounds b = new BoundingBox(0, -9, 10, 10);      // y: [-9,1]
    // Schnittmenge x: [0,10] => dx = 10
    // Schnittmenge y: [0,1]  => dy = 1.0
    boolean result = callIntersectsWithTolerance(p, b, 1.0);

    assertFalse(result, "dy == tolerance (1.0) darf nicht als Kollision gelten");
  }

  @Test
  @DisplayName("intersectsWithTolerance: keine Überlappung in x-Richtung -> false")
  void intersectsWithTolerance_noHorizontalOverlap_returnsFalse() {
    Bounds p = new BoundingBox(0, 0, 10, 10);       // x: [0,10]
    Bounds b = new BoundingBox(11, 0, 5, 10);       // x: [11,16]
    // dx < 0 -> keine Überlappung
    boolean result = callIntersectsWithTolerance(p, b, 1.0);

    assertFalse(result, "Keine horizontale Überlappung -> false");
  }

  @Test
  @DisplayName("intersectsWithTolerance: keine Überlappung in y-Richtung -> false")
  void intersectsWithTolerance_noVerticalOverlap_returnsFalse() {
    Bounds p = new BoundingBox(0, 0, 10, 10);       // y: [0,10]
    Bounds b = new BoundingBox(0, 11, 10, 5);       // y: [11,16]
    boolean result = callIntersectsWithTolerance(p, b, 1.0);

    assertFalse(result, "Keine vertikale Überlappung -> false");
  }

  @Test
  @DisplayName("intersectsWithTolerance: identische Bounds -> true")
  void intersectsWithTolerance_identicalBounds_returnsTrue() {
    Bounds p = new BoundingBox(0, 0, 10, 10);
    Bounds b = new BoundingBox(0, 0, 10, 10);
    boolean result = callIntersectsWithTolerance(p, b, 1.0);

    assertTrue(result, "Identische Bounds müssen kollidieren");
  }

  @Test
  @DisplayName("intersectsWithTolerance: nur Ecke berührt (dx = 0 oder dy = 0) -> false")
  void intersectsWithTolerance_touchingOnlyAtEdge_returnsFalse() {
    Bounds p = new BoundingBox(0, 0, 10, 10);       // x: [0,10], y: [0,10]
    Bounds b = new BoundingBox(10, 10, 5, 5);       // x: [10,15], y: [10,15]
    // Schnittmenge: x [10,10] => dx = 0; y [10,10] => dy = 0
    boolean result = callIntersectsWithTolerance(p, b, 1.0);

    assertFalse(result, "Nur Punkt-/Kantenberührung darf nicht als Kollision gelten");
  }

  // -------------------------------------------------------------------------
  // Hilfsfunktion: Block-Mock mit vorgegebenen Bounds
  // -------------------------------------------------------------------------

  private Block createBlockMock(Bounds sceneBounds) {
    Block block = mock(Block.class);
    ImageView imageView = mock(ImageView.class);

    when(block.getImageView()).thenReturn(imageView);
    when(imageView.getBoundsInLocal()).thenReturn(sceneBounds);
    // Direction.willCollide ruft localToScene(getBoundsInLocal()) auf:
    when(imageView.localToScene(sceneBounds)).thenReturn(sceneBounds);

    return block;
  }

  // -------------------------------------------------------------------------
  // willCollide(Block mainBlock, List<? extends Block> blocks)
  // Hier wird Direction mittels Spy verwendet, um getMovedBoundingBox zu stubben.
  // -------------------------------------------------------------------------

  @Test
  @DisplayName("willCollide(List): leere Liste -> false")
  void willCollide_withEmptyList_returnsFalse() {
    Direction dir = spy(Direction.UP);

    Bounds moved = new BoundingBox(0, 0, 10, 10);
    Block mainBlock = createBlockMock(new BoundingBox(100, 100, 10, 10)); // egal
    doReturn(moved).when(dir).getMovedBoundingBox(mainBlock);

    boolean result = dir.willCollide(mainBlock, Collections.emptyList());

    assertFalse(result, "Bei leerer Blockliste darf keine Kollision gemeldet werden");
  }

  @Test
  @DisplayName("willCollide(List): kein Block kollidiert -> false")
  void willCollide_withNonCollidingBlocks_returnsFalse() {
    Direction dir = spy(Direction.RIGHT);

    Bounds moved = new BoundingBox(0, 0, 10, 10); // p
    Block mainBlock = createBlockMock(new BoundingBox(0, 0, 10, 10));
    doReturn(moved).when(dir).getMovedBoundingBox(mainBlock);

    // Zwei Blöcke weit weg
    Block b1 = createBlockMock(new BoundingBox(50, 50, 10, 10));
    Block b2 = createBlockMock(new BoundingBox(-30, -30, 5, 5));

    boolean result = dir.willCollide(mainBlock, Arrays.asList(b1, b2));

    assertFalse(result, "Keiner der Blöcke überlappt deutlich -> keine Kollision");
  }

  @Test
  @DisplayName("willCollide(List): erster Block kollidiert -> true und Schleife bricht früh ab")
  void willCollide_firstBlockCollides_returnsTrueAndShortCircuits() {
    Direction dir = spy(Direction.DOWN);

    Bounds moved = new BoundingBox(0, 0, 10, 10); // p
    Block mainBlock = createBlockMock(new BoundingBox(0, 0, 10, 10));
    doReturn(moved).when(dir).getMovedBoundingBox(mainBlock);

    // Erster Block kollidiert
    Block colliding = createBlockMock(new BoundingBox(5, 5, 10, 10));   // deutliche Überlappung
    // Zweiter Block sollte nie abgefragt werden, wenn kurzgeschlossen wird
    Block notReached = createBlockMock(new BoundingBox(100, 100, 10, 10));

    List<Block> blocks = Arrays.asList(colliding, notReached);

    boolean result = dir.willCollide(mainBlock, blocks);

    assertTrue(result, "Wenn der erste Block kollidiert, muss true zurückgegeben werden");

    // Sicherstellen, dass die Bounds des zweiten Blocks nie ausgewertet werden
    ImageView iv2 = notReached.getImageView();
    verify(iv2, never()).localToScene(any(Bounds.class));
  }

  @Test
  @DisplayName("willCollide(List): erster blockt nicht, späterer kollidiert -> true")
  void willCollide_laterBlockCollides_returnsTrue() {
    Direction dir = spy(Direction.LEFT);

    Bounds moved = new BoundingBox(0, 0, 10, 10); // p
    Block mainBlock = createBlockMock(new BoundingBox(0, 0, 10, 10));
    doReturn(moved).when(dir).getMovedBoundingBox(mainBlock);

    // erster Block: neben p
    Block b1 = createBlockMock(new BoundingBox(20, 20, 5, 5));
    // zweiter Block: kollidiert
    Block b2 = createBlockMock(new BoundingBox(2, 2, 10, 10));

    boolean result = dir.willCollide(mainBlock, Arrays.asList(b1, b2));

    assertTrue(result, "Wenn ein späterer Block kollidiert, muss true zurückkommen");
    // Beide ImageViews müssen abgefragt werden
    verify(b1.getImageView(), atLeastOnce()).localToScene(any(Bounds.class));
    verify(b2.getImageView(), atLeastOnce()).localToScene(any(Bounds.class));
  }

  // -------------------------------------------------------------------------
  // willCollide(Block mainBlock, Block otherBlock) – einfache True/False Fälle
  // -------------------------------------------------------------------------

  @Test
  @DisplayName("willCollide(Block): kollidierende Bounds -> true")
  void willCollide_singleBlockCollision_returnsTrue() {
    Direction dir = spy(Direction.UP);

    Bounds moved = new BoundingBox(0, 0, 10, 10); // p
    Block mainBlock = createBlockMock(new BoundingBox(0, 0, 10, 10));
    doReturn(moved).when(dir).getMovedBoundingBox(mainBlock);

    Block other = createBlockMock(new BoundingBox(5, 5, 10, 10)); // klare Überlappung

    boolean result = dir.willCollide(mainBlock, other);

    assertTrue(result, "Deutliche Überlappung -> true");
  }

  @Test
  @DisplayName("willCollide(Block): nur Berührung an der Ecke -> false (dx/dy <= Toleranz)")
  void willCollide_singleBlockTouchingEdge_returnsFalse() {
    Direction dir = spy(Direction.UP);

    Bounds moved = new BoundingBox(0, 0, 10, 10); // p
    Block mainBlock = createBlockMock(new BoundingBox(0, 0, 10, 10));
    doReturn(moved).when(dir).getMovedBoundingBox(mainBlock);

    // anderer Block berührt p nur in der Ecke (dx = dy = 0)
    Block other = createBlockMock(new BoundingBox(10, 10, 5, 5));

    boolean result = dir.willCollide(mainBlock, other);

    assertFalse(result, "Nur Punkt-/Kantenkontakt darf nicht als Kollision gelten");
  }

  @Test
  @DisplayName("willCollide(Block): identische Bounds -> true")
  void willCollide_singleBlockIdenticalBounds_returnsTrue() {
    Direction dir = spy(Direction.DOWN);

    Bounds moved = new BoundingBox(0, 0, 10, 10); // p
    Block mainBlock = createBlockMock(new BoundingBox(0, 0, 10, 10));
    doReturn(moved).when(dir).getMovedBoundingBox(mainBlock);

    // anderer Block exakt gleiche Bounds
    Block other = createBlockMock(new BoundingBox(0, 0, 10, 10));

    boolean result = dir.willCollide(mainBlock, other);

    assertTrue(result, "Identische Bounds müssen als Kollision gelten");
  }

}
