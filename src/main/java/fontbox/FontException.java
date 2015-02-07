package fontbox;

public class FontException extends Exception
{

    public FontException(String reason)
    {
        super(reason);
    }

    public FontException(String reason, Throwable cause)
    {
        super(reason, cause);
    }

}
