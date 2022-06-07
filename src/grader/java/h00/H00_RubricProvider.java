package h00;

import org.sourcegrade.jagr.api.rubric.*;

@RubricForSubmission("h00")
public class H00_RubricProvider implements RubricProvider {

    @Override
    public Rubric getRubric() {
        return Rubric
            .builder()
            .title("H00")
            .addChildCriteria(
                Criterion
                    .builder()
                    .shortDescription("H4: Erste Schritte mit FOPBot")
                    .addChildCriteria(
                        Criterion
                            .builder()
                            .shortDescription("Der Roboter erreicht die obere rechte Ecke mindestens einmal")
                            .grader(
                                Grader
                                    .testAwareBuilder()
                                    .requirePass(
                                        JUnitTestRef.ofMethod(
                                            () -> TutorTests.class.getDeclaredMethod("testUpperRightCornerReached")
                                        )
                                    )
                                    .pointsFailedMin()
                                    .pointsPassedMax()
                                    .build()
                            )
                            .build(),
                        Criterion
                            .builder()
                            .shortDescription("Die Endposition des Roboters ist immer exakt korrekt.")
                            .grader(
                                Grader
                                    .testAwareBuilder()
                                    .requirePass(
                                        JUnitTestRef.ofMethod(
                                            () -> TutorTests.class.getDeclaredMethod("testEndPositionCorrect")
                                        )
                                    )
                                    .pointsFailedMin()
                                    .pointsPassedMax()
                                    .build()
                            )
                            .build(),
                        Criterion
                            .builder()
                            .shortDescription("Die Bewegungen des Roboters (Position und Blickrichtung) sind vollständig korrekt.")
                            .grader(
                                Grader
                                    .testAwareBuilder()
                                    .requirePass(
                                        JUnitTestRef.ofMethod(
                                            () -> TutorTests.class.getDeclaredMethod("testEndPositionCorrect")
                                        )
                                    )
                                    .pointsFailedMin()
                                    .pointsPassedMax()
                                    .build()
                            )
                            .build(),
                        Criterion
                            .builder()
                            .shortDescription("Die korrekte Anzahl an Münzen wurde an den geforderten Stellen richtig platziert.")
                            .grader(
                                Grader
                                    .testAwareBuilder()
                                    .requirePass(
                                        JUnitTestRef.ofMethod(
                                            () -> TutorTests.class.getDeclaredMethod("testEndPositionCorrect")
                                        )
                                    )
                                    .pointsFailedMin()
                                    .pointsPassedMax()
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
            .build();
    }
}
