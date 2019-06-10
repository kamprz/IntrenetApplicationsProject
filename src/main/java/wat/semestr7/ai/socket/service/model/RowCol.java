package wat.semestr7.ai.socket.service.model;

import java.util.Objects;

public class RowCol {
    private String row;
    private String col;

    public RowCol(String row, String col) {
        this.row = row;
        this.col = col;
    }

    public String getRow() {
        return row;
    }

    public String getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "row='" + row + '\'' +
                ", col='" + col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RowCol rowCol = (RowCol) o;
        return Objects.equals(row, rowCol.row) &&
                Objects.equals(col, rowCol.col);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
