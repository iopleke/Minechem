package ljdp.easypacket.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class IntegerSerializer extends Serializer {

    @Override
    public void write(Object obj, Field field, DataOutputStream out) throws IOException, IllegalArgumentException, IllegalAccessException {
        int value = field.getInt(obj);
        out.writeInt(value);
    }

    @Override
    public void read(Object obj, Field field, DataInputStream in) throws IOException, IllegalArgumentException, IllegalAccessException {
        int value = in.readInt();
        field.setInt(obj, value);
    }

}
