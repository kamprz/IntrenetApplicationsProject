package wat.semestr8.tim.exceptions.customexceptions;

public class WrongDateFormatException extends Exception {
    public WrongDateFormatException(Exception e) { super(e);}
    public WrongDateFormatException(String message){ super(message);}
}
