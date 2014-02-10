package ljdp.easypacket;

import cpw.mods.fml.common.network.Player;
import ljdp.easypacket.serializer.Serializer;
import ljdp.easypacket.serializer.SerializerHandler;
import net.minecraft.network.INetworkManager;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EasyPacketHandler {

    private static int nextPacketID = 0;
    private static HashMap<Integer, EasyPacketHandler> handlers = new HashMap<Integer, EasyPacketHandler>();

    private static class DataField {
        Class<?> type;
        Field field;
    }

    /**
     * Registers an EasyPacket class and returns an EasyPacketHandler. Note: You can't register more than 256 EasyPackets.
     *
     * @param clazz      the EasyPacket class to register
     * @param dispatcher The dispatcher to bind the handler to.
     * @return
     */
    public static EasyPacketHandler registerEasyPacket(Class<? extends EasyPacket> clazz, EasyPacketDispatcher dispatcher) {
        int packetID = nextPacketID++;
        EasyPacketHandler packetHandler = new EasyPacketHandler(packetID, dispatcher, clazz);
        handlers.put(packetID, packetHandler);
        collectFields(packetHandler, clazz);
        return packetHandler;
    }

    private static void collectFields(EasyPacketHandler handler, Class<?> clazz) {
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null)
            collectFields(handler, superClazz);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation dataAnnotation = field.getAnnotation(EasyPacketData.class);
            if (dataAnnotation != null) {
                registerDataField(handler, field, (EasyPacketData) dataAnnotation);
            }
        }
    }

    private static void registerDataField(EasyPacketHandler handler, Field field, EasyPacketData dataAnnotation) {
        field.setAccessible(true);
        DataField dataField = new DataField();
        dataField.type = field.getType();
        dataField.field = field;
        handler.dataFields.add(dataField);
    }

    protected static void onDataReceived(INetworkManager manager, DataInputStream data, Player player, int packetID) throws IOException {
        EasyPacketHandler packetHandler = handlers.get(packetID);
        packetHandler.onDataReceived(data, player);
    }

    private int packetID;
    private List<DataField> dataFields = new ArrayList<DataField>();
    private List<IEasyPacketCallback> callbacks = new ArrayList<IEasyPacketCallback>();
    private EasyPacketDispatcher dispatcher;
    private Class<? extends EasyPacket> packetClass;

    private EasyPacketHandler(int packetID, EasyPacketDispatcher dispatcher, Class<? extends EasyPacket> clazz) {
        this.packetID = packetID;
        this.dispatcher = dispatcher;
        this.packetClass = clazz;
    }

    /**
     * Registers a class that implements IEasyPacketCallback. When a packet of the handler's type is received the callback methods will be invoked.
     *
     * @param callback
     */
    public void registerCallback(IEasyPacketCallback callback) {
        callbacks.add(callback);
    }

    /**
     * Unregisters an IEasyPacketCallback class.
     *
     * @param callback
     */
    public void unregisterCallback(IEasyPacketCallback callback) {
        callbacks.remove(callback);
    }

    /**
     * Creates an empty packet.
     *
     * @return
     */
    public EasyPacket createPacket() {
        EasyPacket easyPacket = null;
        try {
            easyPacket = packetClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return easyPacket;
    }

    private void onDataReceived(DataInputStream data, Player player) throws IOException {
        EasyPacket easyPacket = createPacket();
        if (easyPacket != null) {
            read(easyPacket, data);
            easyPacket.onReceive(player);
            for (IEasyPacketCallback callback : callbacks)
                callback.onEasyPacketReceived(easyPacket, this, player);
        }
    }

    /**
     * Writes the all fields annotated with @EasyPacketData to a byte array.
     *
     * @param easyPacket
     * @return byte[] data
     */
    public byte[] write(EasyPacket easyPacket) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bos);
        try {
            output.writeByte(packetID);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        for (DataField dataField : dataFields) {
            Serializer serializer = SerializerHandler.instance.getSerializer(dataField.type);
            if (serializer == null)
                continue;
            try {
                serializer.write(easyPacket, dataField.field, output);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bos.toByteArray();
    }

    /**
     * Reads a byte array and inserts the values in the packet's fields annotated with @EasyPacketData
     *
     * @param easyPacket
     * @param data
     */
    public void read(EasyPacket easyPacket, DataInputStream input) {
        for (DataField dataField : dataFields) {
            Serializer serializer = SerializerHandler.instance.getSerializer(dataField.type);
            if (serializer == null)
                continue;
            try {
                serializer.read(easyPacket, dataField.field, input);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToServer(EasyPacket packet) {
        byte[] data = write(packet);
        dispatcher.sendDataToServer(data, packet.isChunkDataPacket());
    }

    public void sendToAllPlayers(EasyPacket packet) {
        byte[] data = write(packet);
        dispatcher.sendDataToAllPlayers(data, packet.isChunkDataPacket());
    }

    public void sendToAllPlayersInDimension(EasyPacket packet, int dimensionID) {
        byte[] data = write(packet);
        dispatcher.sendDataToAllPlayersInDimension(data, packet.isChunkDataPacket(), dimensionID);
    }

    public void sendToAllPlayersAround(EasyPacket packet, double x, double y, double z, double range, int dimensionID) {
        byte[] data = write(packet);
        dispatcher.sendDataToAllPlayersAround(data, packet.isChunkDataPacket(), x, y, z, range, dimensionID);
    }

    public void sendToPlayer(EasyPacket packet, Player player) {
        byte[] data = write(packet);
        dispatcher.sendDataToPlayer(data, packet.isChunkDataPacket(), player);
    }

}
