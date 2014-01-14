package pixlepix.particlephysics.common.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.gui.ContainerEmitter;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
	public static PacketHandler instance;
	public void PacketHandler(){
		instance=this;
	}
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player playerEntity) {

		if(packet.channel.equals("Particle")){

			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			int type=-1;
			try {
				type = inputStream.readByte();
			} catch (IOException e) {

				e.printStackTrace();
			}
			switch(type){
			case 0:
				this.handleParticleUpdatePacket(inputStream);
				break;
			case 1:
				if(playerEntity instanceof EntityPlayer){
					Container container=((EntityPlayer)playerEntity).openContainer;
					if(container instanceof ContainerEmitter){
						try {
							((ContainerEmitter)container).getMachine().receiveButton(inputStream.readByte(),inputStream.readByte());
						} catch (IOException e) {

							e.printStackTrace();
						}
					}
				}
			}

		}
	}
	private void handleParticleUpdatePacket(DataInputStream inputStream) {



		try {

			int entityId=inputStream.readInt();
			Entity toMove=Minecraft.getMinecraft().theWorld.getEntityByID(entityId);
			if(toMove!=null){
				toMove.setPosition(inputStream.readDouble(), inputStream.readDouble(),inputStream.readDouble());
				toMove.setVelocity(inputStream.readDouble(),inputStream.readDouble(),inputStream.readDouble());

				if(toMove instanceof BaseParticle){
					((BaseParticle) toMove).effect=inputStream.readInt();

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}
	public static void sendInterfacePacket(byte type, byte val) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte)1);

			dataStream.writeByte((byte)type);
			dataStream.writeByte(val);

			PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket("Particle", byteStream.toByteArray()));
		}catch(IOException ex) {
			System.err.append("Failed to send button click packet");
		}
	}

}
