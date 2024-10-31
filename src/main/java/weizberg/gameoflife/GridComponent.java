package weizberg.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class GridComponent extends JComponent {
    private final Grid grid;
    private int[][] field;

    public int getCellSize() {
        return cellSize;
    }

    private final int cellSize = 10;

    public GridComponent(Grid grid) {
        this.grid = grid;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        field = grid.getField();

        g.setColor(Color.white);

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
}
