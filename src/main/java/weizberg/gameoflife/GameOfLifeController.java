package weizberg.gameoflife;

public class GameOfLifeController {

    //each user actions shld be a method
    private final Grid model;
    private final GridComponent view;

    public GameOfLifeController(Grid model, GridComponent view) {
        this.model = model;
        this.view = view;
    }
    public void startTimer() {

    }

    public void stopTimer() {

    }

    public void paste(String clipboardContents) {
        RleParser rleParser = new RleParser();
        model.setField(rleParser.parse(rleParser.readCopiedText()));
        view.repaint();
    }

    public void toggleCell(int screenX, int screenY) {
        int gridX = screenX / view.getCellSize();
        int gridY = screenY / view.getCellSize();

        if (model.isAlive(gridX, gridY)) {
            model.makeDead(gridX, gridY);
        } else {
            model.makeAlive(gridX, gridY);
        }

        view.repaint();

    }

}
