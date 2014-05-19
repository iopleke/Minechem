package minechem.item.polytool.types;

import java.util.Random;

import minechem.api.core.EnumElement;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeBromine extends PolytoolUpgradeType
{

    public PolytoolTypeBromine()
    {
        super();
    }

    @Override
    public float getStrVsBlock(ItemStack itemStack, Block block)
    {

        return 0;
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, int id, int x, int y, int z, EntityLivingBase entityLiving)
    {
        Random rand = new Random();
        if (!world.isRemote)
        {
            int bonus = (int) (rand.nextDouble() * Math.log(this.power));
            if (id == Block.oreGold.blockID)
            {
                world.destroyBlock(x, y, z, false);
                world.spawnEntityInWorld(new EntityItem(world, x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), new ItemStack(Item.ingotGold.itemID, 2 + bonus, 0)));
            }
        }
    }

    @Override
    public EnumElement getElement()
    {

        return EnumElement.Br;
    }

    @Override
    public void onTick()
    {
    }

    @Override
    public String getDescription()
    {

        return "Purifies gold ores";
    }

}
