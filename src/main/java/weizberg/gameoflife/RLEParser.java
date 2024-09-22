package weizberg.gameoflife;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class RLEParser {
    private final String file;
    Grid grid;

    public RLEParser(Path fileName, int width, int height) throws IOException {
        file = Files.readString(fileName);
        grid = new Grid(width, height);
    }

    public Grid parse() {
        char letter;
        String strWidth, strHeight, strNum, line, title, comment = "";
        int height, width;
        int numOfSpaces = 1;
        int x = 0, y = 0;
        String [] lines = file.split("\n");

        for (int j = 0; j < lines.length; j++) {
            line = lines[j];
            for (int i = 0; i < line.length(); i++) {
                letter = line.charAt(i);
                System.out.println("Char: " + letter);

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
                    System.out.println("Width: " + width);
                    i = commaIndex - 1;
                } else if (letter == 'y') {
                    int commaIndex = line.indexOf(',', i);
                    System.out.println("Comma index: " + commaIndex);
                    if (commaIndex == -1) {
                        commaIndex = line.length() - 1;
                        System.out.println("New comma index: " + commaIndex);
                    }
                    strHeight = line.substring(i + 3, commaIndex).trim();
                    height = Integer.parseInt(strHeight);
                    System.out.println("Height: " + height);
                    break;
                } else if (Character.isDigit(letter)) {
                    char nextLetter = line.charAt(i + 1);
                    if (Character.isDigit(nextLetter)) {
                        strNum = letter + Character.toString(nextLetter);
                        numOfSpaces = Integer.parseInt(strNum);
                        System.out.println("Num of spaces: " + numOfSpaces);
                        i ++;
                    } else {
                        numOfSpaces = Integer.parseInt(Character.toString(letter));
                        System.out.println("Num of spaces: " + numOfSpaces);
                    }
                    System.out.println("Num " + letter + " is a digit");
                } else if (letter == 'b') {
                    x += numOfSpaces;
                    System.out.println("Num of dead spaces: " + x);
                    System.out.println("New field: " + grid);
                    numOfSpaces = 1;
                } else if (letter == 'o') {
                    for (int k = 0; k < numOfSpaces; k++) {
                        System.out.println("Alive cell X: " + x + " Y: " + y);
                        grid.makeAlive(x, y);
                        x++;
                    }
                    System.out.println("New field\n" + grid);
                    numOfSpaces = 1;
                } else if (letter == '$') {
                    y++;
                    x = 0;
                    System.out.println("New line " + y);
                }
            }
        }

        return grid;
    }


    public Grid readFile(String str) {
        char letter;
        String line;
        String title;
        String comment = "";
        String strWidth, strHeight, strNum;
        int heightOfDesign = 0, width = 0;
        int numOfSpaces = 1;
        int x = 0;
        int y = 0;
        String[] lines = str.split("\n");

        int j = 0;
        boolean moreToRead = true;

        while (moreToRead) {
            line = lines[j];
            for (int i = 0; i < line.length(); i++) {
                letter = line.charAt(i);
                System.out.println("Char: " + letter);

                // sets the name of the design
                if (letter == '#' && line.charAt(i + 1) == 'N') {
                    title = line.substring(3);
                    break;
                } else if (letter == '#' && (line.charAt(i + 1) == 'C' || line.charAt(i + 1) == 'c')) {
                    comment += line.substring(3);
                    break;
                } else if (letter == 'x') {
                    int commaIndex = line.indexOf(',', i);
                    strWidth = line.substring(i + 3, commaIndex).trim();
                    width = Integer.parseInt(strWidth);
                    System.out.println("Width: " + width);
                    i = commaIndex - 1;
                } else if (letter == 'y') {
                    int commaIndex = line.indexOf(',', i);
                    System.out.println("Comma index: " + commaIndex);
                    if (commaIndex == -1) {
                        commaIndex = line.length() - 1;
                        System.out.println("New comma index: " + commaIndex);
                    }
                    strHeight = line.substring(i + 3, commaIndex).trim();
                    heightOfDesign = Integer.parseInt(strHeight);
                    System.out.println("Height: " + heightOfDesign);
                    moreToRead = false;
                    break;
                }
            }
            j++;
        }

        Grid grid = new Grid(300, 400);

        System.out.println("Height: " + heightOfDesign + " Width: " + width);
        for (int xx = j; xx < lines.length; xx++) {
            line = lines[xx];
            for (int i = 0; i < line.length(); i++) {
                letter = line.charAt(i);
                if (Character.isDigit(letter)) {
                    if (Character.isDigit(line.charAt(i + 1))) {
                        strNum = letter + Character.toString(line.charAt(i + 1));
                        numOfSpaces = Integer.parseInt(strNum);
                        System.out.println("Num of spaces: " + numOfSpaces);
                        i += 1;
                    } else {
                        numOfSpaces = Integer.parseInt(Character.toString(letter));
                        System.out.println("Num of space: " + numOfSpaces);
                    }
                    System.out.println("Num " + letter + " is a digit");

                    //checks if cell is dead or alive
                } else if(letter == 'b') {
                    x += numOfSpaces;
                    System.out.println("Num of dead spaces: " + x);
                    System.out.println("New field: \n" + grid);
                    numOfSpaces = 1;
                } else if (letter == 'o') {
                    for (int p = 0; p < numOfSpaces; p++) {
                        System.out.println("Alive cell X: " + x + " Y: " + y);
                        grid.makeAlive(x, y);
                        x++;
                    }
                    System.out.println("New Field: \n" + grid);
                    numOfSpaces = 1;
                } else if (letter == '$') {
                    y++;
                    x = 0;
                    System.out.println("New line " + y);
                }
            }
        }

        return grid;
    }
}
