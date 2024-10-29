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

    public int[][] rleToField(String clipboardText) {
        String rle = readRlefromString(clipboardText);
        return parse(rle);
    }

    public String readRlefromString(String clipboardText) {
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

    public int[][] parse(String file) {
        char letter;
        String strWidth;
        String strHeight;
        String strNum;
        String line;
        String title;
        String comment = "";
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
                    System.out.println("Y: " + line);
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
