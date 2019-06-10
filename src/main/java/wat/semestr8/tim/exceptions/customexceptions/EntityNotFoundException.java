package wat.semestr8.tim.exceptions.customexceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException (String message){
        super(message);
    }

    public EntityNotFoundException() {
    }
}
