package minechem.tileentity.synthesis;

import java.util.ArrayList;
import java.util.List;
import minechem.MinechemItemsRegistration;
import minechem.api.INoDecay;
import minechem.api.IRadiationShield;
import minechem.container.ContainerWithFakeSlots;
import minechem.item.chemistjournal.ChemistJournalSlot;
import minechem.slot.SlotChemical;
import minechem.slot.SlotFake;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SynthesisContainer extends ContainerWithFakeSlots implements IRadiationShield, INoDecay
{

	private SynthesisTileEntity synthesis;

	public SynthesisContainer(InventoryPlayer inventoryPlayer, SynthesisTileEntity synthesis)
	{
		this.synthesis = synthesis;
		addSlotToContainer(new SynthesisSlotOutput(synthesis, SynthesisTileEntity.kStartOutput, 134, 18));
		bindRecipeMatrixSlots();
		bindStorageSlots();
		addSlotToContainer(new ChemistJournalSlot(synthesis, SynthesisTileEntity.kStartJournal, 26, 36));
		bindPlayerInventory(inventoryPlayer);
	}

	private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 105 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 163));
		}
	}

	private void bindRecipeMatrixSlots()
	{
		int slot = 0;
		for (int row = 0; row < 3; row++)
		{
			for (int col = 0; col < 3; col++)
			{
				addSlotToContainer(new SlotFake(synthesis, SynthesisTileEntity.kStartRecipe + slot, 62 + (col * 18), 18 + (row * 18)));
				slot++;
			}
		}
	}

	private void bindStorageSlots()
	{
		int slot = 0;
		for (int col = 0; col < 9; col++)
		{
			addSlotToContainer(new SlotChemical(synthesis, SynthesisTileEntity.kStartStorage + slot, 8 + (col * 18), 84));
			slot++;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return synthesis.isUseableByPlayer(var1);
	}

	public void craftMaxmimum()
	{
		int amount = 0;
		ItemStack outputItem = synthesis.getCurrentRecipe().getOutput();
		for (int slot = 27; slot < this.inventorySlots.size(); slot++)
		{
			ItemStack stack = getSlot(slot).getStack();
			if (stack == null)
			{
				amount += outputItem.getMaxStackSize();
			} else if (stack.isItemEqual(outputItem))
			{
				amount += outputItem.getMaxStackSize() - stack.stackSize;
			}
		}

		List<ItemStack> outputs = synthesis.getOutput(amount);
		for (ItemStack output : outputs)
		{
			mergeItemStack(output, synthesis.getSizeInventory(), inventorySlots.size(), true);
		}
	}

	@Override
	public List<ItemStack> getPlayerInventory()
	{
		List<ItemStack> playerInventory = new ArrayList<ItemStack>();
		for (int slot = 27; slot < this.inventorySlots.size(); slot++)
		{
			ItemStack stack = getSlot(slot).getStack();
			if (stack != null)
			{
				playerInventory.add(stack);
			}
		}
		return playerInventory;
	}

	@Override
	public float getRadiationReductionFactor(int baseDamage, ItemStack itemstack, EntityPlayer player)
	{
		return 0.4F;
	}

	@Override
	public List<ItemStack> getStorageInventory()
	{
		List<ItemStack> storageInventory = new ArrayList<ItemStack>();
		for (int slot = 0; slot < 27; slot++)
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
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slot)
	{
		Slot slotObject = (Slot) inventorySlots.get(slot);
		ItemStack stack = null;

		if (slotObject != null && slotObject.getHasStack())
		{
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			if (slot != SynthesisTileEntity.kStartJournal && stack.getItem() == MinechemItemsRegistration.journal && !getSlot(SynthesisTileEntity.kStartJournal).getHasStack())
			{
				ItemStack copystack = slotObject.decrStackSize(1);
				getSlot(SynthesisTileEntity.kStartJournal).putStack(copystack);
				return null;
			} else if (slot == SynthesisTileEntity.kStartOutput)
			{
				craftMaxmimum();
				return null;
			} else if (slot >= synthesis.getSizeInventory() && slot < inventorySlots.size() && (stackInSlot.getItem() == MinechemItemsRegistration.element || stackInSlot.getItem() == MinechemItemsRegistration.molecule))
			{
				if (!mergeItemStack(stackInSlot, SynthesisTileEntity.kStartStorage, SynthesisTileEntity.kStartStorage + SynthesisTileEntity.kSizeStorage, false))
				{
					return null;
				}
			} else if (slot >= SynthesisTileEntity.kStartStorage && slot < SynthesisTileEntity.kStartStorage + SynthesisTileEntity.kSizeStorage)
			{
				if (!mergeItemStack(stackInSlot, synthesis.getSizeInventory(), inventorySlots.size(), true))
				{
					return null;
				}
			} else if (slot == SynthesisTileEntity.kStartJournal)
			{
				if (!mergeItemStack(stackInSlot, synthesis.getSizeInventory(), inventorySlots.size(), true))
				{
					return null;
				}
			} else if (slot < 47 && stackInSlot.stackSize == stack.stackSize)
			{
				if (!this.mergeItemStack(stackInSlot, 47, 56, false))
				{
					return null;
				}
			} else if (slot > 46 && stackInSlot.stackSize == stack.stackSize)
			{
				if (!this.mergeItemStack(stackInSlot, 20, 47, false))
				{
					return null;
				}
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
			slotObject.onPickupFromSlot(entityPlayer, stackInSlot);
		}
		return stack;
	}
	
	
}
