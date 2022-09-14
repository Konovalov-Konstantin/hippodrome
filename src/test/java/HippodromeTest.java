import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HippodromeTest {

    @Test
    void shouldThrowExceptionIfArgumentIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
    }

    @Test
    void shouldReturnMessageIfArgumentIsNull() {
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionIfListIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    void shouldReturnMessageIfListIsEmpty() {
        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    @Test
    void checkHorseArraysEquals() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horseList.add(i, new Horse("horse " + i, Math.random() * (20.0 - 10.0) + 10.0));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);
        assertArrayEquals(horseList.toArray(), hippodrome.getHorses().toArray());
    }



    @Test
    void move() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horseList.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);
        hippodrome.move();

        for (Horse horse : horseList) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void chaeckWinnerHorse() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horseList.add(i, new Horse("horse " + i, Math.random() * (20.0 - 10.0) + 10.0));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);
        Horse winner = horseList.stream().max(Comparator.comparing(Horse::getDistance)).get();
        assertSame(winner, hippodrome.getWinner());
    }
}