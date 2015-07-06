package minechem.fluid;

import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WaterMobSpawnCheckHandler
{

    @SubscribeEvent
    public void tick(LivingSpawnEvent.CheckSpawn event)
    {
        if ((event.entityLiving instanceof EntityWaterMob) && (event.world.getBlock(MathHelper.floor_double(event.x), MathHelper.floor_double(event.y), MathHelper.floor_double(event.z)) instanceof MinechemFluidBlock))
        {
            event.setResult(Result.DENY);
        }
    }

}
