package wat.semestr7.ai.exceptions.customexceptions;

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
