package weizberg.gameoflife;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RleParser {
    private final String file;
    Grid grid;

    public RleParser(Path fileName, int width, int height) throws IOException {
        File filePath = fileName.toFile();
        file = Files.readString(fileName);
        grid = new Grid(width, height);
    }

    public Grid parse() {
        char letter;
        String strWidth;
        String strHeight;
        String strNum;
        String line;
        String title;
        String comment = "";
        int height;
        int width;
        int numOfSpaces = 1;
        int x = 0;
        int y = 0;
        String [] lines = file.split("\n");

        for (int j = 0; j < lines.length; j++) {
            line = lines[j];
            for (int i = 0; i < line.length(); i++) {
                letter = line.charAt(i);

                //sets the name of the design
                if (letter == '#' && i + 1 <= line.length()) {
                    if (line.charAt(i + 1) == 'N') {
                        title = line.substring(3);
                        break;
                    } else if (line.charAt(i + 1) == 'C' || line.charAt(i + 1) == 'c') {
                        comment += line.substring(3);
                        break;
                    }
                } else if (letter == 'x') {
                    int commaIndex = line.indexOf(',', i);
                    strWidth = line.substring(i + 3, commaIndex).trim();
                    width = Integer.parseInt(strWidth);
                    i = commaIndex - 1;
                } else if (letter == 'y') {
                    int commaIndex = line.indexOf(',', i);
                    if (commaIndex == -1) {
                        commaIndex = line.length() - 1;
                    }
                    strHeight = line.substring(i + 3, commaIndex).trim();
                    height = Integer.parseInt(strHeight);
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
                        grid.makeAlive(x, y);
                        x++;
                    }
                    numOfSpaces = 1;
                } else if (letter == '$') {
                    y++;
                    x = 0;
                }
            }
        }

        return grid;
    }
}
