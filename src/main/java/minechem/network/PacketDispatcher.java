package minechem.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.io.IOException;
import java.util.List;

//Hopefully to replace network code with netty using SimpleNetworkWrapper in FML
public class PacketDispatcher extends SimpleNetworkWrapper {
	public PacketDispatcher(String channel){
		super(channel);

	}
	public <T extends IMessage & IMessageHandler<T, IMessage>>void registerPacket(int id, Side side, Class<T> messageClass){
		registerMessage(messageClass, messageClass, id, side);
	}

	public void sendPacket(EntityPlayer player, IMessage message) {
		sendTo(message, (EntityPlayerMP)player);
	}

	public void sendPacketAllAround(World world, double x, double y, double z, double distance, IMessage message) {
		sendToAllAround(message, new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, distance));
	}

	public void sendPacketAllAround(World world, double x, double y, double z,
	                            double distance, EntityPlayer except, IMessage message) {
		for (EntityPlayer player : (List<EntityPlayer>)world.playerEntities) {
			if (player == except) continue;
			double dx = x - player.posX;
			double dy = y - player.posY;
			double dz = z - player.posZ;
			if ((dx * dx + dy * dy + dz * dz) < (distance * distance))
				sendPacket((EntityPlayerMP) player, message);
		}
	}

	/** Sends a packet to everyone tracking an entity. */
	public void sendPacketToAllTracking(IMessage message, Entity entity) {
		((WorldServer)entity.worldObj).getEntityTracker().func_151247_a(entity, getPacketFrom(message));
	}

	/** Sends a packet to everyone tracking an entity,
	 *  including the entity itself if it's a player. */
	public void sendToAndAllTracking(IMessage message, Entity entity) {
		sendPacketToAllTracking(message, entity);
		if (entity instanceof EntityPlayer)
			sendPacket((EntityPlayer) entity, message);
	}

	public static abstract class AbstractPacket<T extends AbstractPacket<T>> implements IMessage, IMessageHandler<T, IMessage> {

		/*
		 * sendPacket
		 *
		 * Abstract method used to send packet to client
		 */
		public abstract void sendPacket(PacketBuffer buffer) throws IOException;

		public abstract void receivePacket(PacketBuffer buffer) throws IOException;

		public abstract void processPacket(EntityPlayer player);

		@Override
		public final void toBytes(ByteBuf buffer) {
			try { sendPacket(new PacketBuffer(buffer)); }
			catch (IOException ex) { throw new RuntimeException(ex); }
		}

		@Override
		public final void fromBytes(ByteBuf buffer) {
			try { receivePacket(new PacketBuffer(buffer)); }
			catch (IOException ex) { throw new RuntimeException(ex); }
		}

		@Override
		public final IMessage onMessage(T message, MessageContext context) {
			message.processPacket(context.side.isServer() ? context.getServerHandler().playerEntity
					: Minecraft.getMinecraft().thePlayer);
			return null;
		}

	}
}
