package pixlepix.minechem.common;

import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import pixlepix.minechem.api.core.EnumMolecule;
import pixlepix.minechem.api.util.Constants;

import java.util.EnumSet;

public class ScheduledTickHandler implements IScheduledTickHandler
{

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        EntityPlayer entityPlayer = (EntityPlayer) tickData[0];
        RadiationHandler.getInstance().update(entityPlayer);
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        EntityPlayer entityPlayer = (EntityPlayer) tickData[0];
        checkForPoison(entityPlayer);
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.PLAYER);
    }

    @Override
    public String getLabel()
    {
        return "minechem.ScheduledTickHandler";
    }

    @Override
    public int nextTickSpacing()
    {
        return Constants.TICKS_PER_SECOND;
    }

    private void checkForPoison(EntityPlayer entityPlayer)
    {
        ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (isPlayerEating(entityPlayer) && currentItem != null && currentItem.getTagCompound() != null)
        {
            NBTTagCompound stackTag = currentItem.getTagCompound();
            boolean isPoisoned = stackTag.getBoolean("minechem.isPoisoned");
            int effectType = stackTag.getInteger("minechem.effectType");
            EnumMolecule molecule = EnumMolecule.getById(effectType);
            if (isPoisoned)
            {
                PharmacologyEffect.triggerPlayerEffect(molecule, entityPlayer);
                entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
            }
        }
    }

    private boolean isPlayerEating(EntityPlayer player)
    {
        return (player.getDataWatcher().getWatchableObjectByte(0) & 1 << 4) != 0;
    }

}
