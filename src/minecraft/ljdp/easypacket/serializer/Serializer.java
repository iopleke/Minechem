package ljdp.easypacket.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public abstract class Serializer {

    /**
     * writes the field value to the dataOutputStream.
     * 
     * @param obj
     *            The EasyPacket instance containing the field.
     * @param field
     *            The field we wish to write from.
     * @param out
     *            the stream we wish to write to.
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public abstract void write(Object obj, Field field, DataOutputStream out) throws IOException, IllegalArgumentException, IllegalAccessException;

    /**
     * Reads from the dataInputStream and sets the field's value.
     * 
     * @param obj
     *            The EasyPacket instance containing the field.
     * @param field
     *            The field we wish to set.
     * @param in
     *            The stream we wish to read from.
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public abstract void read(Object obj, Field field, DataInputStream in) throws IOException, IllegalArgumentException, IllegalAccessException;
}
