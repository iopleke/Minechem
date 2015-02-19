package minechem.item.augment.augments;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import minechem.item.augment.IAugmentedItem;
import minechem.registry.BlockRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;

public class AugmentLight extends AugmentBase
{
    public AugmentLight()
    {
        super("light");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onBlockHarvest(BlockEvent.HarvestDropsEvent event)
    {
        if (event.harvester != null)
        {
            ItemStack stack = event.harvester.getHeldItem();
            if (stack != null && stack.getItem() instanceof IAugmentedItem)
            {
                IAugmentedItem augmentedItem = (IAugmentedItem) stack.getItem();
                int level = augmentedItem.getAugmentLevel(stack, this);
                if (level > -1 && event.world.getBlockLightValue(event.x, event.y, event.z) < 8)
                {
                    consumeAugment(stack, level);
                    event.world.setBlock(event.x, event.y, event.z, BlockRegistry.blockLight, level, 3);
                }
            }
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int level)
    {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        x += dir.offsetX;
        y += dir.offsetY;
        z += dir.offsetZ;
        if (!world.isRemote && world.isAirBlock(x, y, z))
        {
            consumeAugment(stack, level * 2);
            world.setBlock(x, y, z, BlockRegistry.blockLight, (int) (level * 1.5F), 3);
        }
        return true;
    }
}
