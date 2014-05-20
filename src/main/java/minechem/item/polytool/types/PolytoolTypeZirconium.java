package minechem.item.polytool.types;

import java.util.Random;

import minechem.item.element.EnumElement;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeZirconium extends PolytoolUpgradeType
{

    public PolytoolTypeZirconium()
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
        if (id == Block.dirt.blockID)
        {
            Random rand = new Random();
            if (rand.nextInt(8192) < 1 + power)
            {
                world.spawnEntityInWorld(new EntityItem(world, x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), new ItemStack(Item.diamond.itemID, 1, 0)));
            }
        }
    }

    @Override
    public EnumElement getElement()
    {

        return EnumElement.Zn;
    }

    @Override
    public void onTick()
    {
    }

    @Override
    public String getDescription()
    {

        return "Makes fake diamonds when mining dirt";
    }

}
