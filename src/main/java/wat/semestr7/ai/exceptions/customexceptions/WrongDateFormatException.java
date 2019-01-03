package wat.semestr7.ai.exceptions.customexceptions;

public class WrongDateFormatException extends Exception {
    public WrongDateFormatException(Exception e) { super(e);}
    public WrongDateFormatException(String message){ super(message);}
}
