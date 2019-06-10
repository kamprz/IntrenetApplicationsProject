package wat.semestr8.tim.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SeatDto
{
    private int row;
    private int col;

    public SeatDto(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public SeatDto() {
    }
}
