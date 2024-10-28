package weizberg.gameoflife;

import org.junit.jupiter.api.Test;

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

        //then - need to add mockito
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
        doReturn(1).when(model).isAlive(5, 10);
        //when
        controller.toggleCell(50, 100);

        //then - need to add mockito
        verify(model).makeDead(5, 10);
        verify(view).repaint();
    }

    @Test
    public void pasteRle() {
        Grid model = mock();
        GridComponent view = mock();
        RleParser rleParser = mock();
        GameOfLifeController controller = new GameOfLifeController(model, view);
        String rle = """
                #N Gosper glider gun
                #C This was the first gun discovered.
                #C As its name suggests, it was discovered by Bill Gosper.
                x = 36, y = 9, rule = B3/S23
                24bo$22bobo$12b2o6b2o12b2o$11bo3bo4b2o12b2o$2o8bo5bo3b2o$2o8bo3bob2o4b
                obo$10bo5bo7bo$11bo3bo$12b2o!
                """;

        //when
        controller.paste(rle);

        //then
        verify(rleParser).parse(rleParser.readRlefromString(rle));
        verify(view).repaint();
    }

//    @Test
//    public void pasteUrl() {
//        Grid model = mock();
//        GridComponent view = mock();
//        GameOfLifeController controller = new GameOfLifeController(model, view);
//        String rle = "";
//
//        //when
//        controller.paste(rle);
//
//        //then
//        verify(model).;
//        verify(view).repaint();
//    }

}
