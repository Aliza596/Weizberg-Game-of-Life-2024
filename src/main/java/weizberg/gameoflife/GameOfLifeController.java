package weizberg.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class GameOfLifeController {

    private final Grid model;
    private final GridComponent view;
    private final RleParser rleParser;
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.nextGen();
            view.repaint();
        }
    });

    public GameOfLifeController(Grid model, GridComponent view, RleParser rleParser) {
        this.model = model;
        this.view = view;
        this.rleParser = rleParser;
    }

    public GameOfLifeController(Grid model, GridComponent view) {
        this.model = model;
        this.view = view;
        this.rleParser = new RleParser();
    }

    public void pasteText() {
        paste(openCopiedText());
    }
    public void paste(String clipboardContents) {
        RleParser rleParser = new RleParser();
        int[][] rleField = rleParser.rleToField(clipboardContents);
        model.setField(rleField);
        view.repaint();
    }

    public String openCopiedText() {
        String clipboardText = "";
        try {
            clipboardText = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            throw new RuntimeException(e);
        }
        return clipboardText;
    }

    public void toggleCell(int screenX, int screenY) {
        int gridX = screenX / view.getCellSize();
        int gridY = screenY / view.getCellSize();

        if (model.isAlive(gridX, gridY)) {
            model.makeDead(gridX, gridY);
        } else {
            model.makeAlive(gridX, gridY);
        }

        view.repaint();

    }

    public void timerOn() {
        timer.start();
    }

    public void timerOff() {
        timer.stop();
    }

}
