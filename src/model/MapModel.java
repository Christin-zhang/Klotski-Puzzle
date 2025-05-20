package model;

/**
 * This class is to record the map of one game. For example:
 */
public class MapModel {
    int[][] matrix;


    public MapModel(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getWidth() {
        return this.matrix[0].length;
    }

    public int getHeight() {
        return this.matrix.length;
    }

    public int getId(int row, int col) {
        return matrix[row][col];
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public boolean checkInWidthSize(int col) {
        return col >= 0 && col < matrix[0].length;
    }

    public boolean checkInHeightSize(int row) {
        return row >= 0 && row < matrix.length;
    }

    public boolean canMove(int row, int col, Direction dir){
        if (dir == Direction.LEFT){
            if (getId(row, col) == 1||getId(row, col) == 2) return canSingleMove(row, col, dir);
            if (getId(row, col) == 3||getId(row, col) == 4)
                return (canSingleMove(row, col, dir) && canSingleMove(row + 1, col, dir));
            else return false;
        }
        if (dir == Direction.RIGHT){
            if (getId(row, col) == 1) return canSingleMove(row, col, dir);
            if (getId(row, col) == 2) return canSingleMove(row, col + 1, dir);
            if (getId(row, col) == 3)
                return (canSingleMove(row, col, dir) && canSingleMove(row + 1, col, dir));
            if (getId(row, col) == 4)
                return (canSingleMove(row, col + 1, dir) && canSingleMove(row + 1, col +1, dir));
            else return false;
        }
        if (dir == Direction.UP){
            if (getId(row, col) == 1||getId(row, col) == 3) return canSingleMove(row, col, dir);
            if (getId(row, col) == 2||getId(row, col) == 4)
                return (canSingleMove(row, col, dir) && canSingleMove(row, col + 1, dir));
            else return false;
        }
        if (dir == Direction.DOWN){
            if (getId(row, col) == 1) return canSingleMove(row, col, dir);
            if (getId(row, col) == 2)
                return (canSingleMove(row, col, dir) && canSingleMove(row, col + 1, dir));
            if (getId(row, col) == 3) return canSingleMove(row + 1, col, dir);
            if (getId(row, col) == 4)
                return (canSingleMove(row + 1, col, dir) && canSingleMove(row + 1, col +1, dir));
            else return false;
        }
        else return false;
    }
    public boolean canSingleMove(int row, int col, Direction dir){//包含 Boundary Detection 和 Collision Detection
        int nextRow = row + dir.getRow();
        int nextCol = col + dir.getCol();
        //Boundary Detection
        if (!checkInHeightSize(nextRow)) return false;
        if (!checkInWidthSize(nextCol)) return false;
        //Collision Detection
        int newId = getId(nextRow, nextCol);
        return (newId == 0);
    }

}
