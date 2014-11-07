package minechem.tileentity.leadedchest;

import minechem.api.INoDecay;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LeadedChestContainer extends Container implements INoDecay
{

	protected LeadedChestTileEntity leadedchest;

	public LeadedChestContainer(InventoryPlayer inventoryPlayer, LeadedChestTileEntity leadedChest)
	{
		this.leadedchest = leadedChest;

		this.bindOutputSlots();
		this.bindPlayerInventory(inventoryPlayer);
	}

	private void bindOutputSlots()
	{
		int x = 8;
		int y = 18;
		int j = 0;
		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(leadedchest, i, x + (j * 18), y));
			j++;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return this.leadedchest.isUseableByPlayer(entityplayer);
	}

	private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		int inventoryY = 50;
		int hotBarY = 108;
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, inventoryY + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, hotBarY));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack;
		stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		if (slotObject != null && slotObject.getHasStack())
		{
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			if (slot < 9)
			{
				if (!mergeItemStack(stackInSlot, 9, inventorySlots.size(), true))
				{
					return null;
				}
			} else if (!mergeItemStack(stackInSlot, 0, 9, false))
			{
				return null;
			}

			if (stackInSlot.stackSize == 0)
			{
				slotObject.putStack(null);
			} else
			{
				slotObject.onSlotChanged();
			}
			if (stackInSlot.stackSize == stack.stackSize)
			{
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);

		}
		return stack;
	}

	@Override
	public List<ItemStack> getStorageInventory()
	{
		List<ItemStack> storageInventory = new ArrayList<ItemStack>();
		for (int slot = 0; slot < 9; slot++)
		{
			ItemStack stack = getSlot(slot).getStack();
			if (stack != null)
			{
				storageInventory.add(stack);
			}
		}
		return storageInventory;
	}

	@Override
	public List<ItemStack> getPlayerInventory()
	{
		List<ItemStack> playerInventory = new ArrayList<ItemStack>();
		for (int slot = 9; slot < this.inventorySlots.size(); slot++)
		{
			ItemStack stack = getSlot(slot).getStack();
			if (stack != null)
			{
				playerInventory.add(stack);
			}
		}
		return playerInventory;
	}
}
