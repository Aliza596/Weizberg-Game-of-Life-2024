package weizberg.gameoflife;

import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class GameOfLifeController {

    private final Grid model;
    private final GridComponent view;
    private final RleParser rleParser;
    private final Timer timer = new Timer(1000, new ActionListener() {
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
        paste(getPastedText());
    }

    public void paste(String clipboardContents) {
        int[][] rleField = rleToField(clipboardContents);
        model.setField(rleField);
        view.repaint();
    }

    public int[][] rleToField(String clipboardText) {
        String rle = openPastedText(clipboardText);
        return rleParser.parse(rle);
    }

    public String openPastedText(String clipboardText) {
        Pattern pattern = Pattern.compile("^[#bo0-9].*");
        if (pattern.matcher(clipboardText).find()) {
            return clipboardText;
        } else if (checksIfUrl(clipboardText)) {
            try {
                InputStream in = new URL(clipboardText).openStream();
                return IOUtils.toString(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (checksIfFile(clipboardText)) {
            try {
                return IOUtils.toString(new FileReader(clipboardText));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid paste");
        }

        return "";
    }

    private boolean checksIfUrl(String clipboardText) {
        try {
            new URL(clipboardText).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    private boolean checksIfFile(String clipboardText) {
        Path path = Paths.get(clipboardText);
        return Files.exists(path);
    }


    public String getPastedText() {
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
