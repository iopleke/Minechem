package pixlepix.minechem.common.network;

import cpw.mods.fml.common.network.Player;
import ljdp.easypacket.EasyPacket;
import ljdp.easypacket.EasyPacketData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import pixlepix.minechem.common.MinechemItems;

public class PacketActiveJournalItem extends EasyPacket {

    @EasyPacketData
    int itemID;
    @EasyPacketData
    int itemDMG;
    @EasyPacketData
    int slot;

    public PacketActiveJournalItem(ItemStack activeStack, EntityPlayer player) {
        this.itemID = activeStack.itemID;
        this.itemDMG = activeStack.getItemDamage();
        this.slot = player.inventory.currentItem;
    }

    public PacketActiveJournalItem() {

    }

    @Override
    public boolean isChunkDataPacket() {
        return false;
    }

    @Override
    public void onReceive(Player player) {
        EntityPlayer entityPlayer = (EntityPlayer) player;
        ItemStack journal = entityPlayer.inventory.mainInventory[this.slot];
        if (journal != null && journal.itemID == MinechemItems.journal.itemID) {
            ItemStack activeStack = new ItemStack(this.itemID, 1, this.itemDMG);
            MinechemItems.journal.setActiveStack(activeStack, journal);
        }
    }

}
