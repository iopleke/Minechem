package minechem.network.message;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import net.minecraft.tileentity.TileEntity;


public class DecomposerUpdateMessage implements IMessage, IMessageHandler<DecomposerUpdateMessage, IMessage>
{
    private int posX, posY, posZ;
    private int energyStored, state;

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
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();

        this.energyStored = buf.readInt();
        this.state = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);

        buf.writeInt(this.energyStored);
        buf.writeInt(this.state);
    }

    @Override
    public IMessage onMessage(DecomposerUpdateMessage message, MessageContext ctx)
    {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.posX, message.posY, message.posZ);
        if (tileEntity instanceof DecomposerTileEntity)
        {
            ((DecomposerTileEntity) tileEntity).syncEnergyValue(message.energyStored);
            ((DecomposerTileEntity) tileEntity).setState(message.state);
        }
        return null;

    }
}
