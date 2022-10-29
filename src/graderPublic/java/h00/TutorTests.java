package h00;

import fopbot.Coin;
import fopbot.Direction;
import fopbot.Field;
import fopbot.Robot;
import fopbot.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;

import java.awt.Point;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

import static fopbot.Direction.DOWN;
import static fopbot.Direction.LEFT;
import static fopbot.Direction.RIGHT;
import static fopbot.Direction.UP;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The Tutor Tests for Submission H00.
 *
 * @author Ruben Deisenroth
 */
@TestForSubmission()
public class TutorTests {
    //------------//
    //--Messages--//
    //------------//

    public static final String NO_STATES_MESSAGE = "Der Roboter hat sich nicht bewegt.";
    public static final String WRONG_X_COORDINATE = "Die X-Koordinate des Roboters ist inkorrekt.";
    public static final String WRONG_Y_COORDINATE = "Die Y-Koordinate des Roboters ist inkorrekt.";
    public static final String WRONG_ROBOT_AMOUNT = "Die Anzahl der Roboter auf dem Feld ist inkorrekt.";
    public static final String WRONG_VIEWING_DIRECTION = "Die Blickrichtung des Roboters ist inkorrekt.";
    public static final String WRONG_MOVEMENT_AMOUNT = "Die Anzahl der Bewegungen ist inkorrekt.";

    /**
     * Returns a custom error Message for wrong movement at a given index.
     *
     * @param movementId the Index of the movement
     * @return the message
     */
    public static String getWrongMovementAtMoveMessage(final int movementId) {
        return String.format("Die %s. Bewegung ist inkorrekt: ", movementId + 1);
    }

    /**
     * Returns a custom error Message for wrong coin count at a given position.
     *
     * @param p the position of the coin
     * @return the message
     */
    public static String getWrongCoinCountMessage(@Nullable final Point p) {
        if (p == null) {
            return "Die Anzahl der Münzen ist inkorrekt.";
        }
        return String.format("Die Anzahl der Münzen an Position %s ist inkorrekt.", p);
    }

    //-------------//
    //--Utilities--//
    //-------------//

    /**
     * Prepare the World for testing.
     */
    public static void setupWorld() {
        World.reset();
        World.setSize(5, 5);
        World.setDelay(0);
        World.setVisible(false);
    }

    /**
     * A Movement state of a Robot.
     *
     * @param x the X-Coordinate of the Robot
     * @param y the Y-Coordinate of the Robot
     * @param d the {@link Direction} the robot is facing
     */
    private record MovementState(int x, int y, Direction d) {
    }

    /**
     * Asserts that the actual movement matches the expected Movement with custom error messages.
     *
     * @param expected the expected movement
     * @param actual   the actual movement
     */
    private static void assertMovementEquals(final List<MovementState> expected, final List<MovementState> actual) {
        // length
        assertEquals(expected.size(), actual.size(), WRONG_MOVEMENT_AMOUNT);
        // elements
        for (int i = 0; i < expected.size(); i++) {
            final var expectedState = expected.get(i);
            final var actualState = actual.get(i);
            assertEquals(expectedState.x, actualState.x, getWrongMovementAtMoveMessage(i) + WRONG_X_COORDINATE);
            assertEquals(expectedState.y, actualState.y, getWrongMovementAtMoveMessage(i) + WRONG_Y_COORDINATE);
            assertEquals(expectedState.d, actualState.d, getWrongMovementAtMoveMessage(i) + WRONG_VIEWING_DIRECTION);
        }
    }

    /**
     * Converts a given list of States to a List of {@link MovementState}.
     *
     * @param states the list to convert
     * @return the converted List
     */
    private static List<MovementState> toMovementStates(final List<Field> states) {
        return states.stream().map(x -> {
            final Robot robot = (Robot) x.getEntities().stream().filter(y -> y instanceof Robot).findFirst().orElse(null);
            assertNotNull(robot, WRONG_ROBOT_AMOUNT);
            return new MovementState(robot.getX(), robot.getY(), robot.getDirection());
        }).toList();
    }

    /**
     * Returns the Java-Code to generate a given Movement-State list.
     *
     * @param states the List to generate
     * @return the Java-Code
     */
    @SuppressWarnings("unused")
    public static String getMovementStringListGenerationCode(final List<MovementState> states) {
        return "List.of(\n" + states.stream()
            .map(s -> String.format("\tnew MovementState(%s, %s, %s)", s.x, s.y, s.d))
            .collect(Collectors.joining(",\n")) + "\n);";
    }

    /**
     * Returns the Coin Counts of a given State.
     *
     * @param state the state
     * @return the coin Counts
     */
    public static int[][] getCoinCounts(final Field state) {
        final var result = new int[World.getHeight()][World.getWidth()];
        state.getEntities().stream().filter(Coin.class::isInstance).forEach(c -> result[c.getX()][c.getY()]++);
        return result;
    }

