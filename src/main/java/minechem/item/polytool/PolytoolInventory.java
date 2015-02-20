package minechem.item.polytool;

import minechem.item.element.ElementItem;
import minechem.network.MessageHandler;
import minechem.network.message.PolytoolUpdateMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class PolytoolInventory implements IInventory
{
    public ItemStack item;
    public EntityPlayer player;

    public PolytoolInventory(ItemStack currentItem, EntityPlayer player)
    {
        this.player = player;
    }

    @Override
    public int getSizeInventory()
    {

        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {

        return item;
    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {

        ItemStack itemstack = getStackInSlot(i);

        if (itemstack != null)
        {
            if (itemstack.stackSize <= j)
            {
                setInventorySlotContents(i, null);
            } else
            {
                itemstack = itemstack.splitStack(j);
            }
        }
        return itemstack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        return item;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        item = itemstack;

        if (itemstack != null && itemstack.stackSize == 64 && (itemstack.getItem() instanceof ElementItem) && PolytoolHelper.getTypeFromElement(ElementItem.getElement(itemstack), 1) != null
                && PolytoolItem.validAlloyInfusion(player.getCurrentEquippedItem(), itemstack))
        {
            item = null;
            PolytoolUpgradeType upgrade = PolytoolHelper.getTypeFromElement(ElementItem.getElement(itemstack), 1);
            PolytoolItem.addTypeToNBT(player.inventory.getCurrentItem(), upgrade);

            if (!player.worldObj.isRemote)
            {
                PolytoolUpdateMessage message = new PolytoolUpdateMessage(upgrade);
                MessageHandler.INSTANCE.sendTo(message, (EntityPlayerMP)player);
            }
        }

    }

    @Override
    public String getInventoryName()
    {

        return "Polytool Inventory";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {

        return 64;
    }

    @Override
    public void markDirty()
    {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {

        return false;
    }

    @Override
    public void openInventory()
    {

    }

    @Override
    public void closeInventory()
    {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {

        return itemstack == null || itemstack.stackSize == 64 && (itemstack.getItem() instanceof ElementItem) && PolytoolHelper.getTypeFromElement(ElementItem.getElement(itemstack), 1) != null;
    }

}
