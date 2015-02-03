package minechem.handler.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

/**
 * Basic message for {@link net.minecraft.tileentity.TileEntity}
 */
public abstract class BaseTEMessage<T extends TileEntity> extends BaseMessage implements IMessage
{
    private int posX, posY, posZ;
    
    public BaseTEMessage()
    {
        // Needed for reflection        
    }
    
    public BaseTEMessage(T entity)
    {
        this.posX = entity.xCoord;
        this.posY = entity.yCoord;
        this.posZ = entity.zCoord;
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
    
    public T getTileEntity(MessageContext ctx)
    {
        return (T)getWorld(ctx).getTileEntity(this.posX, this.posY, this.posY);
    }
}
