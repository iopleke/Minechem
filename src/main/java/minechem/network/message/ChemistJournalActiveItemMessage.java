package minechem.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import minechem.MinechemItemsRegistration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ChemistJournalActiveItemMessage implements IMessage, IMessageHandler<ChemistJournalActiveItemMessage, IMessage>
{
    private int itemID, itemDMG, slot;

    public ChemistJournalActiveItemMessage()
    {

    }

    public ChemistJournalActiveItemMessage(ItemStack activeStack, EntityPlayer player)
    {
        this.itemID = Item.getIdFromItem(activeStack.getItem());
        this.itemDMG = activeStack.getItemDamage();
        this.slot = player.inventory.currentItem;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.itemID = buf.readInt();
        this.itemDMG = buf.readInt();
        this.slot = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.itemID);
        buf.writeInt(this.itemDMG);
        buf.writeInt(this.slot);
    }

    @Override
    public IMessage onMessage(ChemistJournalActiveItemMessage message, MessageContext ctx)
    {
        ItemStack journal = ctx.getServerHandler().playerEntity.inventory.mainInventory[this.slot];
        if (journal != null && journal.getItem() == MinechemItemsRegistration.journal)
        {
            ItemStack activeStack = new ItemStack(Item.getItemById(message.itemID), 1, message.itemDMG);
            MinechemItemsRegistration.journal.setActiveStack(activeStack, journal);
        }
        return null;
    }
}
