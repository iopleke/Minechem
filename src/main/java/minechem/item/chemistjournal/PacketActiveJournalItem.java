package minechem.item.chemistjournal;

import minechem.MinechemItems;
import minechem.network.MinechemPackets;
import minechem.network.MinechemPackets.ProtocolException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketActiveJournalItem extends MinechemPackets
{
    /** Item ID for the currently active item in the players chemist journal. */
    private int itemID;
    
    /** Damage value for the active item for metadata tracking purposes. */
    private int itemDMG;
    
    /** Reference to the slot number in the chemist journal that this item corresponds to. */
    private int slot;

    public PacketActiveJournalItem(ItemStack activeStack, EntityPlayer player)
    {
        // Receives information from a client specifically about current active item the player has set in their journal.
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
        // Copy over information to server instance of the chemist journal.
        if (side.isServer())
        {
            // Grab the chemist journal out of the players server world instance inventory.
            ItemStack journal = player.inventory.mainInventory[this.slot];
            
            // Check that it is indeed a journal and matches the ID we have internally for that item.
            if (journal != null && journal.itemID == MinechemItems.journal.itemID)
            {
                // Set the active damage amount for the active item to ensure meta-data items show up properly.
                ItemStack activeStack = new ItemStack(this.itemID, 1, this.itemDMG);
                
                // Sets the active stack in the server instance of the chemists journal.
                MinechemItems.journal.setActiveStack(activeStack, journal);
            }
        }
    }

    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException
    {
        // Chemist Journal specific being read on the server world instance.
        this.itemID = in.readInt();
        this.itemDMG = in.readInt();
        this.slot = in.readInt();
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        // Chemist Journal specific from the client instance of the item.
        out.writeInt(this.itemID);
        out.writeInt(this.itemDMG);
        out.writeInt(this.slot);
    }
}