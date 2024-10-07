package weizberg.gameoflife;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        grid.makeAlive(0, 1);
        grid.makeAlive(1, 1);
        grid.makeAlive(2, 1);
        grid.nextGen();

        //then
        assertEquals("010\n010\n010\n", grid.toString());
    }

    @Test
    public void parse() throws IOException, URISyntaxException {
        //given
        RleParser rleParser = new RleParser();

        //when
        String text = "#C This is a glider.\n x = 3, y = 3 \n bo$2bo$3o!";
        int[][] expectedField = new int[100][100];
        expectedField[49][50] = 1;
        expectedField[50][51] = 1;
        expectedField[51][49] = 1;
        expectedField[51][50] = 1;
        expectedField[51][51] = 1;
        int[][] field = rleParser.parse(text);

        //then
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[y].length; x++) {
                assertEquals(expectedField[y][x], field[y][x]);
            }
        }
    }
}

