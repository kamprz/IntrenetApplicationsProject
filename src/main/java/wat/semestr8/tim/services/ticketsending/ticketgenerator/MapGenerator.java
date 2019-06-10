package wat.semestr8.tim.services.ticketsending.ticketgenerator;

import java.util.HashMap;
import java.util.Map;

public class MapGenerator {

    private Map<MapValues, String> varMap;

    public MapGenerator() {
        varMap = new HashMap<>();
    }

    public MapGenerator putConcertTitle(String concertName) {
        this.varMap.put(MapValues.CONCERT, concertName);
        return this;
    }

    public MapGenerator putDate (String date) {
        this.varMap.put(MapValues.DATE, date);
        return this;
    }

    public MapGenerator putRow (String row) {
        this.varMap.put(MapValues.ROW, row);
        return this;
    }

    public MapGenerator putPosition (String position) {
        this.varMap.put(MapValues.POSITION, position);
        return this;
    }

    public MapGenerator putPrice (String price) {
        this.varMap.put(MapValues.PRICE, price);
        return this;
    }

    public MapGenerator putDiscount (String discount) {
        this.varMap.put(MapValues.DISCOUNT, discount);
        return this;
    }

    public Map get() {
        return varMap;
    }
}
