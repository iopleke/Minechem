package ljdp.minechem.common.containers;

import ljdp.minechem.common.tileentity.TileEntityVat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerVat extends Container {
	
	TileEntityVat vat;
	
	public ContainerVat(InventoryPlayer inventoryPlayer, TileEntityVat vat) {
		this.vat = vat;
		addSlotToContainer(new SlotChemical(vat, vat.kStartInput, 8, 50));
		addSlotToContainer(new SlotTestTube(vat, vat.kStartTestTubeIn, 54, 18));
		addSlotToContainer(new SlotTestTube(vat, vat.kStartTestTubeOut, 126, 18));
		addSlotToContainer(new SlotChemical(vat, vat.kStartOutput, 172, 50));
		bindPlayerInventory(inventoryPlayer);
	}
	
	private void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		int inventoryX = 18;
		int inventoryY = 84;
		int hotBarY = 142;
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                    addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                    		inventoryX + j * 18, inventoryY + i * 18)
                    );
            }
		}

		for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, inventoryX + i * 18, hotBarY));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return this.vat.isUseableByPlayer(entityPlayer);
	}

}
