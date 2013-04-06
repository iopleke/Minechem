package ljdp.minechem.common.containers;

import ljdp.minechem.api.core.IRadiationShield;
import ljdp.minechem.common.MinechemItems;
import ljdp.minechem.common.tileentity.TileEntityDecomposer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerDecomposer extends Container implements IRadiationShield {
	
	protected TileEntityDecomposer decomposer;
	protected final int kPlayerInventorySlotStart;
	protected final int kPlayerInventorySlotEnd;
	protected final int kDecomposerInventoryEnd;
	
	public ContainerDecomposer(InventoryPlayer inventoryPlayer, TileEntityDecomposer decomposer) {
		this.decomposer = decomposer;
		kPlayerInventorySlotStart = decomposer.getSizeInventory();
		kPlayerInventorySlotEnd   = kPlayerInventorySlotStart + (9*4);
		kDecomposerInventoryEnd   = decomposer.getSizeInventory();
		
		addSlotToContainer(new Slot(decomposer, decomposer.kInputSlot, 80, 16));
		bindOutputSlots();
		bindBottleSlots();
		bindPlayerInventory(inventoryPlayer);
	}
	
	private void bindOutputSlots() {
		int x = 8;
		int y = 62;
		int j = 0;
		for(int i = 1; i < 10; i++) {
			addSlotToContainer(new SlotOutput(decomposer, i, x + (j * 18), y));
			j++;
		}
	}
	
	private void bindBottleSlots() {
		addSlotToContainer(new SlotTestTube(decomposer, decomposer.kEmptyTestTubeSlotStart,     125, 15));
		addSlotToContainer(new SlotTestTube(decomposer, decomposer.kEmptyTestTubeSlotStart + 1, 143, 15));
		addSlotToContainer(new SlotTestTube(decomposer, decomposer.kEmptyTestTubeSlotStart + 2, 125, 33));
		addSlotToContainer(new SlotTestTube(decomposer, decomposer.kEmptyTestTubeSlotStart + 3, 143, 33));
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
		return decomposer.isUseableByPlayer(entityPlayer);
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
			} else if(stackInSlot.itemID == MinechemItems.testTube.itemID) {
				if(!mergeItemStack(stackInSlot, decomposer.kEmptyTestTubeSlotStart, decomposer.kEmptyTestTubeSlotEnd + 1, false))
					return null;
			} else if(slot >= kPlayerInventorySlotStart) {
				if(!mergeItemStack(stackInSlot, decomposer.kInputSlot, decomposer.kInputSlot + 1, false))
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
