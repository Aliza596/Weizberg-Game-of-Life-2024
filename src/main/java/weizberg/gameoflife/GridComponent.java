package weizberg.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class GridComponent extends JComponent {
    private Grid grid;
    private int[][] field;
    private Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            grid.nextGen();
            repaint();
        }
    });

    public GridComponent(Grid grid) {
        this.grid = grid;
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int gridX = e.getX() / 20;
                int gridY = e.getY() / 20;

                if (grid.isAlive(gridX, gridY)) {
                    grid.makeDead(gridX, gridY);
                } else {
                    grid.makeAlive(gridX, gridY);
                }
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        field = grid.getField();

        g.setColor(Color.white);

        for (int i = 0; i <= getWidth(); i += 20) {
            g.drawLine(i, 0, i, getHeight());
        }

        for (int i = 0; i <= getHeight(); i += 20) {
            g.drawLine(0, i, getWidth(), i);
        }

        g.setColor(Color.MAGENTA);

        int rows = field.length;
        int cols = field[0].length;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (grid.isAlive(x, y)) {
                    g.fillRect(x * 20, y * 20, 20, 20);
                }
            }
        }
    }

    public void playButtonMethod() {
        timer.start();
    }

    public void pauseButtonMethod() {
        timer.stop();
    }

    public void nextGenMethod() {
        grid.nextGen();
        repaint();
    }

    public void clearButtonMethod() {
        for (int y = 0; y < field.length; y++) {
            Arrays.fill(field[y], 0);
        }
        repaint();
    }

    public void optionsButton(String option) throws IOException, URISyntaxException {
        Path p = null;
        switch (option) {
            case "Glider":
                p = Paths.get(ClassLoader.getSystemResource("glider.rle").toURI());
                break;
            case "Glider gun":
                p = Paths.get(ClassLoader.getSystemResource("gosperGliderGun.rle").toURI());
                break;
            case "Spider":
                p = Paths.get(ClassLoader.getSystemResource("spider.rle").toURI());
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + option);
        }
        RleParser rleParser = new RleParser(p, 300, 400);
        grid = rleParser.parse();
        repaint();
    }
}
