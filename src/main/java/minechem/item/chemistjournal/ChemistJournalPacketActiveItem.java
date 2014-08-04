package minechem.item.chemistjournal;

import minechem.MinechemItemsGeneration;
import minechem.network.MinechemPackets;
import minechem.network.MinechemPackets.ProtocolException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class ChemistJournalPacketActiveItem extends MinechemPackets
{
    /** Item ID for the currently active item in the players chemist journal. */
    private Item item;
    
    /** Damage value for the active item for metadata tracking purposes. */
    private int itemDMG;
    
    /** Reference to the slot number in the chemist journal that this item corresponds to. */
    private int slot;

    public ChemistJournalPacketActiveItem(ItemStack activeStack, EntityPlayer player)
    {
        // Receives information from a client specifically about current active item the player has set in their journal.
        this.item = activeStack.getItem();
        this.itemDMG = activeStack.getItemDamage();
        this.slot = player.inventory.currentItem;
    }

    public ChemistJournalPacketActiveItem()
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
            if (journal != null && journal.getItem() == MinechemItemsGeneration.journal)
            {
                // Set the active damage amount for the active item to ensure meta-data items show up properly.
                ItemStack activeStack = new ItemStack(this.item, 1, this.itemDMG);
                
                // Sets the active stack in the server instance of the chemists journal.
                MinechemItemsGeneration.journal.setActiveStack(activeStack, journal);
            }
        }
    }

	//TODO: Redo packets
    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException
    {
        // Chemist Journal specific being read on the server world instance.
        this.item = in.read;
        this.itemDMG = in.readInt();
        this.slot = in.readInt();
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        // Chemist Journal specific from the client instance of the item.
        out.writeInt(Item.getIdFromItem(this.item));//TODO: Double check to see if getIdFromItem is still in used or possible use string
        out.writeInt(this.itemDMG);
        out.writeInt(this.slot);
    }
}
