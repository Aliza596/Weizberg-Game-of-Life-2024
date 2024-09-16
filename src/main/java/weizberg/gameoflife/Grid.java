package weizberg.gameoflife;

import javax.sound.midi.Soundbank;

public class Grid {

    private int[][] field;
    private int height;
    private int width;


    public Grid(int width, int height) {
        field = new int[height][width];
        this.width = width;
        this.height = height;
    }

    public void makeAlive(int x, int y) {
        field[y][x] = 1;
    }

    public void makeDead(int x, int y) {
        field[y][x] = 0;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[y].length; x++) {
                builder.append(field[y][x]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public void nextGen() {
        int[][] newField = new int[field.length][field[0].length];

        //copy the original field
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[y].length; x++) {
                newField[y][x] = field[y][x];
            }
        }

        //run through array and check neighbors each time
        for (int y = 0; y < newField.length; y++) {
            for (int x = 0; x < newField[y].length; x++) {
                int totalNeighbors = 0;

                //check neighbors
                //check left neighbors
                if (x - 1 >= 0 && newField[y][x - 1] == 1) {
                    totalNeighbors++;
                }
                //check diag up left
                if (x - 1 >= 0 && y - 1 >= 0 && newField[y - 1][x - 1] == 1) {
                    totalNeighbors++;
                }
                //check up
                if (y - 1 >= 0 && newField[y - 1][x] == 1) {
                    totalNeighbors++;
                }
                //check diag up right
                if (y - 1 >= 0 && x + 1 < newField[y].length && newField[y - 1][x + 1] == 1) {
                    totalNeighbors++;
                }
                //check right
                if (x + 1 < newField[y].length && newField[y][x + 1] == 1) {
                    totalNeighbors++;
                }
                //check diag down right
                if (x + 1 < newField[y].length && y + 1 < newField.length && newField[y + 1][x + 1] == 1) {
                    totalNeighbors++;
                }
                //check down
                if (y + 1 < newField.length && newField[y + 1][x] == 1) {
                    totalNeighbors++;
                }
                //check down left
                if (y + 1 < newField.length && x - 1 >= 0 && newField[y + 1][x - 1] == 1) {
                    totalNeighbors++;
                }

                /*
                check rules:
                A live cell dies if it has fewer than two live neighbors.
                A live cell with more than three live neighbors dies.
                A dead cell will be brought back to live if it has exactly three live neighbors.
                 */
                if (totalNeighbors < 2) {
                    field[y][x] = 0;
                } else if (totalNeighbors > 3) {
                    field[y][x] = 0;
                } else if (totalNeighbors == 3) {
                    field[y][x] = 1;
                }

            }
        }
    }

    public boolean isAlive(int x, int y) {
        if (field[y][x] == 1) {
            return true;
        }
        return false;
    }

    public int[][] getField() {
        return field;
    }
}
