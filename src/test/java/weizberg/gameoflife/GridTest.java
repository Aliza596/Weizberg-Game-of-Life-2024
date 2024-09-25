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
        Path p = Paths.get(ClassLoader.getSystemResource("glider.rle").toURI());
        RleParser rleParser = new RleParser(p, 3, 3);

        //when
        Grid grid = rleParser.parse();

        //then
        assertEquals("010\n001\n111\n", grid.toString());
    }
}

