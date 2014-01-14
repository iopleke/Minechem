package pixlepix.minechem.minechem.common.containers;

import pixlepix.minechem.minechem.api.core.EnumElement;
import pixlepix.minechem.minechem.api.core.IRadiationShield;
import pixlepix.minechem.minechem.common.MinechemItems;
import pixlepix.minechem.minechem.common.tileentity.TileEntityFission;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFission extends Container implements IRadiationShield {
	
	protected TileEntityFission fission;
	protected final int kPlayerInventorySlotStart;
	protected final int kPlayerInventorySlotEnd;
	protected final int kDecomposerInventoryEnd;
	
	public ContainerFission(InventoryPlayer inventoryPlayer, TileEntityFission fission) {
		this.fission=fission;
		kPlayerInventorySlotStart = fission.getSizeInventory();
		kPlayerInventorySlotEnd   = kPlayerInventorySlotStart + (9*4);
		kDecomposerInventoryEnd   = fission.getSizeInventory();
		
		addSlotToContainer(new Slot(fission,fission.kInput[0], 80, 16));
		bindOutputSlot();
		bindFuelSlot();
		bindPlayerInventory(inventoryPlayer);
	}
	
	private void bindOutputSlot() {
		int x = 8;
		int y = 62;
		int j = 0;
		addSlotToContainer(new Slot(fission, 2, x + (4 * 18), y));
	}
	
	private void bindFuelSlot() {
		addSlotToContainer(new Slot(fission, fission.kStartFuel, 125, 33));
	}
	
	private void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                    addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                    8 + j * 18, 84 + i * 18)
                    );
            }
		}

		for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return fission.isUseableByPlayer(entityPlayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slot) {
		Slot slotObject = (Slot) inventorySlots.get(slot);
		if(slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			ItemStack stack = stackInSlot.copy();
			if(slot >= 0 && slot < kDecomposerInventoryEnd) {
				if(!mergeItemStack(stackInSlot, kPlayerInventorySlotStart, inventorySlots.size(), true))
					return null;
			} else if(stackInSlot.itemID == MinechemItems.element.itemID && stackInSlot.getItemDamage()==EnumElement.U.atomicNumber()+1) {
				if(!mergeItemStack(stackInSlot, fission.kStartFuel, fission.kStartFuel+1, false))
					return null;
			} else if(slot >= kPlayerInventorySlotStart) {
				if(!mergeItemStack(stackInSlot, fission.kStartInput, fission.kStartInput + 1, false))
					return null;
			} else if(!mergeItemStack(stackInSlot, kPlayerInventorySlotStart, inventorySlots.size(), true))
				return null;
			
			if(stackInSlot.stackSize == 0)
				slotObject.putStack(null);
			else
				slotObject.onSlotChanged();
			
			return stack;
		}
		return null;
	}

	@Override
	public float getRadiationReductionFactor(int baseDamage, ItemStack itemstack, EntityPlayer player) {
		return 0.4F;
	}

}
