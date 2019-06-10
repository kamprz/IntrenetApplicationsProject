package wat.semestr8.tim.exceptions.customexceptions;

public class EntityAlreadyExistsException extends Exception
{
    EntityAlreadyExistsException(Exception e)
    {
        super(e);
    }
    EntityAlreadyExistsException(String message)
    {
        super(message);
    }
}
