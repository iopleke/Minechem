package ljdp.easypacket;

import cpw.mods.fml.common.network.Player;

public interface IEasyPacketCallback {

    public void onEasyPacketReceived(EasyPacket packet, EasyPacketHandler handler, Player player);

}
