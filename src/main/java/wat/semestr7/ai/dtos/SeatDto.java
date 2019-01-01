package wat.semestr7.ai.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
