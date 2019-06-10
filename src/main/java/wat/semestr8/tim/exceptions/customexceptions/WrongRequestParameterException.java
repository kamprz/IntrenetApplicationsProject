package wat.semestr8.tim.exceptions.customexceptions;

public class WrongRequestParameterException extends Exception {
    public WrongRequestParameterException(String message){super(message);}
    public WrongRequestParameterException(Exception ex){super(ex);}
}
