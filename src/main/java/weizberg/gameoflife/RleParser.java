package weizberg.gameoflife;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

public class RleParser {
    private final int[][] field;
    private final int xVal = 100;
    private final int yVal = 100;

    public RleParser() {
        field = new int[xVal][yVal];
    }

    public int[][] parse(String file) {
        char letter;
        String strWidth;
        String strHeight;
        String strNum;
        String line;
        int height;
        int width;
        int startingPointX = 0;
        int numOfSpaces = 1;
        int x = 0;
        int y = 0;
        String [] lines = file.split("\n");

        for (int j = 0; j < lines.length; j++) {
            line = lines[j];

            if (line.startsWith("#")) {
                continue;
            }

            for (int i = 0; i < line.length(); i++) {
                letter = line.charAt(i);

                if (letter == 'x') {
                    int commaIndex = line.indexOf(',', i);
                    strWidth = line.substring(i + 3, commaIndex).trim();
                    width = Integer.parseInt(strWidth);
                    x = startingPointX = (xVal / 2) - (width / 2);
                    i = commaIndex - 1;
                } else if (letter == 'y') {
                    int commaIndex = line.indexOf(',', i);
                    if (commaIndex == -1) {
                        strHeight = line.substring(i + 3).trim();
                    } else {
                        strHeight = line.substring(i + 3, commaIndex).trim();
                    }
                    height = Integer.parseInt(strHeight);
                    y = (yVal / 2) - (height / 2);
                    break;
                } else if (Character.isDigit(letter)) {
                    char nextLetter = line.charAt(i + 1);
                    if (Character.isDigit(nextLetter)) {
                        strNum = letter + Character.toString(nextLetter);
                        numOfSpaces = Integer.parseInt(strNum);
                        i++;
                    } else {
                        numOfSpaces = Integer.parseInt(Character.toString(letter));
                    }
                } else if (letter == 'b') {
                    x += numOfSpaces;
                    numOfSpaces = 1;
                } else if (letter == 'o') {
                    for (int k = 0; k < numOfSpaces; k++) {
                        field[y][x] = 1;
                        x++;
                    }
                    numOfSpaces = 1;
                } else if (letter == '$') {
                    y++;
                    x = startingPointX;
                }
            }
        }


        return field;
    }
}
