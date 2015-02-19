package minechem.handler.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import minechem.item.journal.JournalItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.UUID;

/**
 * Message used to write knowledge in on an journal on the server side
 */
public class JournalMessage extends BaseTEMessage implements IMessageHandler<JournalMessage, IMessage>
{
    private String uuid;
    
    public JournalMessage()
    {
                
    }
    
    public JournalMessage(EntityPlayer player)
    {
          this(player.getUniqueID());
    }
    
    public JournalMessage(UUID uuid)
    {
        this.uuid = uuid.toString();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        int length = buf.readInt();
        this.uuid = new String(buf.readBytes(length).array());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.uuid.length());
        buf.writeBytes(this.uuid.getBytes());
    }

    @Override
    public IMessage onMessage(JournalMessage message, MessageContext ctx)
    {
        EntityPlayer player = getServerPlayer(ctx);
        if (player.getUniqueID().equals(UUID.fromString(message.uuid)))
        {
            if (player.getHeldItem().getItem() instanceof JournalItem)
            {
                ItemStack journalStack = player.getHeldItem();
                JournalItem journalItem = (JournalItem) journalStack.getItem();
                journalItem.writeKnowledge(journalStack, player, false);
            }
        }
        return null;
    }
}
