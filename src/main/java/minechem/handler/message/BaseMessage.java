package minechem.handler.message;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Base class for Messages this place should be used for common {@link cpw.mods.fml.common.network.simpleimpl.IMessage} methods
 * You do need to implement {@link cpw.mods.fml.common.network.simpleimpl.IMessageHandler}
 */
public abstract class BaseMessage implements IMessage
{
    /**
     * Constructor needed for reflection
     */
    public BaseMessage()
    {
    }

    /**
     * Get the World from the MessageContext
     * @param ctx
     * @return the current World
     */
    public World getWorld(MessageContext ctx)
    {
        return ctx.side == Side.CLIENT ? FMLClientHandler.instance().getClient().theWorld : FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
    }

    /**
     * Get the EntityPlayer from the MessageContext
     * @param ctx
     * @return the current EntityPlayer
     */
    public EntityPlayer getPlayer(MessageContext ctx)
    {
        return ctx.side == Side.CLIENT ? FMLClientHandler.instance().getClientPlayerEntity() : ctx.getServerHandler().playerEntity;
    }
}