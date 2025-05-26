package model;

import java.io.Serializable;
import java.util.List;


public class GameSave implements Serializable {
    private static final long serialVersionUID = 1L;

    private int[][] matrix;
    private int steps;
    private List<MoveRecord> history;

    public GameSave(int[][] matrix, int steps, List<MoveRecord> history) {
        this.matrix = matrix;
        this.steps = steps;
        this.history = history;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getSteps() {
        return steps;
    }

    public List<MoveRecord> getHistory() {
        return history;
    }
}