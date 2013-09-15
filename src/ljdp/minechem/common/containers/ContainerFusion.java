package ljdp.minechem.common.containers;

import ljdp.minechem.api.core.IRadiationShield;
import ljdp.minechem.common.tileentity.TileEntityFusion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerFusion extends Container implements IRadiationShield {

	TileEntityFusion fusion;
	InventoryPlayer inventoryPlayer;

	public ContainerFusion(InventoryPlayer inventoryPlayer, TileEntityFusion fusion) {
		this.inventoryPlayer = inventoryPlayer;
		this.fusion = fusion;
		
		addSlotToContainer(new Slot(fusion, fusion.kStartFusionStar, 80, 18));
		addSlotToContainer(new Slot(fusion, fusion.kStartInput1, 22, 62));
		addSlotToContainer(new Slot(fusion, fusion.kStartInput2, 138, 62));
		addSlotToContainer(new Slot(fusion, fusion.kStartOutput, 80, 62));
		
		bindPlayerInventory(inventoryPlayer);
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
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slot) {
		
		Slot slotObject = (Slot) inventorySlots.get(slot);
		if(slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			ItemStack stack = stackInSlot.copy();
			if(slot >= 0 && slot < fusion.getSizeInventory()) {
				if(!mergeItemStack(stackInSlot, fusion.getSizeInventory(), inventorySlots.size(), true)){
					System.out.println(1);
					return null;
				}
			} else if(slot >= fusion.getSizeInventory()) {
				if(!mergeItemStack(stackInSlot, fusion.kStartInput1, fusion.kStartInput1 + 1, false)){
					System.out.println(2);
					return null;
				}
			}
			
			if(stackInSlot.stackSize == 0){
				System.out.println(3);
				slotObject.putStack(null);
			}
			else{
				System.out.println(4);
				slotObject.onSlotChanged();
			}
			System.out.println(5);
			return stack;
		}
		System.out.println(6);
		return null;
	}

	@Override
	public float getRadiationReductionFactor(int baseDamage, ItemStack itemstack, EntityPlayer player) {
		return 1.0F;
	}


}
