package wat.semestr7.ai.socket.service.model;

public class SeatOccupationChangedWsMess {
    public String row;
    public String col;
    public MessageType type;
    public String userId;

    public enum MessageType{
        LOCKED,
        UNLOCKED
    }

    public SeatOccupationChangedWsMess(String row, String col, MessageType type, String userId) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SeatOccupationChangedWsMess{" +
                "row='" + row + '\'' +
                ", col='" + col + '\'' +
                ", type=" + type +
                '}';
    }

    public String getRow() {
        return row;
    }

    public String getCol() {
        return col;
    }

    public MessageType getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }
}
