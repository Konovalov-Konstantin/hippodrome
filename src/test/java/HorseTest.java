import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;

class HorseTest {

    @Test
    void shouldThrowExceptionIfNameArgumentIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 15.2));
    }

    @Test
    void shouldReturnMessageIfNameArgumentIsNull() {
        try {
            new Horse(null, 15.2);
            fail(); // если исключение не выброшено - валим тест
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    ", "\t"})
    void shouldThrowExceptionIfNameArgumentIsEmpty(String argument) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(argument, 15.2));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    ", "\t"})
    void shouldReturnMessageIfNameArgumentIsEmpty(String argument) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(argument, 15.2));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -3, -5})
    void shouldThrowExceptionIfSpeedArgumentIsNegative(int argument) {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Name", argument, 20));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -3, -5})
    void shouldReturnMessageIfSpeedArgumentIsNegative(int argument) {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Name", argument, 20));

        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -3, -5})
    void shouldThrowExceptionIfDistanceArgumentIsNegative(int argument) {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse("Name", 20, argument));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -3, -5})
    void shouldReturnMessageIfDistanceArgumentIsNegative(int argument) {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Name", 20, argument));

        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"firstName", "secondName"})
    void getNameTest(String argument) throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse(argument, 15.2, 20);
        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        assertEquals(argument, name.get(horse));
    }

    @ParameterizedTest
    @ValueSource(doubles = {15.2, 15.3, 15.4})
    void getSpeedTest(double argument) throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Name", argument, 20);
        Field speed = Horse.class.getDeclaredField("speed");
        speed.setAccessible(true);
        assertEquals(argument, speed.get(horse));
    }

    @ParameterizedTest
    @ValueSource(doubles = {20.2, 21.1, 22.0})
    void getDistanceTest(double argument) throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Name", 15.2, argument);
        Field distance = Horse.class.getDeclaredField("distance");
        distance.setAccessible(true);
        assertEquals(argument, distance.get(horse));
    }

    @Test
    void getDistanceTestBy3Params() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Name", 15.2);
        Field distance = Horse.class.getDeclaredField("distance");
        distance.setAccessible(true);
        assertEquals(0.0, distance.get(horse));
    }

    @Test
    void shouldCallGetRandomDoubleMethodWithParams_02_And_09_() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("horseName", 15.2, 31.8).move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {15.0, 20.2, 13.5})
    void checkDistanceValue(double random) {

        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("a", 31, 280);
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            horse.move();
            assertEquals(280 + 31 * random, horse.getDistance());
        }
    }
}
