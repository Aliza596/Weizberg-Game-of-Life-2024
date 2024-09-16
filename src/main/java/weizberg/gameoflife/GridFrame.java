package weizberg.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridFrame extends JFrame {

    public GridFrame() {
        setSize(800, 600);
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        Grid grid = new Grid(300, 400);
        GridComponent gridComponent = new GridComponent(grid);
        gridComponent.setBackground(Color.BLACK);
        add(gridComponent, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        JButton playButton = new JButton("Play");
        buttonPanel.add(playButton);
        playButton.addActionListener(e -> gridComponent.nextGeneration());

        JButton pauseButton = new JButton("Pause");
        buttonPanel.add(pauseButton);
        pauseButton.addActionListener(e -> gridComponent.pauseButtonMethod());

        JButton clearButton = new JButton("Clear");
        buttonPanel.add(clearButton);
        clearButton.addActionListener(e -> gridComponent.clearButtonMethod());
    }


}
