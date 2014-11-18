package minechem.network.message;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.server.FMLServerHandler;
import io.netty.buffer.ByteBuf;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

public class DecomposerUpdateMessage implements IMessage, IMessageHandler<DecomposerUpdateMessage, IMessage>
{
	private int posX, posY, posZ;
	private int energyStored, state;
	private int fluidId, fluidAmount;

	public DecomposerUpdateMessage()
	{

	}

	public DecomposerUpdateMessage(DecomposerTileEntity tile)
	{
		this.posX = tile.xCoord;
		this.posY = tile.yCoord;
		this.posZ = tile.zCoord;

		this.energyStored = tile.getEnergyStored();
		this.state = tile.getState().ordinal();

		if (tile.tank != null)
		{
			this.fluidId = tile.tank.fluidID;
			this.fluidAmount = tile.tank.amount;
		}
		else
		{
			this.fluidId = -1;
			this.fluidAmount = -1;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.posX = buf.readInt();
		this.posY = buf.readInt();
		this.posZ = buf.readInt();

		this.energyStored = buf.readInt();
		this.state = buf.readInt();

		this.fluidId = buf.readInt();
		this.fluidAmount = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.posX);
		buf.writeInt(this.posY);
		buf.writeInt(this.posZ);

		buf.writeInt(this.energyStored);
		buf.writeInt(this.state);

		buf.writeInt(this.fluidId);
		buf.writeInt(this.fluidAmount);
	}

	@Override
	public IMessage onMessage(DecomposerUpdateMessage message, MessageContext ctx)
	{
		TileEntity tileEntity;
		if (ctx.side == Side.CLIENT) tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.posX, message.posY, message.posZ);
		else tileEntity = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getTileEntity(message.posX, message.posY, message.posZ);
		if (tileEntity instanceof DecomposerTileEntity)
		{
			((DecomposerTileEntity) tileEntity).syncEnergyValue(message.energyStored);
			((DecomposerTileEntity) tileEntity).setState(message.state);
			FluidStack tankStack = null;
			if (message.fluidId != -1) tankStack = new FluidStack(message.fluidId, message.fluidAmount);
			((DecomposerTileEntity) tileEntity).tank = tankStack;
		}
		return null;

	}
}
