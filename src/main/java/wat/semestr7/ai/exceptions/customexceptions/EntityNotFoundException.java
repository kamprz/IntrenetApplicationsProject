package wat.semestr7.ai.exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException (String message){
        super(message);
    }

    public EntityNotFoundException() {
    }
}
