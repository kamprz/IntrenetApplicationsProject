package wat.semestr7.ai.exceptions.customexceptions;

public class EntityAlreadyExists extends Exception
{
    EntityAlreadyExists(Exception e)
    {
        super(e);
    }
    EntityAlreadyExists(String message)
    {
        super(message);
    }
}
