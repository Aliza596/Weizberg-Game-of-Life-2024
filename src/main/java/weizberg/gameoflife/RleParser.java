package weizberg.gameoflife;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;

public class RleParser {
    private final int[][] field;
    private final int xVal = 100;
    private final int yVal = 100;

    //use apche instead of file.readString()
    //String wholeFile = IOUtils.toString(new FileReader(filePath));
    // ^^ this sets the entire file/url/anything as a string
    public RleParser() {
        field = new int[xVal][yVal];
    }

    public String readFile() {
        String file = "";
        String clipboardText = "";

        try {
            clipboardText = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //if the copied text is the rule itself return it as is
        if (clipboardText.startsWith("#") || clipboardText.startsWith("b") || clipboardText.startsWith("o") || Character.isDigit(clipboardText.charAt(0))) {
            System.out.println("clipboard text: " + clipboardText);
            return clipboardText;

            //if the copied text is a URL then return the contents of the URL
        } else if (checksIfFile(clipboardText)) {
            try {
                System.out.println("File: " + clipboardText);
                return IOUtils.toString(new FileReader(clipboardText));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //if the copied text is a URL then return the contents of the link
        } else if (checksIfUrl(clipboardText)) {
            try {
                URL url = new URL(clipboardText);
                System.out.println("URL: "+ clipboardText);
                return IOUtils.toString(url.openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("No file: " + clipboardText);
        return file;

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

    //best way is to make a method called parse, that accepts a String as a parameter and returns an int[][] not a Grid
    //have to center the design by dividing the length by the dimensions
    //load the RLE from clipboard, either through filePATH, URL, or content of the file
    //change dimensions to 100 x 100
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
                    x = startingPointX = (xVal / 2) - (width / 2);
                    i = commaIndex - 1;
                } else if (letter == 'y') {
                    System.out.println("Y: " + line);
                    int commaIndex = line.indexOf(',', i);
                    if (commaIndex == -1) {
                        strHeight = line.substring(i + 3).trim();
//                        commaIndex = line.length() - 1;
                    } else {
                        strHeight = line.substring(i + 3, commaIndex).trim();
                    }
//                    strHeight = line.substring(i + 3, commaIndex).trim();
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
