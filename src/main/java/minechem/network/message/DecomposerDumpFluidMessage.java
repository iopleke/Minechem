package minechem.network.message;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import minechem.tileentity.decomposer.DecomposerTileEntity;
import minechem.tileentity.synthesis.SynthesisTileEntity;
import net.minecraft.tileentity.TileEntity;

public class DecomposerDumpFluidMessage implements IMessage, IMessageHandler<DecomposerDumpFluidMessage, IMessage>
{
    int posX,posY,posZ;

    public DecomposerDumpFluidMessage()
    {}

    public DecomposerDumpFluidMessage(DecomposerTileEntity tile)
    {
        this.posX = tile.xCoord;
        this.posY = tile.yCoord;
        this.posZ = tile.zCoord;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
    }

    @Override
    public IMessage onMessage(DecomposerDumpFluidMessage message, MessageContext ctx)
    {
        TileEntity tileEntity = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getTileEntity(message.posX, message.posY, message.posZ);
        if (tileEntity instanceof DecomposerTileEntity)
        {
            ((DecomposerTileEntity) tileEntity).dumpFluid();
        }
        return null;
    }
}
