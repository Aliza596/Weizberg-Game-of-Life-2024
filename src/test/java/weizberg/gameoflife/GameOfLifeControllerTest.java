package weizberg.gameoflife;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;

public class GameOfLifeControllerTest {

    @Test
    void toggleCellOn() {
        //given
        Grid model = mock();
        GridComponent view = mock();
        GameOfLifeController controller = new GameOfLifeController(model, view);
        doReturn(10).when(view).getCellSize();
        doReturn(10).when(model).getWidth();
        doReturn(10).when(model).getHeight();

        //when
        controller.toggleCell(50, 100);

        //then
        verify(model).makeAlive(5, 10);
        verify(view).repaint();
    }

    @Test
    void toggleCellOff() {
        //given
        Grid model = mock();
        GridComponent view = mock();
        GameOfLifeController controller = new GameOfLifeController(model, view);
        doReturn(10).when(view).getCellSize();
        doReturn(10).when(model).getWidth();
        doReturn(10).when(model).getHeight();
        doReturn(true).when(model).isAlive(5, 10);

        //when
        controller.toggleCell(50, 100);

        //then
        verify(model).makeDead(5, 10);
        verify(view).repaint();
    }


    @Test
    public void pasteUrl() throws IOException {

        //Given
        Grid model = mock();
        GridComponent view = mock();
        RleParser rleParser = mock();
        GameOfLifeController controller = new GameOfLifeController(model, view, rleParser);

        int[][] mockedField = new int[100][100];
        mockedField[49][50] = 1;
        mockedField[50][51] = 1;
        mockedField[51][49] = 1;
        mockedField[51][50] = 1;
        mockedField[51][51] = 1;

        //When
        String url = "https://conwaylife.com/patterns/glider.rle";
        when(rleParser.rleToField(anyString())).thenReturn(mockedField);

        InputStream in = new URL(url).openStream();
        String contentOfUrl = IOUtils.toString(in);

        controller.paste(contentOfUrl);

        // Then
        verify(model).setField(argThat(field -> {
            for (int y = 0; y < field.length; y++) {
                for (int x = 0; x < field[y].length; x++) {
                    if ((y == 49 && x == 50) ||
                            (y == 50 && x == 51) ||
                            (y == 51 && x == 49) ||
                            (y == 51 && x == 50) ||
                            (y == 51 && x == 51)) {
                        if (field[y][x] != 1) {
                            return false;
                        }
                    } else {
                        if (field[y][x] != 0) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }));

        verify(view).repaint();
    }

    @Test
    public void pasteRle()
    {
        //Given
        Grid model = mock();
        GridComponent view = mock();
        RleParser rleParser = mock();
        GameOfLifeController controller = new GameOfLifeController(model, view, rleParser);

        int[][] mockedField = new int[100][100];
        mockedField[49][50] = 1;
        mockedField[50][51] = 1;
        mockedField[51][49] = 1;
        mockedField[51][50] = 1;
        mockedField[51][51] = 1;

        //When
        String content = """
                #N Glider
                #O Richard K. Guy
                #C The smallest, most common, and first discovered spaceship. Diagonal, has period 4 and speed c/4.
                #C www.conwaylife.com/wiki/index.php?title=Glider
                x = 3, y = 3, rule = B3/S23
                bob$2bo$3o!
                """;
        controller.paste(content);

        //Then
        verify(model).setField(argThat(field -> {
            for (int y = 0; y < field.length; y++) {
                for (int x = 0; x < field[y].length; x++) {
                    if ((y == 49 && x == 50) ||
                            (y == 50 && x == 51) ||
                            (y == 51 && x == 49) ||
                            (y == 51 && x == 50) ||
                            (y == 51 && x == 51)) {
                        if (field[y][x] != 1) {
                            return false;
                        }
                    } else {
                        if (field[y][x] != 0) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }));

        verify(view).repaint();
    }



    @Test
    public void pasteFileName() throws URISyntaxException {

        //Given
        Grid model = mock();
        GridComponent view = mock();
        RleParser rleParser = mock();
        GameOfLifeController controller = new GameOfLifeController(model, view, rleParser);

        int[][] mockedField = new int[100][100];
        mockedField[49][50] = 1;
        mockedField[50][51] = 1;
        mockedField[51][49] = 1;
        mockedField[51][50] = 1;
        mockedField[51][51] = 1;

        //When
        File file = new File(getClass().getClassLoader().getResource("gliderFile.rle").toURI());
        controller.paste(file.getAbsolutePath());

        // Then
        verify(model).setField(argThat(field -> {
            for (int y = 0; y < field.length; y++) {
                for (int x = 0; x < field[y].length; x++) {
                    if ((y == 49 && x == 50) ||
                            (y == 50 && x == 51) ||
                            (y == 51 && x == 49) ||
                            (y == 51 && x == 50) ||
                            (y == 51 && x == 51)) {
                        if (field[y][x] != 1) {
                            return false;
                        }
                    } else {
                        if (field[y][x] != 0) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }));

        verify(view).repaint();
    }

}
