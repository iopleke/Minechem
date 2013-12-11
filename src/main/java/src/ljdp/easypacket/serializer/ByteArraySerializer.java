package ljdp.easypacket.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class ByteArraySerializer extends Serializer {

    @Override
    public void write(Object obj, Field field, DataOutputStream out) throws IOException, IllegalArgumentException, IllegalAccessException {
        byte[] value = (byte[]) field.get(obj);
        out.writeShort(value.length);
        out.write(value);
    }

    @Override
    public void read(Object obj, Field field, DataInputStream in) throws IOException, IllegalArgumentException, IllegalAccessException {
        short arraySize = in.readShort();
        byte[] value = new byte[arraySize];
        in.read(value);
        field.set(obj, value);
    }

}
