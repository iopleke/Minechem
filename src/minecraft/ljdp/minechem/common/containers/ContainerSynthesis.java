package ljdp.minechem.common.containers;

import java.util.List;

import ljdp.minechem.api.core.IRadiationShield;
import ljdp.minechem.common.MinechemItems;
import ljdp.minechem.common.tileentity.TileEntitySynthesis;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSynthesis extends ContainerWithFakeSlots implements IRadiationShield {

	private TileEntitySynthesis synthesis;
	
	public ContainerSynthesis(InventoryPlayer inventoryPlayer, TileEntitySynthesis synthesis) {
		this.synthesis = synthesis;
		addSlotToContainer(new SlotSynthesisOutput(synthesis, synthesis.kStartOutput, 134, 18));
		bindRecipeMatrixSlots();
		bindTestTubeSlots();
		bindStorageSlots();
		addSlotToContainer(new SlotJournal(synthesis, synthesis.kStartJournal, 26, 36));
		bindPlayerInventory(inventoryPlayer);
	}
	
	private void bindRecipeMatrixSlots() {
		int slot = 0;
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				addSlotToContainer(new SlotFake(synthesis, synthesis.kStartRecipe + slot, 62 + (col * 18), 18 + (row * 18)));
				slot++;
			}
		}
	}
	
	private void bindStorageSlots() {
		int slot = 0;
		for(int col = 0; col < 9; col++) {
			addSlotToContainer(new SlotChemical(synthesis, synthesis.kStartStorage + slot, 8 + (col * 18), 84));
			slot++;
		}
	}
	
	private void bindTestTubeSlots() {
		int slot = 0;
		for(int row = 0; row < 2; row++) {
			for(int col = 0; col < 2; col++) {
				addSlotToContainer(new SlotOutput(synthesis, synthesis.kStartBottles + slot, 134 + (col * 18), 40 + (row * 18)));
				slot++;
			}
		}
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
			if(slot != synthesis.kStartJournal && stack.itemID == MinechemItems.journal.itemID 
					&& !getSlot(synthesis.kStartJournal).getHasStack())
			{
				ItemStack copystack = slotObject.decrStackSize(1);
				getSlot(synthesis.kStartJournal).putStack(copystack);
				return null;
			} else if(slot == synthesis.kStartOutput) {
				if(!craftMaxmimum())
					return null;
			} else if(slot >= synthesis.kStartBottles && slot < synthesis.kStartBottles + synthesis.kSizeBottles) {
				if(!mergeItemStack(stackInSlot, synthesis.getSizeInventory(), inventorySlots.size(), true))
					return null;
			} else if(slot >= synthesis.getSizeInventory() && slot < inventorySlots.size() 
					&& (stackInSlot.itemID == MinechemItems.element.itemID 
					|| stackInSlot.itemID == MinechemItems.molecule.itemID))
			{
				if(!mergeItemStack(stackInSlot, synthesis.kStartStorage, synthesis.kStartStorage + synthesis.kSizeStorage, false))
					return null;
			} else if(slot >= synthesis.kStartStorage && slot < synthesis.kStartStorage + synthesis.kSizeStorage) {
				if(!mergeItemStack(stackInSlot, synthesis.getSizeInventory(), inventorySlots.size(), true))
					return null;
			} else if(slot == synthesis.kStartJournal) {
				if(!mergeItemStack(stackInSlot, synthesis.getSizeInventory(), inventorySlots.size(), true))
					return null;
			} else
				return null;
			
			if(stackInSlot.stackSize == 0)
				slotObject.putStack(null);
			else
				slotObject.onSlotChanged();
			
			return stack;
		}
		return null;
	}
	
	public boolean craftMaxmimum() {
		List<ItemStack> outputs = synthesis.getMaximumOutput();
		if(outputs == null)
			return false;
		for(ItemStack output : outputs) {
			if(!mergeItemStack(output, synthesis.getSizeInventory(), inventorySlots.size(), true))
				return false;
		}
		return true;
	}

	@Override
	public float getRadiationReductionFactor(int baseDamage, ItemStack itemstack, EntityPlayer player) {
		return 0.4F;
	}

}
