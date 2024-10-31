package weizberg.gameoflife;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class GameOfLifeControllerTest {

    @Test
    void toggleCellOn() {
        //given
        Grid model = mock();
        GridComponent view = mock();
        final GameOfLifeController controller = new GameOfLifeController(model, view);
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
        final GameOfLifeController controller = new GameOfLifeController(model, view);
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
    public void pasteText() {
        Grid model = mock();
        GridComponent view = mock();
        RleParser rleParser = mock();
        GameOfLifeController controller = new GameOfLifeController(model, view, rleParser);

        String text = """
                #N Glider
                #O Richard K. Guy
                #C The smallest, most common, and first discovered spaceship. Diagonal, has period 4 and speed c/4.
                #C www.conwaylife.com/wiki/index.php?title=Glider
                x = 3, y = 3, rule = B3/S23
                bob$2bo$3o!
                """;

        int[][] parsedField = new int[100][100];
        when(rleParser.parse(text)).thenReturn(parsedField);

        controller.paste(text);

        verify(rleParser).parse(text);
        verify(model).setField(parsedField);
        verify(view).repaint();
    }

    @Test
    public void pasteFile() {
        Grid model = mock();
        GridComponent view = mock();
        RleParser rleParser = mock();
        final GameOfLifeController controller = new GameOfLifeController(model, view, rleParser);

        URL resource = getClass().getClassLoader().getResource("gliderFile.rle");
        assertNotNull(resource, "file not found");
        File file = new File(resource.getFile());
        String filePath = file.getAbsolutePath();

        String contentsOfFile = """
                #C This is a glider.\r
                x = 3, y = 3\r
                bo$2bo$3o!""";
        contentsOfFile = contentsOfFile.replace("\r\n", "\n").replace("\r", "\n");

        controller.paste(filePath);

        String openedRle = controller.openPastedText(filePath);
        openedRle = openedRle.replace("\r\n", "\n").replace("\r", "\n");
        assertEquals(contentsOfFile, openedRle);
    }

    @Test
    public void pastUrl() {
        Grid model = mock();
        GridComponent view = mock();
        RleParser rleParser = mock();
        final GameOfLifeController controller = new GameOfLifeController(model, view, rleParser);

        String url = "https://conwaylife.com/patterns/glider.rle";
        String contentsOfUrl = """
                #N Glider\r
                #O Richard K. Guy\r
                #C The smallest, most common, and first discovered spaceship. Diagonal, has period 4 and speed c/4.\r
                #C www.conwaylife.com/wiki/index.php?title=Glider\r
                x = 3, y = 3, rule = B3/S23\r
                bob$2bo$3o!\r
                """;

        contentsOfUrl = contentsOfUrl.replace("\r\n", "\n").replace("\r", "\n").trim();

        String openedUrl = controller.openPastedText(url);
        openedUrl = openedUrl.replace("\r\n", "\n").replace("\r", "\n").trim();

        assertEquals(contentsOfUrl, openedUrl);
    }

}
