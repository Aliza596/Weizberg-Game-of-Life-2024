package weizberg.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GridFrame extends JFrame {

    public GridFrame() throws IOException {

        Grid game = new Grid(300, 300);
        GridComponent gridComponent = new GridComponent(game);
        final GameOfLifeController controller = new GameOfLifeController(game, gridComponent);



        setSize(800, 600);
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        gridComponent.setBackground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(gridComponent);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        JButton playButton = new JButton("Play");
        buttonPanel.add(playButton);

        gridComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.toggleCell(e.getX(), e.getY());
            }
        });

        gridComponent.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                controller.toggleCell(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        add(gridComponent, BorderLayout.CENTER);
        playButton.addActionListener(e -> controller.timerOn());

        JButton pauseButton = new JButton("Pause");
        buttonPanel.add(pauseButton);
        pauseButton.addActionListener(e -> controller.timerOff());

        JButton nextButton = new JButton("Next");
        buttonPanel.add(nextButton);
        nextButton.addActionListener(e -> gridComponent.nextGenMethod());

        JButton clearButton = new JButton("Clear");
        buttonPanel.add(clearButton);
        clearButton.addActionListener(e -> gridComponent.clearButtonMethod());

        JButton copiedButton = new JButton("Paste");
        copiedButton.addActionListener(e -> {
            controller.pasteText();
        });
        buttonPanel.add(copiedButton);
//        add(copiedButton, BorderLayout.EAST);
    }


}
