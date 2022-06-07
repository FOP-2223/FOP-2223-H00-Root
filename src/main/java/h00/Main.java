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

        // Zunächst drehen wir uns nach oben
        while (robby.getDirection() != UP) {
            robby.turnLeft();
        }
        // Dann laufen wir nach oben und legen nach jedem schritt eine Münze ab
        for (int i = 0; i < World.getHeight() - 1; i++) {
            robby.move();
            robby.putCoin();
        }
        // nach links schauen
        robby.turnLeft();

        // jetzt gehen wir stufenweise nach Unten links
        for (int i = 0; i < 4; i++) {
            // Schritt nach Links + Münze legen
            robby.move();
            robby.putCoin();

            // Nach unten schauen
            robby.turnLeft();

            // Schritt nach Unten
            robby.move();
            robby.putCoin();

            // Rechtsdrehung
            robby.turnLeft();
            robby.turnLeft();
            robby.turnLeft();
        }
    }
}
