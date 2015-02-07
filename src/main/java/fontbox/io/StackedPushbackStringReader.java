package fontbox.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * StringReader with nested (stack-based) pushback and mark functionality, particularly useful for recursive parsers and readers.
 *
 * @author AfterLifeLochie
 *
 */
public class StackedPushbackStringReader
{

    /**
     * Mutex lock.
     */
    protected Object lock;
    /**
     * Map of all Characters in the stream
     */
    private ArrayList<Character> str;
    /**
     * Pushback history stack
     */
    private Stack<Integer> pushback;
    /**
     * Top of stream pointer
     */
    private int next = 0;

    public StackedPushbackStringReader(String s)
    {
        this.lock = this;
        this.str = new ArrayList<Character>();
        this.pushback = new Stack<Integer>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            str.add(chars[i]);
        }
    }

    /**
     * Check to make sure that the stream has not been closed
     */
    private void ensureOpen() throws IOException
    {
        if (str == null)
        {
            throw new IOException("Stream closed");
        }
    }

    /**
     * Get the next character on the stream, or 0 if no characters are remaining on the stream.
     *
     * @return A character or 0
     * @throws IOException
     */
    public char next() throws IOException
    {
        synchronized (lock)
        {
            ensureOpen();
            if (next >= str.size())
            {
                return (char) 0;
            }
            return str.get(next++);
        }
    }

    /**
     * Pushes the current position onto the stack. If the stack is full, a pushback overflow will be returned.
     */
    public void pushPosition() throws IOException
    {
        synchronized (lock)
        {
            ensureOpen();
            if (this.pushback.size() > 64)
            {
                throw new IOException("Pusback overflow!");
            }
            this.pushback.push(next);
        }
    }

    /**
     * Pops the previous position off the stack. If the stack is empty, a pushback underflow will be returned.
     */
    public void popPosition() throws IOException
    {
        synchronized (lock)
        {
            ensureOpen();
            if (this.pushback.size() == 0)
            {
                throw new IOException("Pushback underflow!");
            }
            this.next = this.pushback.pop();
        }
    }

    /**
     * Returns the current position of the reader.
     *
     * @return The current position of the reader.
     */
    public int getPosition() throws IOException
    {
        synchronized (lock)
        {
            ensureOpen();
            return this.next;
        }
    }

    /**
     * Sets the position of the reader.
     *
     * @param ns A new position.
     */
    public void setPosition(int ns) throws IOException
    {
        synchronized (lock)
        {
            ensureOpen();
            this.next = ns;
        }
    }

    /**
     * Commits the current position of the reader. This pops the previous return position without restoring the pointer.
     */
    public void commitPosition() throws IOException
    {
        synchronized (lock)
        {
            ensureOpen();
            this.pushback.pop();
        }
    }

    /**
     * Skips forward a number of characters.
     *
     * @param ns How far forward to skip.
     */
    public void skip(long ns) throws IOException
    {
        synchronized (lock)
        {
            ensureOpen();
            if (next >= str.size())
            {
                return;
            }
            next += ns;
            if (0 > next)
            {
                next = 0;
            }
            if (next > str.size())
            {
                next = str.size();
            }
        }
    }

    /**
     * Skips backwards a number of characters.
     *
     * @param ns How far backwards to skip.
     */
    public void rewind(long ns) throws IOException
    {
        synchronized (lock)
        {
            ensureOpen();
            if (next >= str.size())
            {
                return;
            }
            next -= ns;
            if (0 > next)
            {
                next = 0;
            }
            if (next > str.size())
            {
                next = str.size();
            }
        }
    }

    /**
     * Ensures the stream is ready for use.
     *
     * @return If the stream is ready for use.
     */
    public boolean ready() throws IOException
    {
        synchronized (lock)
        {
            ensureOpen();
            return true;
        }
    }

    /**
     * Determine the number of characters remaining in the read collection.
     *
     * @return The number of characters waiting to be read.
     */
    public int available() throws IOException
    {
        synchronized (lock)
        {
            ensureOpen();
            return Math.max(0, str.size() - next);
        }
    }

    /**
     * Closes the reader.
     */
    public void close()
    {
        str = null;
    }

}
