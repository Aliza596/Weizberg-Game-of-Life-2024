package weizberg.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

        int cellSize = 10;
        for (int i = 0; i <= getWidth(); i += cellSize) {
            g.drawLine(i, 0, i, getHeight());
        }

        for (int i = 0; i <= getHeight(); i += cellSize) {
            g.drawLine(0, i, getWidth(), i);
        }

        g.setColor(Color.MAGENTA);

        int rows = field.length;
        int cols = field[0].length;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (grid.isAlive(x, y)) {
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
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

    public void copiedButton() {
        RleParser rleParser = new RleParser();
        grid.setField(rleParser.parse(rleParser.readCopiedText()));
        repaint();
    }
}
