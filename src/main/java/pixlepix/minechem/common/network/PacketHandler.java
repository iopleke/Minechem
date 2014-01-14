package pixlepix.minechem.common.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ljdp.easypacket.EasyPacketDispatcher;
import ljdp.easypacket.EasyPacketHandler;
import pixlepix.minechem.api.core.EnumElement;
import pixlepix.minechem.common.polytool.GuiPolytool;
import pixlepix.minechem.common.polytool.PolytoolHelper;
import pixlepix.minechem.common.polytool.PolytoolUpgradeType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketHandler implements IPacketHandler {

	public static final String MINECHEM_PACKET_CHANNEL = "MineChem2";
	private static PacketHandler instance;

	public static final PacketHandler getInstance() {
		return instance;
	}

	private EasyPacketDispatcher dispatcher;
	public EasyPacketHandler decomposerUpdateHandler;
	public EasyPacketHandler printerUpdateHandler;
	public EasyPacketHandler ghostBlockUpdateHandler;
	public EasyPacketHandler synthesisUpdateHandler;
	public EasyPacketHandler activeJournalItemHandler;
	public EasyPacketHandler swapItemHandler;
	public PacketHandler() {
		instance = this;
		dispatcher = new EasyPacketDispatcher(MINECHEM_PACKET_CHANNEL);
		decomposerUpdateHandler = EasyPacketHandler.registerEasyPacket(PacketDecomposerUpdate.class, dispatcher);

		ghostBlockUpdateHandler = EasyPacketHandler.registerEasyPacket(PacketGhostBlock.class, dispatcher);
		synthesisUpdateHandler = EasyPacketHandler.registerEasyPacket(PacketSynthesisUpdate.class, dispatcher);
		activeJournalItemHandler = EasyPacketHandler.registerEasyPacket(PacketActiveJournalItem.class, dispatcher);
		//swapItemHandler = EasyPacketHandler.registerEasyPacket(PacketSwapItem.class, dispatcher);
	}
	@SideOnly(Side.CLIENT)
	public void receivePolytoolUpdate(INetworkManager manager, Packet250CustomPayload packet, Player player){
		if(Minecraft.getMinecraft().currentScreen instanceof GuiPolytool){
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.data);
			DataInputStream inputStream = new DataInputStream(byteArrayInputStream);
			try {
				inputStream.readByte();
				PolytoolUpgradeType upgrade=PolytoolHelper.getTypeFromElement(EnumElement.values()[inputStream.readByte()],inputStream.readByte());
				((GuiPolytool)Minecraft.getMinecraft().currentScreen).addUpgrade(upgrade);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {


		if (packet.data != null){

			if(packet.data[0]==42){
				//PolytoolUpdatePacket
				this.receivePolytoolUpdate(manager, packet, player);
				return;
			}else{
				dispatcher.onPacketData(manager, packet, player);
			}
		}
	}

	public static void sendPolytoolUpdatePacket(PolytoolUpgradeType data, EntityPlayer player){
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(byteStream);
		try {
			dataStream.writeByte((byte)42);

			dataStream.writeByte(data.getElement().ordinal());

			dataStream.writeByte((int) data.power);
		} catch (IOException e) {
			System.out.println("Exception sending polytool update packet");
			e.printStackTrace();
		}


		PacketDispatcher.sendPacketToPlayer(PacketDispatcher.getPacket(MINECHEM_PACKET_CHANNEL, byteStream.toByteArray()),(Player) player);

	}

}
