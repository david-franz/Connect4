import java.io.Serializable;

public class Move implements Serializable {

    private int row;
    private int col;

    public static int UNDEFINED = -1;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
