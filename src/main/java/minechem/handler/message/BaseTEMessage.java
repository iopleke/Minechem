package minechem.handler.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

/**
 * Basic message for {@link net.minecraft.tileentity.TileEntity} T represents the {@link net.minecraft.tileentity.TileEntity}
 */
public abstract class BaseTEMessage<T extends TileEntity> extends BaseMessage implements IMessage
{
    private int posX, posY, posZ;

    /**
     * Constructor needed for reflection
     */
    public BaseTEMessage()
    {
    }

    /**
     * Basic Constructor using the TileEntity Use super(myTE); in subClasses
     *
     * @param entity
     */
    public BaseTEMessage(T entity)
    {
        this.posX = entity.xCoord;
        this.posY = entity.yCoord;
        this.posZ = entity.zCoord;
    }

    /**
     * Read values from ByteBuf Use super(buf); in subClasses
     *
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
    }

    /**
     * Write values to ByteBuf Use super(buf); in subClasses
     *
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
    }

    /**
     * Gets the TileEntity
     *
     * @param ctx
     * @return can be null
     */
    public T getTileEntity(MessageContext ctx)
    {
        TileEntity tileEntity = getWorld(ctx).getTileEntity(this.posX, this.posY, this.posY);
        return tileEntity == null ? null : (T) tileEntity;
    }
}
