package controller;

import model.Direction;
import model.MapModel;
import view.game.BoxComponent;
import view.game.GamePanel;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;

    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
    }

    public void restartGame() {
        System.out.println("Do restart game here");
    }//这里没有在游戏界面实现restart

    public boolean doMove(int row, int col, Direction direction) {
        if (model.getId(row, col) == 1) {
            if (model.canMove(row, col, direction)) {
                int nextRow = row + direction.getRow();
                int nextCol = col + direction.getCol();
                model.getMatrix()[row][col] = 0;
                model.getMatrix()[nextRow][nextCol] = 1;
                repaintBox(nextRow, nextCol, view);
                return true;
            }
        }
        if (model.getId(row, col) == 2){
            if (model.canMove(row, col, direction)){
                int nextRow = row + direction.getRow();
                int nextCol = col + direction.getCol();
                model.getMatrix()[row][col] = 0;
                model.getMatrix()[row][col + 1] = 0;
                model.getMatrix()[nextRow][nextCol] = 2;
                model.getMatrix()[nextRow][nextCol + 1] = 2;
                repaintBox(nextRow, nextCol, view);
                return true;
            }
        }
        if (model.getId(row, col) == 3){
            if (model.canMove(row, col, direction)){
                int nextRow = row + direction.getRow();
                int nextCol = col + direction.getCol();
                model.getMatrix()[row][col] = 0;
                model.getMatrix()[row + 1][col] = 0;
                model.getMatrix()[nextRow][nextCol] = 3;
                model.getMatrix()[nextRow + 1][nextCol] = 3;
                repaintBox(nextRow, nextCol, view);
                return true;
            }
        }
        if (model.getId(row, col) == 4){
            if (model.canMove(row, col, direction)){
                int nextRow = row + direction.getRow();
                int nextCol = col + direction.getCol();
                model.getMatrix()[row][col] = 0;
                model.getMatrix()[row + 1][col] = 0;
                model.getMatrix()[row][col + 1] = 0;
                model.getMatrix()[row + 1][col + 1] = 0;
                model.getMatrix()[nextRow][nextCol] = 4;
                model.getMatrix()[nextRow + 1][nextCol] = 4;
                model.getMatrix()[nextRow][nextCol + 1] = 4;
                model.getMatrix()[nextRow + 1][nextCol + 1] = 4;
                repaintBox(nextRow, nextCol, view);
                return true;
            }
        }
        return false;
    }
    public static void repaintBox(int nextRow, int nextCol, GamePanel view){
        BoxComponent box = view.getSelectedBox();
        box.setRow(nextRow);
        box.setCol(nextCol);
        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
        box.repaint();
    }

    //todo: add other methods such as loadGame, saveGame...

}
