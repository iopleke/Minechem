package minechem.tick;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import minechem.Settings;
import minechem.item.molecule.MoleculeEnum;
import minechem.potion.PotionPharmacologyEffect;
import minechem.radiation.RadiationHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ScheduledTickHandler
{

	@SubscribeEvent
	public void tick(TickEvent.PlayerTickEvent event){
		if(event.side == Side.SERVER)
			if(event.phase == TickEvent.Phase.START){
				EntityPlayer player = event.player;
				RadiationHandler.getInstance().update(player);
			} else if(event.phase == TickEvent.Phase.END){
				EntityPlayer player = event.player;
				checkForPoison(player);
			}
	}


    private void checkForPoison(EntityPlayer entityPlayer)
    {
        ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (isPlayerEating(entityPlayer) && currentItem != null && currentItem.getTagCompound() != null)
        {
            NBTTagCompound stackTag = currentItem.getTagCompound();
            boolean isPoisoned = stackTag.getBoolean("minechem.isPoisoned");
            int effectType = stackTag.getInteger("minechem.effectType");
            MoleculeEnum molecule = MoleculeEnum.getById(effectType);
            if (isPoisoned)
            {
                if (Settings.FoodSpiking)
                {
                    PotionPharmacologyEffect.triggerPlayerEffect(molecule, entityPlayer);
                }
                entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
            }
        }
    }

    private boolean isPlayerEating(EntityPlayer player)
    {
        return (player.getDataWatcher().getWatchableObjectByte(0) & 1 << 4) != 0;
    }

}
