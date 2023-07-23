import org.junit.jupiter.api.Test;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {

    @Test
    public void NullParameter() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void NullParameterGetMessage() {
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }

    @Test
    public void ListIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    public void ListIsEmptyGetMessage() {
        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void getHorses() throws NoSuchFieldException, IllegalAccessException {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            horses.add(new Horse("Лошадь " + i,i+1));
        }
       Hippodrome hippodrome =  new Hippodrome(horses);

        Field field = Hippodrome.class.getDeclaredField("horses");
        field.setAccessible(true);
        List<Horse> horsesClass = (List<Horse>) field.get(hippodrome);

        assertTrue(horses.equals(horsesClass));
    }

    @Test
    public void move(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            horses.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for (Horse horse:horses) {
            verify(horse).move();
        }
    }

    @Test
    public void getWinner(){
        List<Horse> horses = new ArrayList<>();
        horses.add(new Horse("Horse1", 1, 5));
        horses.add(new Horse("Horse2", 1, 10));
        horses.add(new Horse("Horse3", 1, 15));
        horses.add(new Horse("Horse4", 1, 20));
        horses.add(new Horse("Horse5", 1, 25));

        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses.get(4), hippodrome.getWinner());
    }
}
