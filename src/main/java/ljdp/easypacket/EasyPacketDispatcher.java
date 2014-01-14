package ljdp.easypacket;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class EasyPacketDispatcher implements IPacketHandler {

    private String channel;

    /**
     * The EasyPacketDispatcher is links your packet handlers with minecraft's packetDispatchers.
     *
     * @param String channel
     */
    public EasyPacketDispatcher(String channel) {
        this.channel = channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return this.channel;
    }

    public Packet250CustomPayload createCustomPacket(byte[] data, boolean isChunkData) {
        Packet250CustomPayload customPacket = new Packet250CustomPayload();
        customPacket.channel = this.channel;
        customPacket.data = data;
        customPacket.length = data.length;
        customPacket.isChunkDataPacket = isChunkData;
        return customPacket;
    }

    public void sendDataToServer(byte[] data, boolean isChunkData) {
        Packet250CustomPayload packet = createCustomPacket(data, isChunkData);
        PacketDispatcher.sendPacketToServer(packet);
    }

    public void sendDataToPlayer(byte[] data, boolean isChunkData, Player player) {
        Packet250CustomPayload packet = createCustomPacket(data, isChunkData);
        PacketDispatcher.sendPacketToPlayer(packet, player);
    }

    public void sendDataToAllPlayers(byte[] data, boolean isChunkData) {
        Packet250CustomPayload packet = createCustomPacket(data, isChunkData);
        PacketDispatcher.sendPacketToAllPlayers(packet);
    }

    public void sendDataToAllPlayersInDimension(byte[] data, boolean isChunkData, int dimensionID) {
        Packet250CustomPayload packet = createCustomPacket(data, isChunkData);
        PacketDispatcher.sendPacketToAllInDimension(packet, dimensionID);

    }

    public void sendDataToAllPlayersAround(byte[] data, boolean isChunkData, double x, double y, double z, double range, int dimensionID) {
        Packet250CustomPayload packet = createCustomPacket(data, isChunkData);
        PacketDispatcher.sendPacketToAllAround(x, y, z, range, dimensionID, packet);
    }

    /**
     * You'll need to create your own class that implements IPacketHandler and call this method in onPacketData.
     */
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.data);
        DataInputStream inputStream = new DataInputStream(byteArrayInputStream);
        try {
            int packetID = inputStream.readByte();
            EasyPacketHandler.onDataReceived(manager, inputStream, player, packetID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
