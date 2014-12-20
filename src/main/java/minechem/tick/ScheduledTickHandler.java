package minechem.tick;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import minechem.Settings;
import minechem.fluid.MinechemFluidBlock;
import minechem.item.molecule.MoleculeEnum;
import minechem.potion.PharmacologyEffectRegistry;
import minechem.radiation.RadiationHandler;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

public class ScheduledTickHandler
{

	@SubscribeEvent
	public void tick(TickEvent.PlayerTickEvent event)
	{
		if (event.side == Side.SERVER && event.phase == TickEvent.Phase.START)
		{
            EntityPlayer player = event.player;
            RadiationHandler.getInstance().update(player);
		}
	}

    @SubscribeEvent
    public void checkForPoison(PlayerUseItemEvent.Finish event)
    {
        if (event.item != null && event.item.getTagCompound() != null && Settings.FoodSpiking)
        {
            NBTTagCompound stackTag = event.item.getTagCompound();
            boolean isPoisoned = stackTag.getBoolean("minechem.isPoisoned");
            int[] effectTypes = stackTag.getIntArray("minechem.effectTypes");
            if (isPoisoned)
            {
                for (int effectType : effectTypes)
                {
                    MoleculeEnum molecule = MoleculeEnum.getById(effectType);
                    PharmacologyEffectRegistry.applyEffect(molecule, event.entityPlayer);
                }
            }
        }
    }

	@SubscribeEvent
	public void tick(LivingSpawnEvent.CheckSpawn event){
		if (event.entityLiving instanceof EntityWaterMob&&event.world.getBlock(MathHelper.floor_double(event.x), MathHelper.floor_double(event.y), MathHelper.floor_double(event.z)) instanceof MinechemFluidBlock){
			event.setResult(Event.Result.DENY);
		}
	}
}
