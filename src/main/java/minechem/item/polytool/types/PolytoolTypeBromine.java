package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeBromine extends PolytoolUpgradeType
{
    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving)
    {
        if (!world.isRemote)
        {
            int bonus = (int)(rand.nextDouble() * Math.log(this.power));
            if (block == Blocks.gold_ore)
            {
                world.setBlockToAir(x, y, z);
                world.spawnEntityInWorld(new EntityItem(world, x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), new ItemStack(Items.gold_ingot, 2 + bonus, 0)));
            }
        }
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.Br;
    }

    @Override
    public String getDescription()
    {

        return "Purifies gold ores";
    }

}
