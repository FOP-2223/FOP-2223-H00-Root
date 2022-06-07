package h00;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;

import java.lang.reflect.Executable;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static fopbot.Direction.*;

/**
 * Code für Übungsblatt 00
 *
 * @author Ruben Deisenroth
 */
public class Main {

    public static void main(String[] args) {
        final int worldSize = 5;
        World.setSize(worldSize, worldSize);
        World.setDelay(50);
        World.setVisible(true);
        Robot robby = new Robot(4, 0, DOWN, 12);
        doExercise(robby);
    }

    /**
     * Bewegt den Roboter entsprechend den Anforderungen von Übungsblatt 00
     *
     * @param robby der Roboter, der bewegt werden soll
     */
    public static void doExercise(Robot robby) {
        // TODO H00 Implement your solution here:
        Stream.<Runnable>of(() -> IntStream.range(1, World.getHeight()).forEach(i -> Stream.<Runnable>of(() -> Stream.generate(robby::getDirection).takeWhile(d -> d.ordinal() != 0).forEach(d -> robby.turnLeft()), robby::move, robby::putCoin).forEach(Runnable::run)), robby::turnLeft, () -> Stream.generate(robby::isFrontClear).takeWhile(x -> x).forEach((i) -> Stream.<Runnable>of(() -> Stream.generate(robby::getDirection).takeWhile(d -> d.ordinal() != 3).forEach(d -> robby.turnLeft()),robby::move,robby::putCoin,() -> Stream.generate(robby::getDirection).takeWhile(d -> d.ordinal() != 2).forEach(d -> robby.turnLeft()),robby::move,robby::putCoin).forEach(Runnable::run)),() -> Stream.generate(robby::getDirection).takeWhile(d -> d.ordinal() != 3).forEach(d -> robby.turnLeft())).forEach(Runnable::run); // Zeile ist trivial
    }
}
