package wat.semestr7.ai.services.ticketsending.ticketgenerator;

import java.util.HashMap;
import java.util.Map;

import static wat.semestr7.ai.services.ticketsending.ticketgenerator.MapValues.*;

public class MapGenerator {

    private Map<MapValues, String> varMap;

    public MapGenerator() {
        varMap = new HashMap<>();
    }

    public MapGenerator putConcertTitle(String concertName) {
        this.varMap.put(CONCERT, concertName);
        return this;
    }

    public MapGenerator putDate (String date) {
        this.varMap.put(DATE, date);
        return this;
    }

    public MapGenerator putRow (String row) {
        this.varMap.put(ROW, row);
        return this;
    }

    public MapGenerator putPosition (String position) {
        this.varMap.put(POSITION, position);
        return this;
    }

    public MapGenerator putPrice (String price) {
        this.varMap.put(PRICE, price);
        return this;
    }

    public MapGenerator putDiscount (String discount) {
        this.varMap.put(DISCOUNT, discount);
        return this;
    }

    public Map get() {
        return varMap;
    }
}
