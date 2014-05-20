package minechem.tileentity.synthesis;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SynthesisSlotOutput extends Slot
{

    private SynthesisTileEntity synthesis;

    public SynthesisSlotOutput(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
        this.synthesis = (SynthesisTileEntity) par1iInventory;
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
        return synthesis.canTakeOutputStack();
    }

    @Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
    {
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }

}
