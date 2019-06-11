package wat.semestr8.tim.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class SeatOccupied {
    private int row;
    private int col;
    private Date socketDisconnectedTime;

    public SeatOccupied(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Date getSocketDisconnectedTime() {
        return socketDisconnectedTime;
    }

    public void setSocketDisconnectedTime(Date socketDisconnectedTime) {
        this.socketDisconnectedTime = socketDisconnectedTime;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
