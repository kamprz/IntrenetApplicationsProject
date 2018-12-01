package wat.semestr7.ai.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class FreeSeatDTO
{
    private int floor;
    private int row;
    private int col;

    public FreeSeatDTO(int floor, int row, int col) {
        this.row = row;
        this.col = col;
        this.floor = floor;
    }
}
