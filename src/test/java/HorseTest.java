import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;


import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;


public class HorseTest {

    @Test
    public void NullParameter() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 2));
    }

    @Test
    public void NullParameterGetMessage() {
        try {
            new Horse(null, 1, 2);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t\t\t", "\n\n\n"})
    public void isBlankException(String string) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(string, 1, 2));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t\t\t", "\n\n\n"})
    public void isBlankExceptionMessage(String string) {
        try {
            new Horse(string, 1, 2);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    public void isNegativeSpeed() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", -1, 2));
    }

    @Test
    public void isNegativeSpeedMessage() {
        try {
            new Horse("Horse", -1, 2);
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void isNegativeDistance() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", 1, -2));
    }

    @Test
    public void isNegativeDistanceMessage() {
        try {
            new Horse("Horse", 1, -2);
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Horse", 1, 2);

        Field fieldName = Horse.class.getDeclaredField("name");
        fieldName.setAccessible(true);

        String name = (String) fieldName.get(horse);

        assertEquals("Horse", name);
    }

    @Test
    public void getSpeed() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Horse", 25, 50);

        Field fieldSpeed = Horse.class.getDeclaredField("speed");
        fieldSpeed.setAccessible(true);

        Double speed = (Double) fieldSpeed.get(horse);

        assertEquals(25, speed);
    }

    @Test
    public void getDistance() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Horse", 25, 50);
        Horse horse1 = new Horse("Horse 1", 12);

        Field fieldDistance = Horse.class.getDeclaredField("distance");
        fieldDistance.setAccessible(true);

        Double distance = (Double) fieldDistance.get(horse);

        assertEquals(50, distance);

        Double distance1 = (Double) fieldDistance.get(horse1);
        assertEquals(0, distance1);
    }

    @Test
    public void move() {
        try (MockedStatic<Horse> horseStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Horse", 25, 50);
            horse.move();

            horseStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5, 0.7, 0.9, 1.2, 1.5, 1.7})
    public void move2(double number) {
        try (MockedStatic<Horse> horseStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Horse", 25, 50);
            horseStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(number);

            horse.move();

            assertEquals(50 + 25 * number, horse.getDistance());
        }
    }
}