    /**
     * Asserts that the Coin Count arrays match.
     *
     * @param expected the expected coin counts
     * @param actual   the actual Coin Counts
     */
    public static void assertCoinCountsEqual(final int[][] expected, final int[][] actual) {
        for (int x = 0; x < World.getWidth(); x++) {
            for (int y = 0; y < World.getHeight(); y++) {
                assertEquals(expected[y][x], actual[y][x], getWrongCoinCountMessage(new Point(x, y)));
            }
        }
    }

    /**
     * Get the states list of the World.
     *
     * @return the state list
     */
    private List<Field> getStates() {
        final var states = World.getGlobalWorld().getEntityStates();
        assertNotNull(states, NO_STATES_MESSAGE);
        assertFalse(states.isEmpty(), NO_STATES_MESSAGE);
        return states;
    }

    /**
     * Returns the final State of the World.
     *
     * @return the final State of the World
     */
    private Field getFinalState() {
        final var states = getStates();
        return states.get(states.size() - 1);
    }

    //---------//
    //--Tests--//
    //---------//

    @BeforeEach
    public void setup() {
        setupWorld();
        Main.doExercise();
    }

    @Test
    public void testUpperRightCornerReached() {
        final var states = getStates();
        assertTrue(
            states
                .stream()
                .anyMatch(
                    x -> x.getEntities()
                        .stream()
                        .filter(Robot.class::isInstance)
                        .anyMatch(y -> y.getX() == 4 && y.getY() == 4)));
    }

    @Test
    public void testEndPositionCorrect() {
        final var lastState = getFinalState();
        assertTrue(lastState.getEntities().stream()
            .filter(Robot.class::isInstance)
            .anyMatch(x -> x.getX() == 0 && x.getY() == 0));
    }

    @Test
    public void testMovementCorrect() {
        final var movementStates = toMovementStates(getStates());

        /*
         * Generated with System.out.println(getMovementStringListGenerationCode(movementStates));
         */
        final var expectedMovement = List.of(
            // Drehung nach oben
            new MovementState(4, 0, DOWN),
            new MovementState(4, 0, RIGHT),
            // Bewegung nach (4,4)
            new MovementState(4, 0, UP),
            new MovementState(4, 1, UP),
            new MovementState(4, 1, UP),
            new MovementState(4, 1, UP),
            new MovementState(4, 2, UP),
            new MovementState(4, 2, UP),
            new MovementState(4, 2, UP),
            new MovementState(4, 3, UP),
            new MovementState(4, 3, UP),
            new MovementState(4, 3, UP),
            new MovementState(4, 4, UP),
            new MovementState(4, 4, UP),
            new MovementState(4, 4, UP),
            // Stufenartiges Laufen nach (0,0)
            new MovementState(4, 4, LEFT),
            new MovementState(3, 4, LEFT),
            new MovementState(3, 4, LEFT),
            new MovementState(3, 4, LEFT),
            new MovementState(3, 4, DOWN),
            new MovementState(3, 3, DOWN),
            new MovementState(3, 3, DOWN),
            new MovementState(3, 3, DOWN),
            new MovementState(3, 3, RIGHT),
            new MovementState(3, 3, UP),
            new MovementState(3, 3, LEFT),
            new MovementState(2, 3, LEFT),
            new MovementState(2, 3, LEFT),
            new MovementState(2, 3, LEFT),
            new MovementState(2, 3, DOWN),
            new MovementState(2, 2, DOWN),
            new MovementState(2, 2, DOWN),
            new MovementState(2, 2, DOWN),
            new MovementState(2, 2, RIGHT),
            new MovementState(2, 2, UP),
            new MovementState(2, 2, LEFT),
            new MovementState(1, 2, LEFT),
            new MovementState(1, 2, LEFT),
            new MovementState(1, 2, LEFT),
            new MovementState(1, 2, DOWN),
            new MovementState(1, 1, DOWN),
            new MovementState(1, 1, DOWN),
            new MovementState(1, 1, DOWN),
            new MovementState(1, 1, RIGHT),
            new MovementState(1, 1, UP),
            new MovementState(1, 1, LEFT),
            new MovementState(0, 1, LEFT),
            new MovementState(0, 1, LEFT),
            new MovementState(0, 1, LEFT),
            new MovementState(0, 1, DOWN),
            new MovementState(0, 0, DOWN),
            new MovementState(0, 0, DOWN),
            new MovementState(0, 0, DOWN),
            new MovementState(0, 0, RIGHT),
            new MovementState(0, 0, UP),
            new MovementState(0, 0, LEFT)
        );
        assertMovementEquals(expectedMovement, movementStates);
    }

    @Test
    public void testCoinsCorrect() {
        final var actualCoinCounts = getCoinCounts(getFinalState());
        /*
         * Generated with: System.out.println(Arrays.deepToString(actualCoinCounts));
         * Hint: The array here looks mirrored vertically to the visual representation
         */
        final var expectedCoinCounts = new int[][]{
            {1, 1, 0, 0, 0},
            {0, 1, 1, 0, 0},
            {0, 0, 1, 1, 0},
            {0, 0, 0, 1, 1},
            {0, 1, 1, 1, 1}
        };
        assertCoinCountsEqual(expectedCoinCounts, actualCoinCounts);
    }
}
