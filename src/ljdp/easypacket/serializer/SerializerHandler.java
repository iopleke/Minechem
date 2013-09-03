package ljdp.easypacket.serializer;

import java.util.HashMap;

import net.minecraft.item.ItemStack;

public class SerializerHandler {

    public static final SerializerHandler instance = new SerializerHandler();

    public HashMap<Class<?>, Serializer> serializers = new HashMap<Class<?>, Serializer>();

    private SerializerHandler() {
        serializers.put(String.class, new StringSerializer());
        serializers.put(Long.class, new LongSerializer());
        serializers.put(Short.class, new ShortSerializer());
        serializers.put(int.class, new IntegerSerializer());
        serializers.put(boolean.class, new BooleanSerializer());
        serializers.put(byte[].class, new ByteArraySerializer());
        serializers.put(float.class, new FloatSerializer());
        serializers.put(ItemStack.class, new ItemStackSerializer());
    }

    /**
     * Registers a serializer to serializer a type.
     * 
     * @param type
     *            I.E. String.class, Long.class
     * @param serializer
     *            The serializer instance.
     */
    public void registerSerializer(Class<?> type, Serializer serializer) {
        serializers.put(type, serializer);
    }

    /**
     * Gets a serializer by type.
     * 
     * @param type
     * @return
     */
    public Serializer getSerializer(Class<?> type) {
        return serializers.get(type);
    }

}
