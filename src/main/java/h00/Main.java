package h00;

import fopbot.Robot;
import fopbot.World;

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
    public static void main(String... args) {
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
        Robot robby = new Robot(4, 0, DOWN, 12);

        // <solution H4>
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

        // jetzt gehen wir stufenweise nach unten links
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
        // </solution>
    }
}
