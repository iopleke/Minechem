package ljdp.easypacket;

import cpw.mods.fml.common.network.Player;

/**
 * Packets must subclass this. You must have a constructor with no arguments. You can have other constructors too.
 * 
 * @author lukeperkin
 */
public abstract class EasyPacket {

    /**
     * This constructor is needed so that an EasyPacketHandler can create an empty packet.
     */
    public EasyPacket() {

    }

    /**
     * Return whether or not this is a chunk data packet.
     * 
     * @return boolean
     */
    public abstract boolean isChunkDataPacket();

    /**
     * Callback method when a packet of this type is received.
     * 
     * @param player
     */
    public abstract void onReceive(Player player);

}
