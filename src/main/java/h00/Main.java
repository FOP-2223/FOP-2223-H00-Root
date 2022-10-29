package h00;

import fopbot.Robot;
import fopbot.World;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static fopbot.Direction.DOWN;
import static fopbot.Direction.UP;

/**
 * Code für Übungsblatt 00.
 *
 * @author Ruben Deisenroth
 */
public class Main {

    /**
     * Starts the Program.
     *
     * @param args the Launch arguments
     */
    public static void main(final String... args) {
        // Welt mit Größe 5x5 erstellen
        final int worldSize = 5;
        World.setSize(worldSize, worldSize);

        // Die Schrittdauer auf 200ms setzen
        World.setDelay(200);

        // Die Welt anzeigen
        World.setVisible(true);

        // Den Code der Hausübung ausführen
        doExercise();
    }

    /**
     * Bewegt den Roboter entsprechend den Anforderungen von Übungsblatt 00.
     */
    public static void doExercise() {
        final Robot robby = new Robot(4, 0, DOWN, 12);

        // <solution H4>
        Stream.<Runnable>of(() -> IntStream.range(1, World.getHeight()).forEach(i -> Stream.<Runnable>of(() -> Stream.generate(robby::getDirection).takeWhile(d -> d.ordinal() != 0).forEach(d -> robby.turnLeft()), robby::move, robby::putCoin).forEach(Runnable::run)), robby::turnLeft, () -> Stream.generate(robby::isFrontClear).takeWhile(x -> x).forEach((i) -> Stream.<Runnable>of(() -> Stream.generate(robby::getDirection).takeWhile(d -> d.ordinal() != 3).forEach(d -> robby.turnLeft()),robby::move,robby::putCoin,() -> Stream.generate(robby::getDirection).takeWhile(d -> d.ordinal() != 2).forEach(d -> robby.turnLeft()),robby::move,robby::putCoin).forEach(Runnable::run)),() -> Stream.generate(robby::getDirection).takeWhile(d -> d.ordinal() != 3).forEach(d -> robby.turnLeft())).forEach(Runnable::run); // Zeile ist trivial
        // </solution>
    }
}
