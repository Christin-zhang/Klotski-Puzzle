package controller;

import model.Direction;
import model.MapModel;
import view.game.BoxComponent;
import view.game.GamePanel;

import model.GameSave;
import model.MoveRecord;
import model.User;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;

    private List<MoveRecord> moveHistory = new ArrayList<>();

    public void saveGame(User user) {
        if (user == null || "Guest".equals(user.getUsername())) {
            JOptionPane.showMessageDialog(view, "访客无法保存游戏", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String path = user.getSaveFilePath();

        new File(path).getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            GameSave save = new GameSave(model.getCurrentMatrixCopy(), view.getSteps(), moveHistory);
            oos.writeObject(save);
            JOptionPane.showMessageDialog(view, "游戏已成功保存！");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "保存失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadGame(User user) {
        if (user == null || "Guest".equals(user.getUsername())) {
            JOptionPane.showMessageDialog(view, "访客无法加载存档", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String path = user.getSaveFilePath();
        File file = new File(path);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(view, "未找到存档文件。", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            GameSave save = (GameSave) ois.readObject();

            model.setCurrentMatrix(save.getMatrix());
            view.resetSteps();

            for (int i = 0; i < save.getSteps(); i++) {
                view.afterMove();
            }

            this.moveHistory = new ArrayList<>(save.getHistory());
            view.refresh();
            JOptionPane.showMessageDialog(view, "存档加载成功！");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "加载失败，存档文件可能已损坏。", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
    }

    public void restartGame() {
        model.resetMatrix(); // 重置为初始矩阵
        view.resetSteps();   // 步数归零
        view.refresh();      // 刷新界面
        System.out.println("Restart game here");
    }

    public boolean doMove(int row, int col, Direction direction) {
        int nextRow = row + direction.getRow();
        int nextCol = col + direction.getCol();
        if (model.getId(row, col) == 1) {
            if (model.canMove(row, col, direction)) {
                model.getCurrentMatrix()[row][col] = 0;
                model.getCurrentMatrix()[nextRow][nextCol] = 1;
                repaintBox(nextRow, nextCol, view);
                return true;
            }
        }
        if (model.getId(row, col) == 2){
            if (model.canMove(row, col, direction)){
                model.getCurrentMatrix()[row][col] = 0;
                model.getCurrentMatrix()[row][col + 1] = 0;
                model.getCurrentMatrix()[nextRow][nextCol] = 2;
                model.getCurrentMatrix()[nextRow][nextCol + 1] = 2;
                repaintBox(nextRow, nextCol, view);
                return true;
            }
        }
        if (model.getId(row, col) == 3){
            if (model.canMove(row, col, direction)){
                model.getCurrentMatrix()[row][col] = 0;
                model.getCurrentMatrix()[row + 1][col] = 0;
                model.getCurrentMatrix()[nextRow][nextCol] = 3;
                model.getCurrentMatrix()[nextRow + 1][nextCol] = 3;
                repaintBox(nextRow, nextCol, view);
                return true;
            }
        }
        if (model.getId(row, col) == 4){
            if (model.canMove(row, col, direction)){
                model.getCurrentMatrix()[row][col] = 0;
                model.getCurrentMatrix()[row + 1][col] = 0;
                model.getCurrentMatrix()[row][col + 1] = 0;
                model.getCurrentMatrix()[row + 1][col + 1] = 0;
                model.getCurrentMatrix()[nextRow][nextCol] = 4;
                model.getCurrentMatrix()[nextRow + 1][nextCol] = 4;
                model.getCurrentMatrix()[nextRow][nextCol + 1] = 4;
                model.getCurrentMatrix()[nextRow + 1][nextCol + 1] = 4;
                repaintBox(nextRow, nextCol, view);
                return true;
            }
        }
        return false;
    }

    public void showVictory(){
        int steps = view.getSteps();
        String message = String.format("恭喜胜利！总步数: %d", steps);
        JOptionPane.showMessageDialog(view, message, "胜利", JOptionPane.INFORMATION_MESSAGE);
        view.setHasWon(true);
    }


    public static void repaintBox(int nextRow, int nextCol, GamePanel view){
        BoxComponent box = view.getSelectedBox();
        box.setRow(nextRow);
        box.setCol(nextCol);
        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
        box.repaint();
    }

    public void handleMove(Direction dir) {
        view.requestFocusInWindow();

        BoxComponent selectedBox = view.getSelectedBox();
        if (selectedBox == null) {
            JOptionPane.showMessageDialog(view, "请先选中一个方块！");
            return;
        }
        int row = selectedBox.getRow();
        int col = selectedBox.getCol();
        if (doMove(row, col, dir)) {
            moveHistory.add(new MoveRecord(row, col, dir));
            view.afterMove();
            view.refresh();   // 刷新界面
        }
    }

}
