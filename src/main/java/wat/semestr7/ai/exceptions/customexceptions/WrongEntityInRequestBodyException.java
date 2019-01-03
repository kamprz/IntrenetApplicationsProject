package wat.semestr7.ai.exceptions.customexceptions;

public class WrongEntityInRequestBodyException extends Exception {
    public WrongEntityInRequestBodyException(Exception e) { super(e);}
    public WrongEntityInRequestBodyException(String message){ super(message);}
}
