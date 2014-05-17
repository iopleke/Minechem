package minechem.common.network;

import minechem.common.MinechemItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketActiveJournalItem extends MinechemPackets
{
    private int itemID;
    private int itemDMG;
    private int slot;

    public PacketActiveJournalItem(ItemStack activeStack, EntityPlayer player)
    {
        this.itemID = activeStack.itemID;
        this.itemDMG = activeStack.getItemDamage();
        this.slot = player.inventory.currentItem;
    }

    public PacketActiveJournalItem()
    {
        // Required for reflection.
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException
    {
        ItemStack journal = player.inventory.mainInventory[this.slot];
        if (journal != null && journal.itemID == MinechemItems.journal.itemID)
        {
            ItemStack activeStack = new ItemStack(this.itemID, 1, this.itemDMG);
            MinechemItems.journal.setActiveStack(activeStack, journal);
        }
    }

    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException
    {
        this.itemID = in.readInt();
        this.itemDMG = in.readInt();
        this.slot = in.readInt();
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        out.writeInt(this.itemID);
        out.writeInt(this.itemDMG);
        out.writeInt(this.slot);
    }
}
