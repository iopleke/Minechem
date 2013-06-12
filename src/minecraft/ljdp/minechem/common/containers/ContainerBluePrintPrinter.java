package ljdp.minechem.common.containers;

import java.util.List;

import ljdp.minechem.common.MinechemItems;
import ljdp.minechem.common.tileentity.TileEntityBluePrintPrinter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBluePrintPrinter extends ContainerWithFakeSlots {

	private TileEntityBluePrintPrinter synthesis;
	private InventoryCrafting craftMatrix;
	private IInventory craftResult;
	public ContainerBluePrintPrinter(InventoryPlayer inventoryPlayer, TileEntityBluePrintPrinter synthesis) {
		this.synthesis = synthesis;
		bindPlayerInventory(inventoryPlayer);
		addSlotToContainer(new SlotBlueprint(synthesis, synthesis.kStartJournal, 26, 36));
		
	}
	private void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                    addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                    8 + j * 18, 105 + i * 18)
                    );
            }
		}

		for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 163));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return synthesis.isUseableByPlayer(var1);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slot) {
		Slot slotObject = (Slot) inventorySlots.get(slot);
		if(slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			ItemStack stack = stackInSlot.copy();

if(slot == synthesis.kStartOutput) {
				if(slot >= synthesis.getSizeInventory() && slot < inventorySlots.size() 
					&& (stackInSlot.itemID == MinechemItems.element.itemID 
					|| stackInSlot.itemID == MinechemItems.molecule.itemID))
			{
				if(!mergeItemStack(stackInSlot, synthesis.kStartStorage, synthesis.kStartStorage + synthesis.kSizeStorage, false))
					return null;
			} else if(slot >= synthesis.kStartStorage && slot < synthesis.kStartStorage + synthesis.kSizeStorage) {
				if(!mergeItemStack(stackInSlot, synthesis.getSizeInventory(), inventorySlots.size(), true))
					return null;
			}
			else
				return null;
			
			if(stackInSlot.stackSize == 0)
				slotObject.putStack(null);
			else
				slotObject.onSlotChanged();
			
			return stack;
		}
		
	}
		return null;
	}	
	
	

}
