package model;

import java.io.Serializable;

public class MoveRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private int row;
    private int col;
    private Direction direction;

    public MoveRecord(int row, int col, Direction direction) {
        this.row = row;
        this.col = col;
        this.direction = direction;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public Direction getDirection() { return direction; }
}
