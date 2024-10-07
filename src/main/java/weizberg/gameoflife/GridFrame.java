package weizberg.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GridFrame extends JFrame {


    public GridFrame() throws IOException {
        setSize(800, 600);
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        Grid grid = new Grid(300, 400);

        GridComponent gridComponent = new GridComponent(grid);
        gridComponent.setBackground(Color.BLACK);
        add(gridComponent, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(gridComponent);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        JButton playButton = new JButton("Play");
        buttonPanel.add(playButton);
        playButton.addActionListener(e -> gridComponent.playButtonMethod());

        JButton pauseButton = new JButton("Pause");
        buttonPanel.add(pauseButton);
        pauseButton.addActionListener(e -> gridComponent.pauseButtonMethod());

        JButton nextButton = new JButton("Next");
        buttonPanel.add(nextButton);
        nextButton.addActionListener(e -> gridComponent.nextGenMethod());

        JButton clearButton = new JButton("Clear");
        buttonPanel.add(clearButton);
        clearButton.addActionListener(e -> gridComponent.clearButtonMethod());

        JButton copiedButton = new JButton("Paste");
        add(copiedButton, BorderLayout.EAST);
        copiedButton.addActionListener(e -> gridComponent.copiedButton());
    }


}
