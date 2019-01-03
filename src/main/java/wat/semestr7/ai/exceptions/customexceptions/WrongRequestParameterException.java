package wat.semestr7.ai.exceptions.customexceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class WrongRequestParameterException extends Exception {
    public WrongRequestParameterException(String message){super(message);}
    public WrongRequestParameterException(Exception ex){super(ex);}
}
