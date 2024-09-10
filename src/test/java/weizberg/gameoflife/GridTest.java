package weizberg.gameoflife;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GridTest {

    @Test
    public void string() {
        //given
        Grid grid = new Grid(3, 3);

        //when
        String actual = grid.toString();

        //then
        assertEquals("000\n000\n000\n", actual);
    }

    @Test
    public void put() {
        //given
        Grid grid = new Grid(3, 3);

        //when
        grid.makeAlive(1, 0);

        //then
        assertEquals("010\n000\n000\n", grid.toString());
    }

    @Test
    public void nextGen() {
        //given
        Grid grid = new Grid(3, 3);

        //when
        grid.makeAlive(0, 0);
        grid.makeAlive(2, 0);
        grid.makeAlive(1, 1);
        grid.makeAlive(0, 2);
        grid.makeAlive(1, 2);
        grid.nextGen();

        //then
        assertEquals("010\n001\n110\n", grid.toString());
    }
}
