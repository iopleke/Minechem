package pixlepix.minechem.minechem.common.polytool;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPolytool extends Container{
	public InventoryPlayer player;
	public ArrayList infusionsToUpdate=new ArrayList();
	public ContainerPolytool(EntityPlayer invPlayer) {
		this.player=invPlayer.inventory;
		

		addSlotToContainer(new SlotPolytool(new PolytoolInventory(player.getCurrentItem(),invPlayer), 0, 8, 17));
		
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(player, x, 8 + 18 * x, 64+130));
		}

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + 18 * x, 64 + 72 + y * 18));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		
		return null;
	}

}
