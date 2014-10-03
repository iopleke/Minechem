package minechem.tick;

import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import minechem.MinechemItemsRegistration;
import minechem.item.molecule.MoleculeEnum;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

// Thanks to thepers for teaching me rendering - Mandrake
public class TickHandler
{
    public void transmuteWaterToPortal(World world, int dx, int dy, int dz)
    {
        int px = dx;
        int pz = dz;

        if (world.getBlock(px - 1, dy, pz) == Blocks.water)
        {
            px--;
        }
        if (world.getBlock(px, dy, pz - 1) == Blocks.water)
        {
            pz--;
        }

        world.setBlock(px + 0, dy, pz + 0, Blocks.stone, 0, 2);
    }

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent event)
    {
        World world=event.world;
        
        for (Object p : world.playerEntities){
            EntityPlayer player=(EntityPlayer) p;
            double rangeToCheck = 32.0D;
            
            List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(rangeToCheck, rangeToCheck, rangeToCheck));
            for (EntityItem entityItem : itemList)
            {
                if ((entityItem.getEntityItem().getItem()== new ItemStack(MinechemItemsRegistration.element, 1, MoleculeEnum.potassiumNitrate.ordinal()).getItem() && (world.isMaterialInBB(entityItem.boundingBox, Material.water))))
                {
                    world.createExplosion(entityItem, entityItem.posX, entityItem.posY, entityItem.posZ, 0.9F, true);
                    int dx = MathHelper.floor_double(entityItem.posX);
                    int dy = MathHelper.floor_double(entityItem.posY);
                    int dz = MathHelper.floor_double(entityItem.posZ);
                    transmuteWaterToPortal(world, dx, dy, dz);
                    return;
                }
            }
        }
        
    }

}
